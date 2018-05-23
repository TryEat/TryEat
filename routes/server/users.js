module.exports = function (_dbPool) {
    var express = require('express');
    var crypto = require('crypto');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/', function (req, res) {
        var query = 'SELECT user_login_id FROM user';
        dbPool.query(query, [], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;

        var query = 'SELECT * FROM user WHERE user_id=? LIMIT 1';
        dbPool.query(query, [user_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows);
        });
    });

    router.put('/profile', function (req, res) {
        var user_id = req.body.user_id;
        var profile = req.body.profile;

        var query = 'UPDATE user SET profile=? WHERE user_id=?';
        dbPool.query(query, [profile, user_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.changedRows != 0) res.status(201).json({ message: "profile revise success" })
            else res.status(400).json({ message: "profile revise fail" })
        });
    });

    router.delete('/', function (req, res) {
        var user_id = req.body.user_id;
        var user_pwd = req.body.user_pwd;

        var query = 'DELETE FROM user WHERE user_id=? AND user_pwd=?';
        dbPool.query(query, [user_id, user_pwd], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.status(201).json({ message: "delete user success" })
            else res.status(400).json({ message: "delete user fail" })
        });
    });

    return router;
}