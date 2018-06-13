module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var multer = require('multer')();
    var dbPool = _dbPool;

    router.get('/:user_id/:position/user', function (req, res) {
        var user_id = req.params.user_id;
        var position = req.params.position;
        
        var query = 'SELECT review.*, restaurant_name, address, user_login_id FROM \
        review INNER JOIN restaurant ON review.restaurant_id = restaurant.restaurant_id \
        INNER JOIN user ON review.user_id = user.user_id WHERE review.user_id = ? LIMIT ?,10';
        
        dbPool.query(query, [user_id,parseInt(position)], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/:restaurant_id/:position/restaurant', function (req, res) {
        var restaurant_id = req.params.restaurant_id;
        var position = req.params.position;

        var query = 'SELECT * FROM review WHERE restaurant_id=? LIMIT ?,5';
        dbPool.query(query, [restaurant_id,parseInt(position)], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/:user_id/:restaurant_id', function (req, res) {
        var user_id = req.params.user_id;
        var restaurant_id = req.params.restaurant_id;

        var query = 'SELECT * FROM review WHERE user_id=? AND restaurant_id=?';
        dbPool.query(query, [user_id, restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/:review_id', function (req, res) {
        var review_id = req.params.review_id;

        var query = 'SELECT review_id,img FROM review WHERE review_id=?';
        dbPool.query(query, [review_id], function (err, rows, fields) {
            if (err) throw err;
            JSON.stringify(rows[0]);
            res.status(200).json(rows[0]);
        });
    });

    router.post('/', multer.single('upload'), function (req, res) {
        var restaurant_id = req.body.restaurant_id;
        var user_id = req.body.user_id;
        var img = req.file.buffer;
        var content = req.body.content;
        var rate = req.body.rate;

        if(img.length==0)img=null;

        var query = 'INSERT INTO review (restaurant_id,user_id,img,content,rate) VALUES (?,?,?,?,?)';
        dbPool.query(query, [restaurant_id, user_id, img, content, rate], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) {
                var query = 'UPDATE restaurant SET review_count=review_count+1, total_rate=total_rate+? WHERE restaurant_id=?';
                dbPool.query(query, [rate, restaurant_id], function (err, rows, fields) {
                    if (err) throw err;
                    if (rows.changedRows != 0) res.status(201).json({ message: "write review, count++ success" })
                    else res.status(400).json({ message: "write review success, count++ fail" })
                });
            }
            else res.status(400).json({ message: "write review fail" })
        });
    });

    router.put('/', function (req, res) {
        var review_id = req.body.review_id;
        var img = req.body.img;
        var content = req.body.content;
        var rate = req.body.rate;

        var query = 'UPDATE review SET img=?, content=?, rate=? WHERE review_id=?';
        dbPool.query(query, [img, content, rate, review_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.changedRows != 0) res.status(201).json({ message: "update review success" })
            else res.status(400).json({ message: "update review fail" })
        });
    });

    router.delete('/', function (req, res) {
        var review_id = req.body.review_id;

        var query = 'DELETE FROM review WHERE review_id=?';
        dbPool.query(query, [review_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.status(201).json({ message: "delete review success" })
            else res.status(400).json({ message: "delete review fail" })
        });
    });

    return router;
}