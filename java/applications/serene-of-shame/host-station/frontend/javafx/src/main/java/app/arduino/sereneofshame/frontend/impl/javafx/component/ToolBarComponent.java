package app.arduino.sereneofshame.frontend.impl.javafx.component;

import app.arduino.sereneofshame.frontend.impl.javafx.SireneOfShameJavaFXController;
import app.arduino.sereneofshame.frontend.impl.javafx.exception.util.ErrorHelper;
import app.arduino.sereneofshame.frontend.impl.javafx.resources.UIImage;
import app.arduino.sereneofshame.frontend.impl.javafx.resources.UIMessage;
import app.arduino.sereneofshame.frontend.impl.javafx.type.EConnectionState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToolBarComponent extends ToolBar {

	// ... properties

	private final Button buttonConnect;
	private final Button buttonDisconnect;

	// ... constructors

	public ToolBarComponent(final SireneOfShameJavaFXController sireneOfShameController) {

		setPadding(new Insets(5));

		getStyleClass().removeAll();
		getStyleClass().add("tools-bar");

		buttonConnect = newToolBarButton(UIMessage.EMPTY, UIImage.BUTTON_CONNECT_24, new Runnable() {

			@Override
			public void run() {

				sireneOfShameController.connect();
			}
		});
		buttonDisconnect = newToolBarButton(UIMessage.EMPTY, UIImage.BUTTON_DISCONNECT_24, new Runnable() {

			@Override
			public void run() {

				sireneOfShameController.disconnect();
			}
		});

		getItems().addAll(buttonConnect, buttonDisconnect);
	}

	private Button newToolBarButton(final UIMessage message, final UIImage toolImage, final Runnable toolAction) {

		final String text = message.getText();
		final Image image = new Image(toolImage.getAsStream());

		final Button toolButton = new Button(text, new ImageView(image));
		toolButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent e) {

				runWithinUIThread(toolAction);
			}
		});
		return toolButton;
	}

	public void runWithinUIThread(final Runnable job) {

		Platform.runLater(job);
	}

	// ... business methods

	public void setConnectionState(final EConnectionState connectionState) {

		switch (connectionState) {
			case CONNECTED:
				buttonConnect.setDisable(true);
				buttonDisconnect.setDisable(false);
				break;
			case DISCONNECTED:
				buttonConnect.setDisable(false);
				buttonDisconnect.setDisable(true);
				break;
			default:
				throw ErrorHelper.handleValueIsNotSupportedYet("connectionState", connectionState);
		}
	}

}
