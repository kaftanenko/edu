var request = require("supertest");
var app = require("./app");

request(app)
	.get("/")
	.expect(200)
	.end(function (error) {
		if (error) throw error;
		console.log("Done");
	});
