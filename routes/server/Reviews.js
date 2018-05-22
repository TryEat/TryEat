module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;

        var query = 'SELECT * FROM restaurant_rate WHERE user_id=?';
        dbPool.query(query, [user_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) {
                var data = JSON.stringify(rows);
                res.end(data);
            }
            else res.end('없음');
        });
    });

    router.get('/:user_id/:restaurant_id', function (req, res) {
        var user_id = req.params.user_id;
        var restaurant_id = req.params.restaurant_id;

        var query = 'SELECT * FROM restaurant_rate WHERE user_id=? AND restaurant_id=?';
        dbPool.query(query, [user_id, restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) {
                var data = JSON.stringify(rows);
                res.end(data);
            }
            else res.end('없음');
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
                var query = 'UPDATE restaurant SET review_count=review_count+1 WHERE restaurant_id=?';
                dbPool.query(query, [restaurant_id], function (err, rows, fields) {
                    if (err) throw err;
                    if (rows.changedRows != 0) res.end('리뷰 반영 성공');
                    else res.end('리뷰 반영 실패');
                });
                res.end('리뷰 등록 성공');
            }
            else res.end('리뷰 등록 실패');
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
            if (rows.changedRows != 0) res.end('리뷰 변경 성공');
            else res.end('리뷰 변경 실패');
        });
    });

    router.delete('/', function (req, res) {
        var review_id = req.body.review_id;

        var query = 'DELETE FROM restaurant_rate WHERE txt_id=?';
        dbPool.query(query, [review_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.end('리뷰 삭제 성공');
            else res.end('리뷰 삭제 실패');
        });
    });

    return router;
}