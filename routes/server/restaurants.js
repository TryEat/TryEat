module.exports = function (_dbPool) {
    var express = require('express');
    var router = express.Router();
    var dbPool = _dbPool;

    function generateID() {
        var _id;
        return _id;
    }

    router.get('/', function (req, res) {
        var query = 'SELECT * FROM restaurant';
        dbPool.query(query, [], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0){
                var data = JSON.stringify(rows);
                res.end(data);
            }
            else res.end('없음');
        });
    })

    router.get('/:id/byid', function (req, res) {
        var restaurant_id  = req.params.id ;

        var query = 'SELECT * FROM restaurant WHERE restaurant_id=?';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0){
                var data = JSON.stringify(rows);
                res.end(data);
            }
            else res.end('없음');
        });
    })
    router.get('/:name/byname', function (req, res) {
        var restaurant_name  = req.params.name;
        
        var query = 'SELECT * FROM restaurant WHERE restaurant_name=?';
        dbPool.query(query, [restaurant_name], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0){
                var data = JSON.stringify(rows);
                res.end(data);
            }
            else res.end('없음');
        });
    })

    router.get('/:lat/:ion/bylocation', function (req, res) {
        var locate_latitude = req.params.lat;
        var locate_longitude = req.params.ion;

        var query = 'SELECT * from restaurant where locate_latitude=? AND locate_longitude=?';
        dbPool.query(query, [locate_latitude,locate_longitude], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0){
                var data = JSON.stringify(rows);
                res.end(data);
            }
            else res.end('없음');
        });
    })

    router.get('/is_exist/:name/byname', function (req, res) {
        var restaurant_name  = req.params.name;

        var query = 'SELECT EXISTS (select * from restaurant where restaurant_name=?) as success';
        dbPool.query(query, [restaurant_name], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].success == 1) {
                res.end('대상 있음');
            }
            else res.end('대상 없음');
        });
    })

    router.get('/is_exist/:lat/:ion/location', function (req, res) {
        var locate_latitude = req.params.lat;
        var locate_longitude = req.params.ion;

        var query = 'SELECT EXISTS (select * from restaurant where locate_latitude=? AND locate_longitude=?) as success';
        dbPool.query(query, [locate_latitude,locate_longitude], function (err, rows, fields) {
            if (err) throw err;
            if (rows[0].success == 1) {
                res.end('대상 있음');
            }
            else res.end('대상 없음');
        });
        res.end(req.params.name);
    })

    router.get('/count/:id', function (req, res) {
        var restaurant_id  = req.params.restaurant_id ;
        
        var query = ' SELECT review_count FROM restaurant WHERE restaurant_id=?';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.length != 0) res.end('리뷰 개수 : '+rows[0].review_count );
            else res.end('리뷰 없음');
        });
    });

    router.post('/', function (req, res) {
        var img_id = req.body.img_id;
        var restaurant_name = req.body.restaurant_name;
        var locate_latitude = req.body.locate_latitude;
        var locate_longitude = req.body.locate_longitude;
        var restaurant_desc = req.body.restaurant_desc;
        var open_time = req.body.open_time;
        var close_time = req.body.close_time;
        
        var query = 'INSERT INTO restaurant (img_id,restaurant_name,locate_latitude,locate_longitude,restaurant_desc,open_time,close_time,review_count,average) VALUES (?,?,?,?,?,?,?,0,0)';
        dbPool.query(query, [img_id, restaurant_name,locate_latitude,locate_longitude,restaurant_desc,open_time,close_time,0,0], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.end('음식점 등록 성공');
            else res.end('음식점 등록 실패');
        });
    });

    router.put('/', function (req, res) {
        var restaurant_id  = req.body.restaurant_id ;
        var img_id = req.body.img_id;
        var restaurant_name = req.body.restaurant_name;
        var locate_latitude = req.body.locate_latitude;
        var locate_longitude = req.body.locate_longitude;
        var restaurant_desc = req.body.restaurant_desc;
        var open_time = req.body.open_time;
        var close_time = req.body.close_time;

        var query = 'UPDATE restaurant SET img_id=?,restaurant_name=?,locate_latitude=?,locate_longitude=?,restaurant_desc=?,open_time=?,close_time=? WHERE restaurant_id=?';
        dbPool.query(query, [img_id, restaurant_name,locate_latitude,locate_longitude,restaurant_desc,open_time,close_time,restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.changedRows != 0) res.end('음식점 수정 성공');
            else res.end('음식점 수정 실패');
        });
    });

    router.delete('/', function (req, res) {
        var restaurant_id  = req.body.restaurant_id ;

        var query = 'DELETE FROM restaurant WHERE restaurant_id=?';
        dbPool.query(query, [restaurant_id], function (err, rows, fields) {
            if (err) throw err;
            if (rows.affectedRows != 0) res.end('음식점 삭제 성공');
            else res.end('음식점 삭제 실패');
        });
    });

    return router;
}