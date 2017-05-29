package app.arduino.sereneofshame.frontend.impl.javafx;

import java.util.HashMap;
import java.util.Map;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.api.SireneOfShameControllerEventsListener;
import app.arduino.sereneofshame.service.impl.serial.host.SireneOfShameSerialHostController;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SireneOfShameJavaFXLauncher extends Application {

	private static final ESireneOfShameState STATE_UNKNOWN = null;
	// ... properties

	private Label labelCurrentState;
	private Button buttonUpdateCurrentState;
	private final Map<ESireneOfShameState, Button> buttonsSetStateTo = new HashMap<>();

	private SireneOfShameSerialHostController sireneOfShameController;

	// ... life cycle methods

	@Override
	public void init() throws Exception {

		super.init();

		labelCurrentState = new Label();

		buttonUpdateCurrentState = new Button("GET_STATE");
		buttonUpdateCurrentState.setOnAction(e -> {
			final ESireneOfShameState state = sireneOfShameController.getState();
			updateLabelCurrentState(state);
		});

		for (final ESireneOfShameState state : ESireneOfShameState.values()) {

			final Button buttonSetStateTo = new Button("SET_STATE_TO_" + state.name());
			buttonSetStateTo.setOnAction(e -> {
				sireneOfShameController.setState(state);
			});

			buttonsSetStateTo.put(state, buttonSetStateTo);
		}

		sireneOfShameController = new SireneOfShameSerialHostController();
		final SireneOfShameControllerEventsListener eventsListener = new SireneOfShameControllerEventsListener() {
			@Override
			public void onStateChanged(final ESireneOfShameState from, final ESireneOfShameState to) {
				updateLabelCurrentState(to);
			}
		};
		sireneOfShameController.subscribe(eventsListener);
		sireneOfShameController.open();
	}

	private void resetLabelCurrentState() {

		labelCurrentState.setText("Current state: unknown");
	}

	private void updateLabelCurrentState(final ESireneOfShameState state) {

		String stateName;
		if (state == STATE_UNKNOWN) {
			stateName = "unknown";
		} else {
			stateName = state.name();
		}
		labelCurrentState.setText("Current state: " + stateName);
	}

	@Override
	public void start(final Stage window) throws Exception {

		final StackPane rootNode = new StackPane();

		final VBox mainFrame = new VBox();
		mainFrame.getChildren().add(labelCurrentState);
		mainFrame.getChildren().add(buttonUpdateCurrentState);
		for (final ESireneOfShameState state : ESireneOfShameState.values()) {

			final Node channelController = buttonsSetStateTo.get(state);
			mainFrame.getChildren().add(channelController);
		}

		rootNode.getChildren().add(mainFrame);

		final Scene scene = new Scene(rootNode, 160, 120);
		window.setScene(scene);
		window.setTitle("Sirene of Shame");

		window.setResizable(false);
		window.show();
	}

	@Override
	public void stop() throws Exception {

		super.stop();
		sireneOfShameController.close();
	}

	// ... launcher method

	public static void main(final String... args) {

		launch(args);
	}

}
