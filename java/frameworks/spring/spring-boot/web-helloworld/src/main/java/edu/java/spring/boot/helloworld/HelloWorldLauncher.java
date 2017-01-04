package edu.java.spring.boot.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class HelloWorldLauncher {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(HelloWorldLauncher.class, args);
	}
}
