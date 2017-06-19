package app.arduino.sireneofshame.frontend.impl.javafx;

import app.arduino.sireneofshame.frontend.impl.javafx.component.ControlBoardComponent;
import app.arduino.sireneofshame.frontend.impl.javafx.component.StatusLineComponent;
import app.arduino.sireneofshame.frontend.impl.javafx.component.ToolBarComponent;
import app.arduino.sireneofshame.frontend.impl.javafx.resources.UIImage;
import app.arduino.sireneofshame.frontend.impl.javafx.resources.UIMessage;
import app.arduino.sireneofshame.frontend.impl.javafx.type.EConnectionState;
import app.arduino.sireneofshame.service.host.api.ESireneOfShameAlarmLevel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SireneOfShameJavaFXApplication extends Application {

	// ... constants

	private static final int DEFAULT_SCENE_WIDTH_IN_PX__270 = 270;
	private static final int DEFAULT_SCENE_HEIGHT_IN_PX__440 = 440;

	private final String CSS_REFERENCE = this.getClass().getResource("/style/index.css").toExternalForm();

	// ... properties

	private ToolBarComponent toolBarComponent;
	private ControlBoardComponent controlBoardComponent;
	private StatusLineComponent statusLineComponent;

	private SireneOfShameJavaFXController sireneOfShameJavaFXController;

	// ... life cycle methods

	@Override
	public void init() throws Exception {

		super.init();

		sireneOfShameJavaFXController = new SireneOfShameJavaFXController(this);
	}

	@Override
	public void start(final Stage window) throws Exception {

		final BorderPane rootNode = new BorderPane();

		toolBarComponent = new ToolBarComponent(sireneOfShameJavaFXController);
		rootNode.setTop(toolBarComponent);

		controlBoardComponent = new ControlBoardComponent(sireneOfShameJavaFXController);
		rootNode.setCenter(controlBoardComponent);

		statusLineComponent = new StatusLineComponent();
		rootNode.setBottom(statusLineComponent);

		// ...

		toolBarComponent.setConnectionState(EConnectionState.DISCONNECTED);
		statusLineComponent.setStatusTextTo(UIMessage.STATUS_DISCONNECTED.getText());

		final Scene scene = new Scene(rootNode, DEFAULT_SCENE_WIDTH_IN_PX__270, DEFAULT_SCENE_HEIGHT_IN_PX__440);
		scene.getStylesheets().clear();
		scene.getStylesheets().add(CSS_REFERENCE);

		window.setScene(scene);

		window.setTitle(UIMessage.APP_TITLE.getText());
		window.getIcons().add(new Image(UIImage.APPLICATION_LOGO_36.getAsStream()));

		resetApplicationState();

		window.setResizable(false);
		window.show();
	}

	@Override
	public void stop() throws Exception {

		super.stop();
	}

	// ... GUI components management methods

	private void resetApplicationState() {

		toolBarComponent.setConnectionState(EConnectionState.DISCONNECTED);
		toolBarComponent.resetStateSelection();
		controlBoardComponent.resetAlarmLevelSelection();
		statusLineComponent.setStatusTextTo("Disconnected");
	}

	public void onConnected(final String portName, final ESireneOfShameAlarmLevel state) {

		toolBarComponent.setConnectionState(EConnectionState.CONNECTED);
		toolBarComponent.updateStateSelection(state);
		controlBoardComponent.updateAlarmLevelSelection(state);
		statusLineComponent.setStatusTextTo("Connected to the port " + portName);
	}

	public void onDisconnected() {

		resetApplicationState();
	}

	public void onAlarmLevelChanged(final ESireneOfShameAlarmLevel state) {

		toolBarComponent.updateStateSelection(state);
		controlBoardComponent.updateAlarmLevelSelection(state);
	}

}
