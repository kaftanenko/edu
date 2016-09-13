var request = require("supertest");
var app = require("./app");

describe("Main page tests set.", function() {

	it("Successful call to the main page...", function(done) {

		request(app)
			.get("/")
			.expect(200)
			.end(function(error) {
				if (error) throw error;
				done();
			});
	});
});

describe("Feature: cities data management service.", function() {

	it("Returning status code 200...", function(done) {
	
		request(app)
			.get("/cities")
			.expect(200, done);
	});
	
	it("Returning JSON content...", function(done) {
		request(app)
			.get("/cities")
			.expect("Content-Type", /json/)
			.expect(200, done);
	});
});
