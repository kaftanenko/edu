package app.cloud.services.echo.frontend.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import app.cloud.services.echo.business.EchoService;
import app.cloud.services.echo.business.EchoServiceImpl;

@ComponentScan
@EnableAutoConfiguration
public class EchoServiceLauncher {

	@Bean
	public EchoService echoService() {

		return new EchoServiceImpl();
	}

	public static void main(final String[] args) throws Exception {

		SpringApplication.run(EchoServiceLauncher.class, args);
	}
}
