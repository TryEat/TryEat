//sample 코드
//서버 내부에서, 외부에서 전부 호출 가능해야함
//index.js 에는 호출 테스트를 위한 코드만 작성해야함
//sample.js 파일에 스키마 ( 테이블 ) 선언 후 사용 

var mongoose = require('mongoose');
mongoose.Promise = global.Promise;
var sample = require('../models/sample')

mongoose.connect('mongodb://localhost:27017/sample');
var db = mongoose.connection;

var express

db.on('error',function(){
    console.log('Connection Failed!');
});

db.once('open', function() {
    console.log('Connected!');
});

module.exports = function(){
    var express = require('express');
	var router = express.Router();
    router.get('/books', function(req,res){
        res.end('Hello!');
    });

    // GET SINGLE BOOK
    router.get('/books/:book_id', function(req, res){
        Book.findOne({_id: req.params.book_id}, function(err, book){
            if(err) return res.status(500).json({error: err});
            if(!book) return res.status(404).json({error: 'book not found'});
            res.json(book);
        })
    });

    // GET BOOK BY AUTHOR
    router.get('/books/author/:author', function(req, res){
        res.end();
    });

    // CREATE BOOK
    router.post('/books', function(req, res){
        var book = new Book();
        book.title = req.body.title;
        book.author = req.body.author;
        book.published_date = new Date(req.body.published_date);

        book.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json({result: 1});

        });
    });

    // UPDATE THE BOOK
    router.put('/books/:book_id', function(req, res){
        res.end();
    });

    // DELETE BOOK
    router.delete('/books/:book_id', function(req, res){
        res.end();
    });
    return router;
}