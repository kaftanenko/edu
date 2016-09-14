var express = require("express");
var app = express();

app.get("/", function(request, response) {

	response.send("Ok");
});


app.get("/cities", function(request, response) {

	response.json(200);
});

module.exports = app;
