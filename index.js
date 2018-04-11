var express = require('express');
var app = express();
var bodyParser = require('body-parser');

var dbrouter = require('./routes/db-route.js');
app.use('/db',dbrouter);

app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());
 
var port = process.env.PORT || 8080;

app.get('/', function (req, res) {
  res.send('Hello World!');
});
 
var server = app.listen(port, function () {
  console.log('서버 동작중.' + port);
});