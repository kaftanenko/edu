package app.cloud.services.echo.business;

public class EchoServiceImpl implements EchoService {

	@Override
	public String echoMessage(final String message) {

		return message;
	}

}
