module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/', function (req, res) {
        var query = 'SELECT user_login_id FROM user';
        dbPool.query(query, [], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) {
                var data = JSON.stringify(rows);
                res.end(data);
            }
            else res.end('없음');
        });
    });

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;

        var query = 'SELECT * FROM user WHERE user_id=? LIMIT 1';
        dbPool.query(query, [user_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) {
                var data = JSON.stringify(rows);
                res.end(data);
            }
            else res.end('없음');
        });
    });

    router.post('/signin', function (req, res) {
        var user_login_id = req.body.user_login_id;
        var user_pwd = req.body.user_pwd;

        var query = 'SELECT * FROM user WHERE user_login_id=? AND user_pwd=? LIMIT 1';
        dbPool.query(query, [user_login_id, user_pwd], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) res.end('로그인 성공');
            else res.end('로그인 실패');
        });
    })

    router.post('/signup', function (req, res) {
        var user_login_id = req.body.user_login_id;
        var user_pwd = req.body.user_pwd;
        //아이디 존재 확인
        var query = 'INSERT INTO user (user_login_id ,user_pwd) VALUES (?,?)';
        dbPool.query(query, [user_login_id, user_pwd], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.end('가입 성공');
            else res.end('가입 실패');
        });
    })

    router.put('/profile', function (req, res) {
        var user_id = req.body.user_id;
        var profile = req.body.profile;

        var query = 'UPDATE user SET profile=? WHERE user_id=?';
        dbPool.query(query, [profile, user_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.changedRows != 0) res.end('프로필 수정 성공');
            else res.end('프로필 수정 실패');
        });
    });

    router.delete('/', function (req, res) {
        var user_id = req.body.user_id;
        var user_pwd = req.body.user_pwd;

        var query = 'DELETE FROM user WHERE user_id=? AND user_pwd=?';
        dbPool.query(query, [user_id, user_pwd], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.end('삭제 성공');
            else res.end('삭제 실패');
        });
    });

    return router;
}