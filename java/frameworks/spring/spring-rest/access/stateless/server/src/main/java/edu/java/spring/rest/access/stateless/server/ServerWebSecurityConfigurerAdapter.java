package edu.java.spring.rest.access.stateless.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.java.spring.rest.access.stateless.server.filter.GreetingServiceRestFilter;

@Configuration
@EnableWebSecurity
public class ServerWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	// ... business methods

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.addFilterBefore(new GreetingServiceRestFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
