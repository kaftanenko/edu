package app.arduino.sirenofshame.frontend.javafx.stage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PropertiesDialogStage extends Stage {

	public PropertiesDialogStage() {

		setTitle("Einstellungen");
		initModality(Modality.APPLICATION_MODAL);

		final GridPane dialogPane = new GridPane();

		dialogPane.add(new Label("Jenkins URL"), 1, 1);
		dialogPane.add(new TextField(), 2, 1);

		dialogPane.add(new Label("Nutzername"), 1, 2);
		dialogPane.add(new TextField(), 2, 2);

		dialogPane.add(new Label("Password"), 1, 3);
		dialogPane.add(new PasswordField(), 2, 3);

		final FlowPane buttonPane = new FlowPane();

		final Button buttonOk = new Button("Ok");
		buttonOk.setOnAction(e -> this.close());
		buttonPane.getChildren().add(buttonOk);

		final Button buttonCancel = new Button("Cancel");
		buttonCancel.setOnAction(e -> this.close());
		buttonPane.getChildren().add(buttonCancel);

		dialogPane.add(buttonPane, 1, 4, 2, 1);

		final Scene scene = new Scene(dialogPane);

		setScene(scene);
	}

}
