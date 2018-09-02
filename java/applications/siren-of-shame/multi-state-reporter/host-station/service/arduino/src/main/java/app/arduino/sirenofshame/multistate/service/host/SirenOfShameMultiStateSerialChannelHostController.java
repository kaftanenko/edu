package app.arduino.sirenofshame.multistate.service.host;

import app.arduino.sirenofshame.common.service.host.SerialChannelHostController;

public class SirenOfShameMultiStateSerialChannelHostController extends SerialChannelHostController {

  // ... constants

  public SirenOfShameMultiStateSerialChannelHostController() {

    super(true);
  }

  private static final String COMMAND__DELETE = "DELETE";
  private static final String COMMAND__GET = "GET";
  private static final String COMMAND__PING = "PING";
  private static final String COMMAND__POST = "POST";
  private static final String COMMAND__PUT = "PUT";

  private static final String COMMAND__RESPONSE_SUCCEEDED = "SUCCEEDED";

  // ... business methods

  public boolean createResource(final String resourcePath) {

    sendCommand(COMMAND__PUT, resourcePath);

    final String responseMessage = receiveMessage();
    return responseMessage.contains(COMMAND__RESPONSE_SUCCEEDED);
  }

  public boolean deleteResource(final String resourcePath) {

    sendCommand(COMMAND__DELETE, resourcePath);

    final String responseMessage = receiveMessage();
    return responseMessage.contains(COMMAND__RESPONSE_SUCCEEDED);
  }

  public boolean ping() {

    sendCommand(COMMAND__PING, "");

    final String responseMessage = receiveMessage();
    return responseMessage.contains(COMMAND__RESPONSE_SUCCEEDED);
  }

  public byte[] getResource(final String resourcePath) {

    sendCommand(COMMAND__GET, resourcePath);

    final byte[] resourceContent = receiveData();
    return resourceContent;
  }

  public boolean uploadResource(final String resourcePath, final byte[] resourceContent) {

    sendCommand(COMMAND__POST, resourcePath);
    sendData(resourceContent);

    final String responseMessage = receiveMessage();
    return responseMessage.contains(COMMAND__RESPONSE_SUCCEEDED);
  }

  public boolean uploadResource(final String resourcePath, final String resourceContent) {

    sendCommand(COMMAND__POST, resourcePath);
    sendMessage(resourceContent);

    final String responseMessage = receiveMessage();
    return responseMessage.contains(COMMAND__RESPONSE_SUCCEEDED);
  }

  // ... helper methods

  private void sendCommand(final String commandDelete, final String resourcePath) {

    final String message = String.format("%s %s\n", commandDelete, resourcePath);
    sendMessage(message);
  }

}
