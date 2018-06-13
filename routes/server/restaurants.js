module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var multer = require('multer')();
    var dbPool = _dbPool;

    function generateID() {
        var _id;
        return _id;
    }

    router.get('/:position', function (req, res) {
        var position = req.params.position;

        var query = 'SELECT * FROM restaurant LIMIT ?,5';
        dbPool.query(query, [parseInt(position)], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    })

    router.get('/list/:id_list/byid', function (req, res) {
        var id_list = req.params.id_list;

        var query = 'SELECT * FROM restaurant WHERE restaurant_id IN (?)';
        dbPool.query(query, [id_list], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    })

    router.get('/:id/byid', function (req, res) {
        var restaurant_id = req.params.id;

        var query = 'SELECT * FROM restaurant WHERE restaurant_id=?';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows[0]);
        });
    })

    router.get('/image/:id', function (req, res) {
        var restaurant_id = req.params.id;

        var query = 'SELECT img FROM restaurant WHERE restaurant_id=?';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    })

    router.get('/:name/byname', function (req, res) {
        var restaurant_name = "%" + req.params.name + "%";
        var restaurant_name2 = "%" + req.params.name.slice(0, -1) + "%";
        if (restaurant_name2 == "%%") restaurant_name2 = restaurant_name;
        var query = 'SELECT restaurant_id, restaurant_name FROM restaurant WHERE restaurant_name LIKE ? OR restaurant_name LIKE ? LIMIT 30';
        dbPool.query(query, [restaurant_name, restaurant_name2], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) res.status(200).json(rows);
            else res.status(400).json({ message: 'is not exist' });
        });
    })

    router.get('/:lat/:ion/bylocation', function (req, res) {
        var locate_latitude = req.params.lat;
        var locate_longitude = req.params.ion;

        var query = 'SELECT * from restaurant where locate_latitude=? AND locate_longitude=?';
        dbPool.query(query, [locate_latitude, locate_longitude], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    })

    router.get('/:name/:lat/:ion', function (req, res) {
        var restaurant_name = "%" + req.params.name + "%";
        var locate_latitude = req.params.lat;
        var locate_longitude = req.params.ion;

        var query = 'SELECT * FROM restaurant WHERE restaurant_name LIKE ? AND locate_latitude BETWEEN ? AND ? AND locate_longitude BETWEEN ? AND ?';
        dbPool.query(query, [restaurant_name, locate_latitude - 0.1, locate_latitude + 0.1, locate_longitude - 0.1, locate_longitude + 0.1], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    })

    router.get('/is_exist/:name/byname', function (req, res) {
        var restaurant_name = req.params.name;

        var query = 'SELECT EXISTS (select * from restaurant where restaurant_name=?) as success';
        dbPool.query(query, [restaurant_name], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].success == 1) {
                res.status(200).json({ message: "is Exist" })
            }
            else res.status(400).json({ message: "is not Exist" })
        });
    })

    router.get('/is_exist/:lat/:ion/bylocation', function (req, res) {
        var locate_latitude = req.params.lat;
        var locate_longitude = req.params.ion;

        var query = 'SELECT EXISTS (select * from restaurant where locate_latitude=? AND locate_longitude=?) as success';
        dbPool.query(query, [locate_latitude, locate_longitude], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].success == 1) {
                res.status(200).json({ message: "is Exist" })
            }
            else res.status(400).json({ message: "is not Exist" })
        });
        res.end(req.params.name);
    })

    router.get('/count/:id', function (req, res) {
        var restaurant_id = req.params.restaurant_id;

        var query = ' SELECT review_count FROM restaurant WHERE restaurant_id=?';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) res.status(200).json({ message: "is Exist", payLoadInt: rows[0].review_count })
            else res.status(400).json({ message: "is not Exist" })
        });
    });

    router.post('/', multer.single('upload'), function (req, res) {
        var img = req.file.buffer;
        var restaurant_name = req.body.restaurant_name;
        var address = req.body.address;
        var phone = req.body.phone;
        var locate_latitude = req.body.locate_latitude;
        var locate_longitude = req.body.locate_longitude;

        if(img.length==0)img=null;

        var query = 'INSERT INTO restaurant (img,restaurant_name,address,phone,locate_latitude,locate_longitude,review_count,total_rate) VALUES (?,?,?,?,?,?,?,?)';
        dbPool.query(query, [img, restaurant_name, address, phone, locate_latitude, locate_longitude,0,0], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.status(201).json({ message: "Add Restaurant Success", payLoadInt: rows.insertId , payLoadString: restaurant_name})
            else res.status(400).json({ message: "Add Restaurant Fail" })
        });
    });

    router.put('/', function (req, res) {
        var restaurant_id = req.body.restaurant_id;
        var img = req.body.img;
        var restaurant_name = req.body.restaurant_name;
        var address = req.body.address;
        var phone = req.body.phone;
        var locate_latitude = req.body.locate_latitude;
        var locate_longitude = req.body.locate_longitude;

        var query = 'UPDATE restaurant SET img=?,restaurant_name=?,address=?,phone=?,locate_latitude=?,locate_longitude=?, WHERE restaurant_id=?';
        dbPool.query(query, [img, restaurant_name, address, phone, locate_latitude, locate_longitude, restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.changedRows != 0) res.status(201).json({ message: "Restaurant Revise Success" })
            else res.status(400).json({ message: "Restaurant Revise Fail" })
        });
    });

    router.delete('/', function (req, res) {
        var restaurant_id = req.body.restaurant_id;

        var query = 'DELETE FROM restaurant WHERE restaurant_id=?';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.status(201).json({ message: "Restaurant Delete Success" })
            else res.status(400).json({ message: "Restaurant Delete Fail" })
        });
    });

    return router;
}