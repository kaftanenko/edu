package app.arduino.sirenofshame.frontend.impl.javafx.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusLineComponent extends HBox {

	// ... properties

	private final Label labelStatusLine;

	// ... constructors

	public StatusLineComponent() {

		setSpacing(5);
		setPadding(new Insets(5));

		getStyleClass().removeAll();
		getStyleClass().add("status-line");

		labelStatusLine = new Label();
		getChildren().add(labelStatusLine);
	}

	// ... business methods

	public void setStatusTextTo(final String statusText) {

		labelStatusLine.setText(statusText);
	}

}
