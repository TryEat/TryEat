var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');

app.use('/restaurants',require('./routes/server/restaurants'));
app.use('/follows',require('./routes/server/follows'));
app.use('/Reviews',require('./routes/server/Reviews'));

var connection = mysql.createConnection({
	host: 'localhost',
	user: 'root',
	password: 'mytryit',
	database: 'tryeat'
});

connection.connect();

connection.query('SELECT * FROM user', function(err, result, fields){
	if(!err)
		console.log('The solution is : \n', result);
	else
		console.log('Error while performing Query.', err);
});

connection.end();

app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;

app.get('/', function (req, res) {
  res.send('Hello World!');
});
 
var server = app.listen(port, function () {
  console.log('서버 동작중.' + port);
});