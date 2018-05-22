module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/', function (req, res) {
        res.end();
    });

    router.post('/', function (req, res) {
        res.end();
    })

    router.put('/', function (req, res) {
        res.end();
    });

    router.delete('/', function (req, res) {
        res.end();
    });

    return router;
}