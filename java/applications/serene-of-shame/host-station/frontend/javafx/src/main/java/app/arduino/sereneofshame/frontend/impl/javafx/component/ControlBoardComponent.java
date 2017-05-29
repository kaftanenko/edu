package app.arduino.sereneofshame.frontend.impl.javafx.component;

import java.util.HashMap;
import java.util.Map;

import app.arduino.sereneofshame.frontend.impl.javafx.SireneOfShameJavaFXController;
import app.arduino.sereneofshame.frontend.impl.javafx.exception.util.ErrorHelper;
import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ControlBoardComponent extends VBox {

	// ... properties

	private final Map<ESireneOfShameState, Button> buttonsSetStateTo = new HashMap<>();

	// ... constructors

	public ControlBoardComponent(final SireneOfShameJavaFXController sireneOfShameController) {

		setSpacing(5);
		setPadding(new Insets(5));

		for (final ESireneOfShameState state : ESireneOfShameState.values()) {

			final Button buttonSetStateTo = new Button(state.name());
			buttonSetStateTo.setOnAction(e -> {
				sireneOfShameController.setState(state);
			});

			buttonSetStateTo.setMinWidth(240);
			buttonSetStateTo.getStyleClass().removeAll();
			buttonSetStateTo.getStyleClass().add("buttonSetStateTo");

			final String bgColor;
			switch (state) {
				case RED:
					bgColor = "red";
					break;
				case YELLOW:
					bgColor = "yellow";
					break;
				case GREENBLUE:
					bgColor = "blue";
					break;
				default:
					throw ErrorHelper.handleValueIsNotSupportedYet("state", state);
			}
			buttonSetStateTo.setStyle("-fx-text-fill: " + bgColor);

			buttonsSetStateTo.put(state, buttonSetStateTo);
			getChildren().add(buttonSetStateTo);
		}

	}

	// ... business methods

	public void resetStateSelection() {

		buttonsSetStateTo.values().stream().forEach(b -> b.setDisable(true));
	}

	public void updateStateSelection(final ESireneOfShameState state) {

		buttonsSetStateTo.values().stream().forEach(b -> b.setDisable(false));
	}

}
