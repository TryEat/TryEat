module.exports = function (_dbPool) {
    var express = require('express');
    var crypto = require('crypto');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;

        var query = 'SELECT * FROM user WHERE user_id=? LIMIT 1';
        dbPool.query(query, [user_id], function (err, rows, fields) {
            if (err) throw err;
            res.status(200).json(rows[0]);
        });
    });

    return router;
}