// 스키마 파일 이름은 맘대로 바꿔도 됨
// 이름을 바꾸면 db-route.js 에서도 이름을 바꿀 것

var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var sampleSchema = new Schema({
    name: String,
    age: Number
});

module.exports = mongoose.model('sample',sampleSchema);