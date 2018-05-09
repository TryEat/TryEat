module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/:user_id/:target_id', function (req, res) {
        var user_id = req.params.user_id;
        var target_id = req.params.target_id;
        dbPool.query('INSERT INTO follow (user_id,target_id) VALUES ('+user_id+','+target_id+')', function(err, rows, fields) {
            if (err) throw err;
          });
        res.end(user_id+'\n'+target_id+'\n insert');
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

    return router;
}