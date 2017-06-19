package app.arduino.sirenofshame.frontend.impl.javafx.component;

import java.util.HashMap;
import java.util.Map;

import app.arduino.sirenofshame.frontend.impl.javafx.SirenOfShameJavaFXController;
import app.arduino.sirenofshame.frontend.impl.javafx.exception.util.ErrorHelper;
import app.arduino.sirenofshame.frontend.impl.javafx.resources.UIMessage;
import app.arduino.sirenofshame.service.host.api.ESirenOfShameAlarmLevel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ControlBoardComponent extends VBox {

	// ... properties

	private final Map<ESirenOfShameAlarmLevel, Button> buttonsSetAlarmLevelTo = new HashMap<>();

	// ... constructors

	public ControlBoardComponent(final SirenOfShameJavaFXController sirenOfShameController) {

		setSpacing(5);
		setPadding(new Insets(5));

		for (final ESirenOfShameAlarmLevel alarmLevel : ESirenOfShameAlarmLevel.values()) {

			if (alarmLevel == ESirenOfShameAlarmLevel.UNDEFINED) {
				continue;
			}

			final String buttonDisplayText = UIMessage.BUTTON_SET_TO_ALARM_LEVEL
					.getText(alarmLevel.name().toLowerCase());
			final Button buttonSetAlarmLevelTo = new Button(buttonDisplayText);
			buttonSetAlarmLevelTo.setOnAction(e -> {
				sirenOfShameController.setAlarmLevelTo(alarmLevel);
			});

			buttonSetAlarmLevelTo.setMinWidth(270);
			buttonSetAlarmLevelTo.getStyleClass().removeAll();
			buttonSetAlarmLevelTo.getStyleClass().add("buttonSetAlarmLevelTo");

			final String bgColor;
			switch (alarmLevel) {
				case RED:
					bgColor = "red";
					break;
				case RED_EXPECTING_UPDATE:
					bgColor = "red";
					break;
				case YELLOW:
					bgColor = "yellow";
					break;
				case YELLOW_EXPECTING_UPDATE:
					bgColor = "yellow";
					break;
				case GREENBLUE:
					bgColor = "blue";
					break;
				case GREENBLUE_EXPECTING_UPDATE:
					bgColor = "blue";
					break;
				default:
					throw ErrorHelper.handleValueIsNotSupportedYetException("alarLevel", alarmLevel);
			}
			buttonSetAlarmLevelTo.setStyle("-fx-text-fill: " + bgColor);

			buttonsSetAlarmLevelTo.put(alarmLevel, buttonSetAlarmLevelTo);
			getChildren().add(buttonSetAlarmLevelTo);
		}

	}

	// ... business methods

	public void resetAlarmLevelSelection() {

		buttonsSetAlarmLevelTo.values().stream().forEach(b -> b.setDisable(true));
	}

	public void updateAlarmLevelSelection(final ESirenOfShameAlarmLevel alarmLevel) {

		buttonsSetAlarmLevelTo.values().stream().forEach(b -> b.setDisable(false));
	}

}
