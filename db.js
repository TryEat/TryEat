var mysql = require('mysql'); 

var dbConfig = {
    host: 'localhost',
    user: 'root',
    password: '1234',
    database: 'tryeat',
    port: 3306,
    connectionLimit: 100,
    multipleStatements : true,
    insecureAuth: true
};

var pool = mysql.createPool(dbConfig); 

module.exports = pool;