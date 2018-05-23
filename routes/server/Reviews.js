module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;

        var query = 'SELECT * FROM restaurant_rate WHERE user_id=?';
        dbPool.query(query, [user_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/:restaurant_id', function (req, res) {
        var restaurant_id = req.params.restaurant_id;

        var query = 'SELECT * FROM restaurant_rate WHERE restaurant_id=?';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/:user_id/:restaurant_id', function (req, res) {
        var user_id = req.params.user_id;
        var restaurant_id = req.params.restaurant_id;

        var query = 'SELECT * FROM restaurant_rate WHERE user_id=? AND restaurant_id=?';
        dbPool.query(query, [user_id, restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.post('/', function (req, res) {
        var restaurant_id = req.body.restaurant_id;
        var user_id = req.body.user_id;
        var img_id = req.body.img_id;
        var content = req.body.content;
        var rate = req.body.rate;

        var query = 'INSERT INTO restaurant_rate (restaurant_id,user_id,img_id,content,rate) VALUES (?,?,?,?,?)';
        dbPool.query(query, [restaurant_id, user_id, img_id, content, rate], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) {
                var query = 'UPDATE restaurant SET review_count=review_count+1, total_rate=total_rate+rate WHERE restaurant_id=?';
                dbPool.query(query, [restaurant_id], function (err, rows, fields) {
                    if (err) throw err;
                    if (rows.changedRows != 0) res.status(201).json({message: "write review, count++ success"})
                    else res.status(400).json({message: "write review success, count++ fail"})
                });
            }
            else res.status(400).json({message: "write review fail"})
        });
    });

    router.put('/', function (req, res) {
        var review_id = req.body.review_id;
        var img_id = req.body.img_id;
        var content = req.body.content;
        var rate = req.body.rate;

        var query = 'UPDATE restaurant_rate SET img_id=?, content=?, rate=? WHERE txt_id=?';
        dbPool.query(query, [img_id, content, rate, review_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.changedRows != 0) res.status(201).json({message: "update review success"})
            else res.status(400).json({message: "update review fail"})
        });
    });

    router.delete('/', function (req, res) {
        var review_id = req.body.review_id;

        var query = 'DELETE FROM restaurant_rate WHERE txt_id=?';
        dbPool.query(query, [review_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.status(201).json({message: "delete review success"})
            else res.status(400).json({message: "delete review fail"})
        });
    });

    return router;
}