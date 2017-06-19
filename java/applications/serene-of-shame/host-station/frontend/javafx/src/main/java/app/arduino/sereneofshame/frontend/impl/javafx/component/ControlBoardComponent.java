package app.arduino.sereneofshame.frontend.impl.javafx.component;

import java.util.HashMap;
import java.util.Map;

import app.arduino.sereneofshame.frontend.impl.javafx.SireneOfShameJavaFXController;
import app.arduino.sereneofshame.frontend.impl.javafx.exception.util.ErrorHelper;
import app.arduino.sereneofshame.service.host.api.ESireneOfShameAlarmLevel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ControlBoardComponent extends VBox {

	// ... properties

	private final Map<ESireneOfShameAlarmLevel, Button> buttonsSetAlarmLevelTo = new HashMap<>();

	// ... constructors

	public ControlBoardComponent(final SireneOfShameJavaFXController sireneOfShameController) {

		setSpacing(5);
		setPadding(new Insets(5));

		for (final ESireneOfShameAlarmLevel alarmLevel : ESireneOfShameAlarmLevel.values()) {

			if (alarmLevel == ESireneOfShameAlarmLevel.UNDEFINED) {
				continue;
			}

			final Button buttonSetAlarmLevelTo = new Button(alarmLevel.name());
			buttonSetAlarmLevelTo.setOnAction(e -> {
				sireneOfShameController.setAlarmLevelTo(alarmLevel);
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
					throw ErrorHelper.handleValueIsNotSupportedYetException("state", alarmLevel);
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

	public void updateAlarmLevelSelection(final ESireneOfShameAlarmLevel alarmLevel) {

		buttonsSetAlarmLevelTo.values().stream().forEach(b -> b.setDisable(false));
	}

}
