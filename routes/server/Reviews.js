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
        INNER JOIN user ON review.user_id = user.user_id WHERE review.user_id = ? LIMIT ?,5';

        dbPool.query(query, [user_id, parseInt(position)], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/:restaurant_id/:position/restaurant', function (req, res) {
        var restaurant_id = req.params.restaurant_id;
        var position = req.params.position;

        var query = 'SELECT review.*, restaurant_name, address, user_login_id FROM \
        review INNER JOIN restaurant ON review.restaurant_id = restaurant.restaurant_id \
        INNER JOIN user ON review.user_id = user.user_id WHERE review.restaurant_id = ? LIMIT ?,5';
        dbPool.query(query, [restaurant_id, parseInt(position)], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/:user_id/:restaurant_id', function (req, res) {
        var user_id = req.params.user_id;
        var restaurant_id = req.params.restaurant_id;

        var query = 'SELECT review.*, restaurant_name, address, user_login_id FROM \
        review INNER JOIN restaurant ON review.restaurant_id = restaurant.restaurant_id \
        INNER JOIN user ON review.user_id = user.user_id WHERE review.user_id = ? AND review.restaurant_id = ? LIMIT 1';
        dbPool.query(query, [user_id, restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows[0]);
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
        var content = req.body.content.replace(/"/gi, '');;
        var rate = req.body.rate;

        if (img.length == 0) img = null;

        var query = 'INSERT INTO review (restaurant_id,user_id,img,content,rate) VALUES (?,?,?,?,?);';
        query += 'UPDATE restaurant SET review_count=review_count+1, total_rate=total_rate+? WHERE restaurant_id=?;'
        query += 'UPDATE user SET review_count=review_count+1 WHERE user_id=?;'
        query += 'update counting SET review=review+1 where target=0;'
        dbPool.query(query, [restaurant_id, user_id, img, content, rate, rate, restaurant_id,user_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].changedRows != 0) res.status(201).json({ message: "write review, count++ success" })
            else res.status(400).json({ message: "write review success, count++ fail" })
        });
    });

    router.put('/', multer.single('upload'),function (req, res) {
        var review_id = req.body.review_id;
        var img = req.file.buffer;
        var content = req.body.content.replace(/"/gi, '');;
        var rate = req.body.rate;

        if(img.length==0)img=null;

        var query = 'Update tryeat.restaurant,(Select rate,restaurant_id from tryeat.review Where review_id=?)target \
        Set total_rate=total_rate-target.rate Where tryeat.restaurant.restaurant_id=target.restaurant_id;'
        query += 'UPDATE review SET img=?, content=?, rate=? WHERE review_id=?;';
        dbPool.query(query, [review_id,img, content, rate, review_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].changedRows != 0) res.status(201).json({ message: "update review success" })
            else res.status(400).json({ message: "update review fail" })
        });
    });

    router.delete('/', function (req, res) {
        var review_id = req.body.review_id;

        var query = 'Update tryeat.restaurant AS a, tryeat.user AS b,(Select rate,restaurant_id,user_id from tryeat.review Where review_id=?)target \
        Set a.review_count=a.review_count-1, a.total_rate=a.total_rate-target.rate, b.review_count=b.review_count-1 \
        Where a.restaurant_id=target.restaurant_id AND b.user_id=target.user_id;'
        query += 'DELETE FROM review WHERE review_id=?;'
        query += 'update tryeat.counting SET review=review-1 where target=0;'
        dbPool.query(query, [review_id,review_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].affectedRows != 0) res.status(201).json({ message: "delete review success" })
            else res.status(400).json({ message: "delete review fail" })
        });
    });

    return router;
}