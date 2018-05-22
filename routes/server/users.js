module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/', function (req, res) {
        var query = 'SELECT user_login_id FROM user';
        dbPool.query(query, [], function (err, rows, fields) {
            if (err) throw err;
            var data = JSON.stringify(rows);
            res.status(200).json(data);
        });
    });

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;

        var query = 'SELECT * FROM user WHERE user_id=? LIMIT 1';
        dbPool.query(query, [user_id], function (err, rows, fields) {
            if (err) throw err;
            var data = JSON.stringify(rows);
            res.status(200).json(data);
        });
    });

    router.post('/signin', function (req, res) {
        var user_login_id = req.body.user_login_id;
        var user_pwd = req.body.user_pwd;

        var query = 'SELECT * FROM user WHERE user_login_id=? AND user_pwd=? LIMIT 1';
        dbPool.query(query, [user_login_id, user_pwd], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) res.status(200).json({message: "signin success"})
            else res.status(401).json({message: "signin fail"})
        });
    })

    router.post('/signup', function (req, res) {
        var user_login_id = req.body.user_login_id;
        var user_pwd = req.body.user_pwd;
        var query = 'SELECT * FROM user WHERE user_login_id=? LIMIT 1';
        dbPool.query(query, [user_login_id],function(err,rows,fields){
            if(rows.length ==0){
                query = 'INSERT INTO user (user_login_id ,user_pwd) VALUES (?,?)';
                dbPool.query(query, [user_login_id, user_pwd], function (err, rows, fields) {
                    if (err) throw err;
                    if (rows.affectedRows != 0)  res.status(201).json({message: "signup success"})
                    else res.status(401).json({message: "signup fail"})
                });
            }else{
                res.status(409).json({message: "signup fail"})
            }
        });
    })

    router.put('/profile', function (req, res) {
        var user_id = req.body.user_id;
        var profile = req.body.profile;

        var query = 'UPDATE user SET profile=? WHERE user_id=?';
        dbPool.query(query, [profile, user_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.changedRows != 0) res.status(201).json({message: "profile revise success"})
            else res.status(400).json({message: "profile revise fail"})
        });
    });

    router.delete('/', function (req, res) {
        var user_id = req.body.user_id;
        var user_pwd = req.body.user_pwd;

        var query = 'DELETE FROM user WHERE user_id=? AND user_pwd=?';
        dbPool.query(query, [user_id, user_pwd], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.status(201).json({message: "delete user success"})
            else res.status(400).json({message: "delete user fail"})
        });
    });

    return router;
}