var express = require('express');
var router = express.Router();

router.get('/:user_id/:restaurant_id', function (req, res) {
    var user_id = req.params.user_id;
    var restaurant_id = req.params.restaurant_id;
    res.end();
});

router.post('/', function (req, res) {
    var user_id = req.body.user_id;
    var restaurant_id = req.body.restaurant_id;
    var data = req.body.data;
    res.end();
});

router.put('/', function (req, res) {
    var user_id = req.body.user_id;
    var restaurant_id = req.body.restaurant_id;
    var data = req.body.data;
    res.end();
});

router.delete('/', function (req, res) {
    var user_id = req.body.user_id;
    var restaurant_id = req.body.restaurant_id;
    res.end();
});

module.exports = router;