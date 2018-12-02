package app.arduino.sirenofshame.frontend.javafx;

import app.arduino.sirenofshame.frontend.javafx.component.ControlBoardComponent;
import app.arduino.sirenofshame.frontend.javafx.component.StatusLineComponent;
import app.arduino.sirenofshame.frontend.javafx.component.ToolBarComponent;
import app.arduino.sirenofshame.frontend.javafx.resources.UIImage;
import app.arduino.sirenofshame.frontend.javafx.resources.UIMessage;
import app.arduino.sirenofshame.frontend.javafx.stage.PropertiesDialogStage;
import app.arduino.sirenofshame.frontend.javafx.type.EConnectionState;
import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SirenOfShameJavaFXApplication extends Application {

  // ... constants

  private static final int DEFAULT_SCENE_WIDTH_IN_PX__270 = 270;
  private static final int DEFAULT_SCENE_HEIGHT_IN_PX__440 = 440;

  private final String CSS_REFERENCE = this.getClass().getResource("/style/index.css").toExternalForm();

  // ... properties

  private ToolBarComponent toolBarComponent;
  private ControlBoardComponent controlBoardComponent;
  private StatusLineComponent statusLineComponent;
  private PropertiesDialogStage propertiesDialogStage;

  private SirenOfShameJavaFXController sirenOfShameJavaFXController;

  // ... life cycle methods

  @Override
  public void init() throws Exception {

    super.init();

    sirenOfShameJavaFXController = new SirenOfShameJavaFXController(this);
  }

  @Override
  public void start(final Stage window) throws Exception {

    // ...

    propertiesDialogStage = new PropertiesDialogStage();

    // ...

    final BorderPane rootNode = new BorderPane();

    toolBarComponent = new ToolBarComponent(sirenOfShameJavaFXController);
    rootNode.setTop(toolBarComponent);

    controlBoardComponent = new ControlBoardComponent(sirenOfShameJavaFXController);
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

  public void showPropertiesDialog() {
    propertiesDialogStage.showAndWait();
  }

  private void resetApplicationState() {

    toolBarComponent.setConnectionState(EConnectionState.DISCONNECTED);
    toolBarComponent.resetStateSelection();
    controlBoardComponent.resetAlarmLevelSelection();
    statusLineComponent.setStatusTextTo("Disconnected");
  }

  public void onConnected(final String portName, final ESirenOfShameAlarmLevel state) {

    toolBarComponent.setConnectionState(EConnectionState.CONNECTED);
    toolBarComponent.updateStateSelection(state);
    controlBoardComponent.updateAlarmLevelSelection(state);
    statusLineComponent.setStatusTextTo("Connected to the port " + portName);
  }

  public void onDisconnected() {

    resetApplicationState();
  }

  public void onAlarmLevelChanged(final ESirenOfShameAlarmLevel state) {

    toolBarComponent.updateStateSelection(state);
    controlBoardComponent.updateAlarmLevelSelection(state);
  }

}