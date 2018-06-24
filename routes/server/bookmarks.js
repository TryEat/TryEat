module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/:user_id/:position', function (req, res) {
        var user_id = req.params.user_id;
        var position = req.params.position;

        var query = 'SELECT *,date FROM tryeat.restaurant \
         INNER JOIN tryeat.bookmark ON tryeat.bookmark.restaurant_id = tryeat.restaurant.restaurant_id \
         WHERE tryeat.bookmark.user_id = ? ORDER BY date DESC LIMIT ?,10';
        dbPool.query(query, [user_id, parseInt(position)], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/isExist/:user_id/:restaurant_id', function (req, res) {
        var user_id = req.params.user_id;
        var restaurant_id = req.params.restaurant_id;

        var query = 'SELECT EXISTS (select * from bookmark where user_id=? AND restaurant_id=?) as success';
        dbPool.query(query, [user_id, restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].success == 1) {
                res.status(201).json({ message: "bookmark is Exist" })
            } else {
                res.status(400).json({ message: "bookmark is not Exist" })
            }
        });
    });

    router.post('/', function (req, res) {
        var user_id = req.body.user_id;
        var restaurant_id = req.body.restaurant_id;

        var query = 'INSERT INTO tryeat.bookmark (user_id,restaurant_id) SELECT ?,?\
        WHERE NOT EXISTS (select * from tryeat.bookmark where tryeat.bookmark.user_id=? AND tryeat.bookmark.restaurant_id=?) \
        AND EXISTS (select * from tryeat.restaurant where restaurant.restaurant_id=?) LIMIT 1;'
        query += 'UPDATE user SET bookmark_count=bookmark_count+1 WHERE user_id=?;'
        query += 'UPDATE restaurant SET total_bookmark=total_bookmark+1 WHERE restaurant_id = ?;'
        dbPool.query(query, [user_id, restaurant_id,user_id, restaurant_id,restaurant_id,user_id,restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].affectedRows != 0) res.status(201).json({ message: "follow sueccess" })
            else res.status(400).json({ message: "follow fail" })
        });
    });

    router.delete('/', function (req, res) {
        var user_id = req.body.user_id;
        var restaurant_id = req.body.restaurant_id;

        var query = 'DELETE FROM bookmark WHERE(user_id=? AND restaurant_id=?);';
        query += 'UPDATE user SET bookmark_count=bookmark_count-1 WHERE user_id=?;'
        query += 'UPDATE restaurant SET total_bookmark=total_bookmark-1 WHERE restaurant_id = ?;'
        dbPool.query(query, [user_id, restaurant_id,user_id,restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].affectedRows != 0) res.status(201).json({ message: "delete follow success" })
            else res.status(400).json({ message: "delete follow fail" })
        });
    });

    return router;
}