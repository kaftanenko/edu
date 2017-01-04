package edu.java.spring.boot.helloworld.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

	public String getHelloWorldMessage() {

		return "Hello World!";
	}

}
