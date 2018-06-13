module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;

        var query = 'SELECT target_id, user_login_id FROM tryeat.follow \
         INNER JOIN tryeat.user ON tryeat.user.user_id = tryeat.follow.target_id \
         WHERE tryeat.follow.user_id = ? LIMIT 0,3';
        dbPool.query(query, [user_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.post('/', function (req, res) {
        var user_id = req.body.user_id;
        var restaurant_id = req.body.restaurant_id;

        var query = 'SELECT EXISTS (select * from user where user_id=?) as success';
        dbPool.query(query, [target_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].success == 1) {
                query = 'INSERT INTO follow (user_id,restaurant_id) VALUES (?,?)';
                dbPool.query(query, [user_id, restaurant_id], function (err, rows, fields) {
                    if (err) throw err;
                    if (rows.affectedRows != 0) res.status(201).json({message: "follow sueccess"})
                    else res.status(400).json({message: "follow fail"})
                });
            }
            else  res.status(409).json({message: "target not exist"})
        });
    });

    router.delete('/', function (req, res) {
        var user_id = req.body.user_id;
        var restaurant_id = req.body.restaurant_id;

        var query = 'DELETE FROM follow WHERE(user_id=? AND restaurant_id=?)';
        dbPool.query(query, [user_id, restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0)  res.status(201).json({message: "delete follow success"})
            else res.status(400).json({message: "delete follow fail"})
        });
    });

    return router;
}