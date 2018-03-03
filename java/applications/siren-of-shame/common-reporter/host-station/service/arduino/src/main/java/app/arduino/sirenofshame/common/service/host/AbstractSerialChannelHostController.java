package app.arduino.sirenofshame.common.service.host;

import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.closeSerialChannel;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.findSerialChannelByWellcomeMessage;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.openSerialChannel;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.readBytes;
import static app.arduino.sirenofshame.common.service.host.util.SerialChannelUtils.writeBytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import app.arduino.sirenofshame.common.service.host.event.SerialChannelEventsListener;
import app.arduino.sirenofshame.common.service.host.util.SerialChannelLogManager;
import dk.thibaut.serial.SerialChannel;

public abstract class AbstractSerialChannelHostController {

    // ... constants

    private static final String EXPECTING_WELCOME_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";

    // ... dependencies

    protected static final Logger LOG = SerialChannelLogManager.getLogger();

    // ... properties

    protected SerialChannel serialChannel;
    private String serialChannelPortName;

    private final List<SerialChannelEventsListener> eventsListeners = new ArrayList<>();

    // ... business methods

    public void connect() {

        try {

            serialChannelPortName = findSerialChannelByWellcomeMessage(EXPECTING_WELCOME_MESSAGE);
            serialChannel = openSerialChannel(serialChannelPortName);
            readMessage(); // ... read welcome message
        } catch (final Exception ex) {
            throw handleFatalException(ex);
        }
    }

    public void disconnect() {

        try {

            closeSerialChannel(serialChannel);
        } catch (final Exception ex) {
            throw handleFatalException(ex);
        }
    }

    public String getPortName() {

        return serialChannelPortName;
    }

    // ... helper methods

    protected void sendMessage(final String message) {

        try {
            writeBytes(serialChannel, message);
            notifyEventListenersAboutSentMessage(message);
        } catch (final IOException ex) {
            throw handleFatalException(ex);
        }
    }

    protected String readMessage() {

        try {

            final String message = readBytes(serialChannel);

            if (StringUtils.isNotBlank(message)) {
                notifyEventListenersAboutReceivedMessage(message);
            }

            return message;
        } catch (final IOException ex) {
            throw handleFatalException(ex);
        }
    }

    protected static RuntimeException handleFatalException(final Exception ex) {

        throw new RuntimeException(ex);
    }

    // ... events management methods

    protected void notifyEventListenersAboutSentMessage(final String message) {

        for (final SerialChannelEventsListener eventsListener : eventsListeners) {

            eventsListener.onMessageSent(message);
        }
    }

    protected void notifyEventListenersAboutReceivedMessage(final String message) {

        for (final SerialChannelEventsListener eventsListener : eventsListeners) {

            eventsListener.onMessageReceived(message);
        }
    }

    public void subscribe(final SerialChannelEventsListener eventsListener) {

        eventsListeners.add(eventsListener);
    }

    public void unsubscribe(final SerialChannelEventsListener eventsListener) {

        eventsListeners.remove(eventsListener);
    }

}
