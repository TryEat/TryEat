module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;

        var query = 'SELECT * FROM follow WHERE user_id=?';
        dbPool.query(query, [user_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) {
                var data = JSON.stringify(rows);
                res.end(data);
            } 
            else res.end('0 명');
        });
    });

    router.post('/', function (req, res) {
        var user_id = req.body.user_id;
        var target_id = req.body.target_id;

        var query = 'SELECT EXISTS (select * from user where user_id=?) as success';
        dbPool.query(query, [target_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].success == 1) {
                query = 'INSERT INTO follow (user_id,target_id) VALUES (?,?)';
                dbPool.query(query, [user_id, target_id], function (err, rows, fields) {
                    if (err) throw err;
                    if (rows.affectedRows != 0) res.end('팔로우 성공');
                    else res.end('팔로우 실패');
                });
            }
            else res.end('대상 없음');
        });
    });

    router.delete('/', function (req, res) {
        var user_id = req.body.user_id;
        var target_id = req.body.target_id;

        var query = 'DELETE FROM follow WHERE(user_id=? AND target_id=?)';
        dbPool.query(query, [user_id, target_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.end('팔로우 삭제 성공');
            else res.end('팔로우 삭제 실패');
        });
    });

    return router;
}