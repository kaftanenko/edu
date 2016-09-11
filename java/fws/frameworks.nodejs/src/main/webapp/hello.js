var http = require("http");
var fs = require("fs");
var events = require("events");

// ... logger object

var logger = new events.EventEmitter();
logger.on('info', function(message) {

	console.log("INFO: " + message);
});

// ... server object

var server = http.createServer(function(req, resp) {

	resp.writeHead(200);

	setTimeout(function() {

		fs.readFile('hello.html', function(error, contents) {

			logger.emit('info', 'The file has been read!');

			resp.write(contents);
			resp.end();
		})
	}, 2000);

});

server.on('request', function(request, response) {
	console.log('New request coming in...');
});

server.on('close', function() {

	console.log("... the Node.js server is stopped.");
});

server.listen(8080);
console.log("The Node.js server is listening on the port 8080...");
