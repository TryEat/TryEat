module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    function generateID() {
        var _id;
        return _id;
    }

    router.get('/', function (req, res) {
        res.end()
    })

    router.get('/:id/byid', function (req, res) {
        res.end(req.params.id);
    })
    router.get('/:name/byname', function (req, res) {
        res.end(req.params.name);
    })
    router.get('/:lat/:ion/bylocation', function (req, res) {
        res.end(req.params.lat + '\n' + req.params.ion);
    })

    router.get('/is_exist/:name/name', function (req, res) {
        res.end(req.params.name);
    })
    router.get('/is_exist/:lat/:ion/location', function (req, res) {
        res.end(req.params.lat + '\n' + req.params.ion);
    })

    router.get('/count/:id', function (req, res) {
        res.end();
    });

    router.post('/', function (req, res) {
        var id = generateID();
        var information = req.body.information;
        res.end()
    });

    router.put('/', function (req, res) {
        var id = req.body.id;
        var information = req.body.information;
        res.end();
    });

    router.delete('/', function (req, res) {
        var id = req.body.id;
        res.end();
    });

    return router;
}