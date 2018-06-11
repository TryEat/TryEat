module.exports = function (_dbPool) {
    var express = require('express');
    var crypto = require('crypto');
    var router = express.Router();
    var dbPool = _dbPool;

    router.get('/:user_id', function (req, res) {
        var user_id = req.params.user_id;

        var spawn = require("child_process").spawn;
     
        var process = spawn('python',["./deep/recommand.py",user_id,4,8] );
      
        process.stdout.on('data', function(data) {
            var idlist = data.toString().split(',').map(function(item) {
                return parseInt(item, 10);
            });
            var query = 'SELECT * FROM restaurant WHERE restaurant_id IN (?)';
            dbPool.query(query, [idlist], function (err, rows, fields) {
                if (err) throw err;
                res.status(200).json(rows);
            });
        })    
    });

    return router;
}