var express = require('express');
var router = express.Router();

router.get('/:user_id/:target_id', function (req, res) {
    var user_id = req.params.user_id;
    var restaurant_id = req.params.target_id;
    res.end();
});

router.post('/', function (req, res) {
    var user_id = req.body.user_id;
    var target_id = req.body.target_id;
    res.end();
});

router.delete('/', function (req, res) {
    var user_id = req.body.user_id;
    var target_id = req.body.target_id;
    res.end();
});

module.exports = router;