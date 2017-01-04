package edu.java.spring.boot.helloworld.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.java.spring.boot.helloworld.service.HelloWorldService;

@Controller
public class HelloWorldController {

	@Autowired
	private HelloWorldService helloWorldService;

	@RequestMapping("/")
	@ResponseBody
	public String home() {

		return helloWorldService.getHelloWorldMessage();
	}

}
