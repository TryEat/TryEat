module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    function generateID() {
        var _id;
        return _id;
    }

    router.get('/', function (req, res) {
        var query = 'SELECT * FROM restaurant';
        dbPool.query(query, [], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    })

    router.get('/:id/byid', function (req, res) {
        var restaurant_id = req.params.id;

        var query = 'SELECT * FROM restaurant WHERE restaurant_id=?';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    })

    router.get('/:name/byname', function (req, res) {
        var restaurant_name = req.params.name;

        var query = 'SELECT * FROM restaurant WHERE restaurant_name=?';
        dbPool.query(query, [restaurant_name], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
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

    router.post('/', function (req, res) {
        var img_id = req.body.img_id;
        var restaurant_name = req.body.restaurant_name;
        var locate_latitude = req.body.locate_latitude;
        var locate_longitude = req.body.locate_longitude;
        var restaurant_desc = req.body.restaurant_desc;
        var open_time = req.body.open_time;
        var close_time = req.body.close_time;

        var query = 'INSERT INTO restaurant (img_id,restaurant_name,locate_latitude,locate_longitude,restaurant_desc,open_time,close_time,review_count,total_rate) VALUES (?,?,?,?,?,?,?,0,0)';
        dbPool.query(query, [img_id, restaurant_name, locate_latitude, locate_longitude, restaurant_desc, open_time, close_time, 0, 0], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.status(201).json({ message: "Add Restaurant Success" })
            else res.status(400).json({ message: "Add Restaurant Fail" })
        });
    });

    router.put('/', function (req, res) {
        var restaurant_id = req.body.restaurant_id;
        var img_id = req.body.img_id;
        var restaurant_name = req.body.restaurant_name;
        var locate_latitude = req.body.locate_latitude;
        var locate_longitude = req.body.locate_longitude;
        var restaurant_desc = req.body.restaurant_desc;
        var open_time = req.body.open_time;
        var close_time = req.body.close_time;

        var query = 'UPDATE restaurant SET img_id=?,restaurant_name=?,locate_latitude=?,locate_longitude=?,restaurant_desc=?,open_time=?,close_time=? WHERE restaurant_id=?';
        dbPool.query(query, [img_id, restaurant_name, locate_latitude, locate_longitude, restaurant_desc, open_time, close_time, restaurant_id], function (err, rows, fields) {
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