module.exports = function (_dbPool) {
    var fs = require('fs');
    var path = require('path');
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;
    var multer = require('multer');
    var upload = multer({
        storage:multer.diskStorage({
            destination: function (req, file, cb) {
                cb(null, 'Image/restaurant');
            },
            filename: function (req, file, cb) {
                cb(null, new Date().valueOf() + '.webp');
            }
        }),
    });

    function generateID() {
        var _id;
        return _id;
    }

    router.get('/:position', function (req, res) {
        var position = req.params.position;

        var query = 'SELECT * FROM restaurant LIMIT ?,10';
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

    router.get('/:name/byname', function (req, res) {
        var restaurant_name = "%" + req.params.name + "%";
        var restaurant_name2 = "%" + req.params.name.slice(0, -1) + "%";
        if (restaurant_name2 == "%%") restaurant_name2 = restaurant_name;
        var query = 'SELECT restaurant_id, restaurant_name,address FROM restaurant WHERE restaurant_name LIKE ? OR restaurant_name LIKE ? LIMIT 30';
        dbPool.query(query, [restaurant_name, restaurant_name2], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) res.status(200).json(rows);
            else res.status(400).json({ message: 'is not exist' });
        });
    })

    router.get('/image/uri/:uri', function (req, res) {
        var uri = req.params.uri;
        fs.readFile('Image/restaurant/'+uri,function(err,data){
            res.end(data);
        });
    });

    router.get('/byrecommander/:user_id/:lat/:ion/:position/:distance', function (req, res) {
        var user_id = req.params.user_id;
        var locate_latitude = req.params.lat;
        var locate_longitude = req.params.ion;
        var distance = req.params.distance;
        var position = req.params.position;

        var spawn = require("child_process").spawn;

        var process = spawn('python', ["./deep/recommand.py", user_id, position, 40]);

        process.stdout.on('data', function (data) {
            var idlist = data.toString().split(',').map(function (item) {
                return parseInt(item, 10);
            });

            if (!isNaN(idlist[0])){
                if (distance == 0) {
                    var query = 'SELECT * FROM restaurant WHERE restaurant_id IN (?) ORDER BY FIND_IN_SET(restaurant_id,"?") LIMIT ?,10';
                    dbPool.query(query, [idlist,idlist,parseInt(position%40)], function (err, rows, fields) {
                        if (err) throw err;
                        res.status(200).json(rows);

                    });
                } else {
                    distance=5000;
                    var query = 'SELECT restaurant_name,(6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(locate_latitude)) * COS(RADIANS(locate_longitude) \
                - RADIANS(?)) + SIN(RADIANS(?)) * SIN(RADIANS(locate_latitude)))) AS distance \
                 From tryeat.restaurant WHERE restaurant_id IN (?)  HAVING distance < ? LIMIT ?,10;'
                    dbPool.query(query, [locate_latitude, locate_longitude, locate_latitude, idlist, parseInt(distance), parseInt(position%40)], function (err, rows, fields) {
                        if (err) throw err;
                        res.status(200).json(rows);
                    });
                }
            }else{
                res.status(400);
            }
        })
    });

    router.get('/bydistance/:lat/:ion/:position/:distance', function (req, res) {
        var locate_latitude = req.params.lat;
        var locate_longitude = req.params.ion;
        var distance = req.params.distance;
        var position = req.params.position;

        if (distance == 0) distance = 100000;

        var query = 'SELECT *, (6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(locate_latitude)) * COS(RADIANS(locate_longitude) \
        - RADIANS(?)) + SIN(RADIANS(?)) * SIN(RADIANS(locate_latitude)))) AS distance FROM tryeat.restaurant HAVING distance < ? \
        ORDER BY distance LIMIT ? , 5;';
        dbPool.query(query, [locate_latitude, locate_longitude, locate_latitude, parseInt(distance), parseInt(position)], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    })

    router.get('/byreview/:lat/:ion/:position/:distance', function (req, res) {
        var locate_latitude = req.params.lat;
        var locate_longitude = req.params.ion;
        var distance = req.params.distance;
        var position = req.params.position;

        if (distance == 0) {
            var query = 'SELECT * FROM tryeat.restaurant ORDER BY review_count DESC LIMIT ? , 5;';
            dbPool.query(query, [parseInt(position)], function (err, rows, fields) {
                if (err) throw err;
                res.status(200).json(rows);
            });
        } else {
            var query = 'SELECT *, (6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(locate_latitude)) * COS(RADIANS(locate_longitude) \
        - RADIANS(?)) + SIN(RADIANS(?)) * SIN(RADIANS(locate_latitude)))) AS distance FROM tryeat.restaurant HAVING distance < ? \
        ORDER BY review_count DESC LIMIT ? , 5;'
            dbPool.query(query, [locate_latitude, locate_longitude, locate_latitude, parseInt(distance), parseInt(position)], function (err, rows, fields) {
                if (err) throw err;
                res.status(200).json(rows);
            });
        }
    });

    router.get('/byrate/:lat/:ion/:position/:distance', function (req, res) {
        var locate_latitude = req.params.lat;
        var locate_longitude = req.params.ion;
        var distance = req.params.distance;
        var position = req.params.position;

        if (distance == 0) {
            var query = 'SELECT * FROM tryeat.restaurant ORDER BY total_rate/review_count DESC LIMIT ? , 5';
            dbPool.query(query, [parseInt(position)], function (err, rows, fields) {
                if (err) throw err;
                res.status(200).json(rows);
            });
        } else {
            query = 'SELECT *, (6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(locate_latitude)) * COS(RADIANS(locate_longitude) \
        - RADIANS(?)) + SIN(RADIANS(?)) * SIN(RADIANS(locate_latitude)))) AS distance FROM tryeat.restaurant HAVING distance < ? \
        ORDER BY total_rate/review_count DESC LIMIT ? , 5;'
            dbPool.query(query, [locate_latitude, locate_longitude, locate_latitude, parseInt(distance), parseInt(position)], function (err, rows, fields) {
                if (err) throw err;
                res.status(200).json(rows);
            });
        }
    });

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

    router.post('/', upload.single('upload'), function (req, res) {
        var restaurant_name = req.body.restaurant_name.replace(/"/gi, '');
        var address = req.body.address.replace(/"/gi, '');
        var phone = req.body.phone.replace(/"/gi, '');
        var locate_latitude = req.body.locate_latitude;
        var locate_longitude = req.body.locate_longitude;

        var img_uri = (req.file.size==0)?null:req.file.filename;

        var query = 'INSERT INTO restaurant (img_uri,restaurant_name,address,phone,locate_latitude,locate_longitude,review_count,total_rate,total_bookmark) VALUES (?,?,?,?,?,?,?,?,?);';
        query += 'update tryeat.counting SET restaurant=restaurant+1 where target=0;'
        dbPool.query(query, [img_uri, restaurant_name, address, phone, locate_latitude, locate_longitude, 0, 0, 0], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].affectedRows != 0) res.status(201).json({ message: "Add Restaurant Success", payLoadInt: rows[0].insertId, payLoadString: restaurant_name })
            else res.status(400).json({ message: "Add Restaurant Fail" })
        });
    });

    router.delete('/', function (req, res) {
        var restaurant_id = req.body.restaurant_id;

        var query = 'DELETE FROM restaurant WHERE restaurant_id=?;';
        query += 'update tryeat.counting SET restaurant=restaurant-1 where target=0;'
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].affectedRows != 0) res.status(201).json({ message: "Restaurant Delete Success" })
            else res.status(400).json({ message: "Restaurant Delete Fail" })
        });
    });

    return router;
}