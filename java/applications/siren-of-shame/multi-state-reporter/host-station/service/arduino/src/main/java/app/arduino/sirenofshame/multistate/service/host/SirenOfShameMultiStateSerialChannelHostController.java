package app.arduino.sirenofshame.multistate.service.host;

import app.arduino.sirenofshame.common.service.host.SerialChannelHostController;

public class SirenOfShameMultiStateSerialChannelHostController extends SerialChannelHostController {

  // ... constants

  public SirenOfShameMultiStateSerialChannelHostController() {

    super(true);
  }

  private static final String COMMAND__CLEAR = "CLEAR";
  private static final String COMMAND__RESPONSE_SUCCEEDED = "SUCCEEDED";

  // ... business methods

  public boolean uploadResource(final String resource) {

    sendMessage(resource);

    final String responseMessage = readMessage();
    return COMMAND__RESPONSE_SUCCEEDED.equals(responseMessage);
  }

  public boolean clearResource() {

    sendMessage(COMMAND__CLEAR);

    final String responseMessage = readMessage();
    return COMMAND__RESPONSE_SUCCEEDED.equals(responseMessage);
  }

}
