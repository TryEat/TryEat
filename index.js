var express = require('express');
var app = express();
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;

var userTokens = [];

var printMessage = function(req,res,next){
  console.log();
  console.log(req);
  console.log(req.body);
  next();
}

var verifyUser = function (req, res, next) {
  if (userTokens[req.headers["id"]] == undefined || userTokens[req.headers["id"]] != req.headers["authorization"]) {
    console.log("need");
  } else {
    console.log("don't need");
    next();
  }
}

var pool = require('./db');

app.get('/', function (req, res) {
  res.end("hello");
});

app.use(printMessage);
app.use('/sign', require('./routes/server/sign')(pool, userTokens));
app.use('/recommends', require('./routes/server/recommends')(pool, userTokens));
app.use(verifyUser);
app.use('/users', require('./routes/server/users')(pool, userTokens));
app.use('/restaurants', require('./routes/server/restaurants')(pool));
app.use('/follows', require('./routes/server/follows')(pool));
app.use('/reviews', require('./routes/server/reviews')(pool));
app.use('/request', require('./routes/server/user-requests')(pool));
app.use('/ad', require('./routes/server/ad')(pool));

var server = app.listen(port, function () {
  console.log('서버 동작중.' + port);
});

function myFunc() {
  console.log((new Date()).toLocaleString() + " SVD 추천 정보 갱신 시작")
  var process = require("child_process").spawn('python',["./deep/svd_train_val.py"]);
  process.on("exit",function (code) {
    console.log((new Date()).toLocaleString() + " SVD 추천 정보 갱신 끝")
    setTimeout(() => {
      myFunc();
    }, 3000); //5초
  });
}

//myFunc();