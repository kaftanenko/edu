package edu.java.jse.javafx.helloworld;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorldJavaFXLauncher extends Application {

	// ... properties

	private Label label;

	// ... life cycle methods

	@Override
	public void init() throws Exception {

		super.init();
		label = new Label("Hello World!");
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		final StackPane root = new StackPane();
		root.getChildren().add(label);

		final Scene scene = new Scene(root, 480, 240);

		primaryStage.setTitle("Hello World JavaFX Application");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}

	// ... launcher method

	public static void main(final String... args) {

		launch(args);
	}

}
