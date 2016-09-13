var express = require("express");
var http = require("http");
var io = require("socket.io");
var redis = require("redis");
var url = require("url");
var prettyjson = require('prettyjson');
// var $ = jQuery = require('jquery')(window);

// ... server/routing

var app = express();

app.get("/hello", function(request, response) {

	response.sendFile(__dirname + "/hello.html");
});

app.get("/index.html", function(request, response) {

	response.sendFile(__dirname + "/index.html");
});

app.get("/tweets/:username", function(request, response) {

	var username = request.params.username;

	response.locals = {
		name : username,
		tweeter : {}
	};
	response.render("twitter.ejs");
});

var server = app.listen(8080);

// ... data base

var redisClient = redis.createClient("redis://192.168.99.100:32768");
// localhost:6379

redisClient.get("message2", function(error, data) {

	console.log(data);
});

function db_GetLastMessages(limit, callback) {

	// redisClient.ltrim("messages", 0, 0);

	return redisClient.lrange("messages", 0, limit, function(error, messagesAsString) {

		callback(error, messagesAsString);
	});
}

function db_StoreMessage(message) {

	var messageAsString = JSON.stringify(message);

	redisClient.lpush("messages", messageAsString, function(error, reply) {

		console.log(reply);
	});
}

// ... socket driven communication

io.listen(server).on("connection", function(client) {

	client.on("join", function(name) {

		client.name = name;

		db_GetLastMessages(10, function(error, messagesAsString) {

			messagesAsString.reverse();
			messagesAsString.forEach(function(messageAsString) {

				var message = JSON.parse(messageAsString);
				client.emit("message", message);
			});
		});
	});

	client.on("messageText", function(messageText) {

		var message = {

			name : client.name,
			messageText : messageText
		};

		console.log("Message received: " + prettyjson.render(message));

		db_StoreMessage(message);

		client.emit("message", message);
		client.broadcast.emit("message", message);
	});

});
