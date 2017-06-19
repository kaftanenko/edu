package app.arduino.sirenofshame.frontend.impl.javafx.component;

import app.arduino.sirenofshame.frontend.impl.javafx.SirenOfShameJavaFXController;
import app.arduino.sirenofshame.frontend.impl.javafx.exception.util.ErrorHelper;
import app.arduino.sirenofshame.frontend.impl.javafx.resources.UIImage;
import app.arduino.sirenofshame.frontend.impl.javafx.resources.UIMessage;
import app.arduino.sirenofshame.frontend.impl.javafx.type.EConnectionState;
import app.arduino.sirenofshame.service.host.api.ESirenOfShameAlarmLevel;
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

	private final ImageView imageViewAlarmLevelRed;
	private final ImageView imageViewAlarmLevelYellow;
	private final ImageView imageViewAlarmLevelGreenBlue;

	// ... constructors

	public ToolBarComponent(final SirenOfShameJavaFXController sirenOfShameController) {

		buttonConnect = newToolBarButton(UIMessage.EMPTY, UIImage.BUTTON_CONNECT_24, new Runnable() {

			@Override
			public void run() {

				sirenOfShameController.connect();
			}
		});
		// buttonConnect.setPadding(new Insets(5));

		buttonDisconnect = newToolBarButton(UIMessage.EMPTY, UIImage.BUTTON_DISCONNECT_24, new Runnable() {

			@Override
			public void run() {

				sirenOfShameController.disconnect();
			}
		});
		// buttonDisconnect.setPadding(new Insets(5));

		final Pane rightSpacer = new Pane();
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

		imageViewAlarmLevelRed = new ImageView(new Image(UIImage.JENKINS_STATE_RED_32.getAsStream()));
		imageViewAlarmLevelYellow = new ImageView(new Image(UIImage.JENKINS_STATE_YELLOW_32.getAsStream()));
		imageViewAlarmLevelGreenBlue = new ImageView(new Image(UIImage.JENKINS_STATE_BLUE_32.getAsStream()));

		final HBox paneJankinsState = new HBox();
		paneJankinsState.getChildren().addAll(imageViewAlarmLevelRed, imageViewAlarmLevelYellow,
				imageViewAlarmLevelGreenBlue);

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

		imageViewAlarmLevelRed.setImage(new Image(UIImage.JENKINS_STATE_DISABLED_32.getAsStream()));
		imageViewAlarmLevelYellow.setImage(new Image(UIImage.JENKINS_STATE_DISABLED_32.getAsStream()));
		imageViewAlarmLevelGreenBlue.setImage(new Image(UIImage.JENKINS_STATE_DISABLED_32.getAsStream()));
	}

	public void updateStateSelection(final ESirenOfShameAlarmLevel alarmLevel) {

		resetStateSelection();

		switch (alarmLevel) {
			case RED:
				imageViewAlarmLevelRed.setImage(new Image(UIImage.JENKINS_STATE_RED_32.getAsStream()));
				break;
			case RED_EXPECTING_UPDATE:
				imageViewAlarmLevelRed.setImage(new Image(UIImage.JENKINS_STATE_RED_ANIME_32.getAsStream()));
				break;
			case YELLOW:
				imageViewAlarmLevelYellow.setImage(new Image(UIImage.JENKINS_STATE_YELLOW_32.getAsStream()));
				break;
			case YELLOW_EXPECTING_UPDATE:
				imageViewAlarmLevelYellow.setImage(new Image(UIImage.JENKINS_STATE_YELLOW_ANIME_32.getAsStream()));
				break;
			case GREENBLUE:
				imageViewAlarmLevelGreenBlue.setImage(new Image(UIImage.JENKINS_STATE_BLUE_32.getAsStream()));
				break;
			case GREENBLUE_EXPECTING_UPDATE:
				imageViewAlarmLevelGreenBlue.setImage(new Image(UIImage.JENKINS_STATE_BLUE_ANIME_32.getAsStream()));
				break;
			case UNDEFINED:
				break;
			default:
				throw ErrorHelper.handleValueIsNotSupportedYetException("alarmLevel", alarmLevel);
		}
	}

}
