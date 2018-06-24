var express = require('express');
var app = express();
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;

var userTokens = [];

var printMessage = function (req, res, next) {
  console.log();
  console.log(req);
  console.log(req.body);
  next();
}

var verifyUser = function (req, res, next) {
  if (userTokens[req.headers["id"]] == undefined || userTokens[req.headers["id"]] != req.headers["authorization"]) {
    console.log("fail token check");
  } else {
    console.log("success token check");
    next();
  }
}

var pool = require('./db');

app.get('/', function (req, res) {
  res.end("hello");
});

app.use(printMessage);
app.use('/sign', require('./routes/server/sign')(pool, userTokens));
//app.use(verifyUser);
app.use('/users', require('./routes/server/users')(pool));
app.use('/restaurants', require('./routes/server/restaurants')(pool));
app.use('/bookmarks', require('./routes/server/bookmarks')(pool));
app.use('/reviews', require('./routes/server/reviews')(pool));

var server = app.listen(port, function () {
  console.log('서버 동작중.' + port);
  myFunc();
});

function myFunc() {
  console.log((new Date()).toLocaleString() + " 리뷰 데이터 추출 시작")
  var makeData = require("child_process").spawn('python', ["./deep/makeData.py"]);
  makeData.on("exit", function (code) {
    console.log((new Date()).toLocaleString() + " 리뷰 데이터 추출 끝")
    console.log((new Date()).toLocaleString() + " SVD 추천 정보 갱신 시작")
    var train = require("child_process").spawn('python', ["./deep/svd_train_val.py"]);
    train.stdout.on("data",function(data){
      console.log("오차 " + data)
    });
    train.on("exit", function (code) {
      console.log((new Date()).toLocaleString() + " SVD 추천 정보 갱신 끝")
      setTimeout(() => {
        myFunc();
      }, 10000); //10초
    });
  });
}
