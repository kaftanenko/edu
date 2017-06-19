package app.arduino.sereneofshame.frontend.impl.javafx.component;

import app.arduino.sereneofshame.frontend.impl.javafx.SireneOfShameJavaFXController;
import app.arduino.sereneofshame.frontend.impl.javafx.exception.util.ErrorHelper;
import app.arduino.sereneofshame.frontend.impl.javafx.resources.UIImage;
import app.arduino.sereneofshame.frontend.impl.javafx.resources.UIMessage;
import app.arduino.sereneofshame.frontend.impl.javafx.type.EConnectionState;
import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ToolBarComponent extends ToolBar {

	// ... properties

	private final Button buttonConnect;
	private final Button buttonDisconnect;
	private final ImageView imageViewStateRed;
	private final ImageView imageViewStateYellow;
	private final ImageView imageViewStateGreenBlue;

	// ... constructors

	public ToolBarComponent(final SireneOfShameJavaFXController sireneOfShameController) {

		buttonConnect = newToolBarButton(UIMessage.EMPTY, UIImage.BUTTON_CONNECT_24, new Runnable() {

			@Override
			public void run() {

				sireneOfShameController.connect();
			}
		});
		// buttonConnect.setPadding(new Insets(5));

		buttonDisconnect = newToolBarButton(UIMessage.EMPTY, UIImage.BUTTON_DISCONNECT_24, new Runnable() {

			@Override
			public void run() {

				sireneOfShameController.disconnect();
			}
		});
		// buttonDisconnect.setPadding(new Insets(5));

		final Pane rightSpacer = new Pane();
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

		imageViewStateRed = new ImageView(new Image(UIImage.JENKINS_STATE_RED_32.getAsStream()));
		imageViewStateYellow = new ImageView(new Image(UIImage.JENKINS_STATE_YELLOW_32.getAsStream()));
		imageViewStateGreenBlue = new ImageView(new Image(UIImage.JENKINS_STATE_BLUE_32.getAsStream()));

		final HBox paneJankinsState = new HBox();
		paneJankinsState.getChildren().addAll(imageViewStateRed, imageViewStateYellow, imageViewStateGreenBlue);

		getItems().addAll(buttonConnect, buttonDisconnect, rightSpacer, paneJankinsState);

		getStyleClass().removeAll();
		getStyleClass().add("tools-bar");
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
			throw ErrorHelper.handleValueIsNotSupportedYetException("connectionState", connectionState);
		}
	}

	public void resetStateSelection() {

		imageViewStateRed.setImage(new Image(UIImage.JENKINS_STATE_DISABLED_32.getAsStream()));
		imageViewStateYellow.setImage(new Image(UIImage.JENKINS_STATE_DISABLED_32.getAsStream()));
		imageViewStateGreenBlue.setImage(new Image(UIImage.JENKINS_STATE_DISABLED_32.getAsStream()));
	}

	public void updateStateSelection(final ESireneOfShameState state) {

		resetStateSelection();

		switch (state) {
		case RED:
			imageViewStateRed.setImage(new Image(UIImage.JENKINS_STATE_RED_32.getAsStream()));
			break;
		case YELLOW:
			imageViewStateYellow.setImage(new Image(UIImage.JENKINS_STATE_YELLOW_32.getAsStream()));
			break;
		case GREENBLUE:
			imageViewStateGreenBlue.setImage(new Image(UIImage.JENKINS_STATE_BLUE_32.getAsStream()));
			break;
		case UNDEFINED:
			break;
		default:
			throw ErrorHelper.handleValueIsNotSupportedYetException("state", state);
		}
	}

}
