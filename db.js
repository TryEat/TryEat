var mysql = require('mysql');

var dbConfig = {
    host: 'localhost',
    user: 'root',
    password: 'mytryit',
    database: 'tryeat',
    port: 3306,
    connectionLimit: 100,
    insecureAuth : true
};

var pool = mysql.createPool(dbConfig);

module.exports = pool;