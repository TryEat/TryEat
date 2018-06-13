module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/:user_id/:position', function (req, res) {
        var user_id = req.params.user_id;
        var position = req.params.position;

        var query = 'SELECT * FROM tryeat.restaurant \
         INNER JOIN tryeat.bookmark ON tryeat.bookmark.restaurant_id = tryeat.restaurant.restaurant_id \
         WHERE tryeat.bookmark.user_id = ? LIMIT ?,10';
        dbPool.query(query, [user_id,parseInt(position)], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/isExist/:user_id/:restaurant_id', function (req, res) {
        var user_id = req.params.user_id;
        var restaurant_id = req.params.restaurant_id;

        var query = 'SELECT EXISTS (select * from bookmark where user_id=? AND restaurant_id=?) as success';
        dbPool.query(query, [user_id,restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].success == 1) {
                res.status(201).json({message: "bookmark is Exist"}) 
            }else{
                res.status(400).json({message: "bookmark is not Exist"})
            }
        });
    });

    router.post('/', function (req, res) {
        var user_id = req.body.user_id;
        var restaurant_id = req.body.restaurant_id;

        var query = 'SELECT EXISTS (select * from restaurant where restaurant_id=?) as success';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].success == 1) {
                query = 'INSERT INTO bookmark (user_id,restaurant_id) VALUES (?,?)';
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

        var query = 'DELETE FROM bookmark WHERE(user_id=? AND restaurant_id=?)';
        dbPool.query(query, [user_id, restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0)  res.status(201).json({message: "delete follow success"})
            else res.status(400).json({message: "delete follow fail"})
        });
    });

    return router;
}