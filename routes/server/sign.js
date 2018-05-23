module.exports = function (_dbPool, _userTokens) {
    var express = require('express');
    var crypto = require('crypto');
    var router = express.Router();
    var dbPool = _dbPool;

    router.post('/signin', function (req, res) {
        var user_login_id = req.body.user_login_id;
        var user_pwd = req.body.user_pwd;

        var query = 'SELECT * FROM user WHERE user_login_id=? AND user_pwd=? LIMIT 1';
        dbPool.query(query, [user_login_id, user_pwd], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) {
                var token = crypto.randomBytes(64).toString('hex');
                _userTokens[rows[0].user_id.toString()] = token;
                res.status(200).json({ message: "signin success", payLoadInt: rows[0].user_id, payLoadString: token })
            }
            else res.status(401).json({ message: "signin fail" })
        });
    })

    router.post('/signout', function (req, res) {
        var id = req.headers["id"];
        var userToken = req.headers["authorization"];
        _userTokens[id] = undefined;
        res.status(200).json({ message: 'signout success' })
    })

    router.post('/signup', function (req, res) {
        var user_login_id = req.body.user_login_id;
        var user_pwd = req.body.user_pwd;
        
        var query = 'SELECT * FROM user WHERE user_login_id=? LIMIT 1';
        dbPool.query(query, [user_login_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length == 0) {
                query = 'INSERT INTO user (user_login_id ,user_pwd) VALUES (?,?)';
                dbPool.query(query, [user_login_id, user_pwd], function (err, rows, fields) {
                    if (err) throw err;
                    if (rows.affectedRows != 0) res.status(201).json({ message: "signup success" })
                    else res.status(401).json({ message: "signup fail" })
                });
            } else {
                res.status(409).json({ message: "id duplication" })
            }
        });
    })

    return router;
}