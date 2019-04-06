package app.arduino.sirenofshame.frontend.javafx.component;

import java.util.HashMap;
import java.util.Map;

import app.arduino.sirenofshame.frontend.javafx.SirenOfShameJavaFXController;
import app.arduino.sirenofshame.frontend.javafx.exception.util.ErrorHelper;
import app.arduino.sirenofshame.frontend.javafx.resources.UIMessage;
import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;
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

      final String buttonDisplayText = UIMessage.BUTTON_SET_TO_ALARM_LEVEL.getText(alarmLevel.name().toLowerCase());
      final Button newButtonSetAlarmLevelTo = new Button(buttonDisplayText);
      newButtonSetAlarmLevelTo.setOnAction(e -> {
        sirenOfShameController.updateAlarmLevelTo(alarmLevel);
      });

      newButtonSetAlarmLevelTo.setMinWidth(270);
      newButtonSetAlarmLevelTo.getStyleClass().removeAll();
      newButtonSetAlarmLevelTo.getStyleClass().add("buttonSetAlarmLevelTo");

      final String bgColor;
      switch (alarmLevel) {
        case RED:
        case RED_EXPECTING_UPDATE:
          bgColor = "red";
          break;
        case YELLOW:
        case YELLOW_EXPECTING_UPDATE:
          bgColor = "yellow";
          break;
        case GREENBLUE:
        case GREENBLUE_EXPECTING_UPDATE:
          bgColor = "blue";
          break;
        default:
          throw ErrorHelper.handleValueIsNotSupportedYetException("alarLevel", alarmLevel);
      }
      newButtonSetAlarmLevelTo.setStyle("-fx-text-fill: " + bgColor);

      buttonsSetAlarmLevelTo.put(alarmLevel, newButtonSetAlarmLevelTo);
      getChildren().add(newButtonSetAlarmLevelTo);
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
