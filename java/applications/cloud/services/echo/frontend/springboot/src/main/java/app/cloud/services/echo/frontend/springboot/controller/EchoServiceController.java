package app.cloud.services.echo.frontend.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import app.cloud.services.echo.business.EchoService;

@Controller
public class EchoServiceController {

	@Autowired
	private EchoService echoService;

	@RequestMapping(path = "/echo/{message}", method = RequestMethod.GET)
	@ResponseBody
	public String echoGet(@PathVariable("message") final String message) {

		return echoService.echoMessage(message);
	}

	@RequestMapping(path = "/echo", method = RequestMethod.POST)
	@ResponseBody
	public String echoPost(@RequestBody final String message) {

		return echoService.echoMessage(message);
	}

}
