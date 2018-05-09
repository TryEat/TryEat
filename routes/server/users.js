module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/', function (req, res) {
        res.end();
    });

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;
        res.end();
    });

    router.post('/', function (req, res) {
        var restaurant_id = req.body.restaurant_id;
        var data = req.body.data;
        res.end();
    });

    router.post('/signin', function (req, res) {
        var user_login_id = req.body.user_login_id;
        var user_pwd = req.body.user_pwd;
        var query = 'SELECT * FROM user WHERE user_login_id=? AND user_pwd=?';
        dbPool.query(query,[user_login_id,user_pwd], function (err, rows, fields) {
            if (err) throw err;
            if(rows.length!=0)res.end('로그인 성공');
            else res.end('로그인 실패');
        });
    })

    router.post('/signup', function (req, res) {
        var user_login_id = req.body.user_login_id;
        var user_pwd = req.body.user_pwd;
        //아이디 존재 확인
        var query = 'INSERT INTO user (user_login_id ,user_pwd) VALUES (?,?)';
        dbPool.query(query,[user_login_id,user_pwd], function (err, rows, fields) {
            if (err) throw err;
            res.end('가입성공');
        });
    })

    router.put('/', function (req, res) {
        var user_id = req.body.user_id;
        var restaurant_id = req.body.restaurant_id;
        var data = req.body.data;
        res.end();
    });

    router.delete('/', function (req, res) {
        var user_id = req.body.user_id;
        res.end();
    });

    return router;
}