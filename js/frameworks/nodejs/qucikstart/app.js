var express = require("express");
var app = express();

app.get("/", function(request, response){
	response.send(200);
});

module.exports = app;