package app.arduino.sirenofshame.frontend.javafx.dialog;

import static app.arduino.sirenofshame.frontend.javafx.resources.UIMessage.COMMON_BUTTON_CANCEL;
import static app.arduino.sirenofshame.frontend.javafx.resources.UIMessage.COMMON_BUTTON_OK;
import static app.arduino.sirenofshame.frontend.javafx.resources.UIMessage.SETTINGS_LABEL_HOST_URL;
import static app.arduino.sirenofshame.frontend.javafx.resources.UIMessage.SETTINGS_LABEL_JOBS_NAME_REGEX_PATTERN;
import static app.arduino.sirenofshame.frontend.javafx.resources.UIMessage.SETTINGS_LABEL_PASSWORD;
import static app.arduino.sirenofshame.frontend.javafx.resources.UIMessage.SETTINGS_LABEL_POLLING_PERIOD_IN_SEC;
import static app.arduino.sirenofshame.frontend.javafx.resources.UIMessage.SETTINGS_LABEL_JOBS_FOLDER_PATH;
import static app.arduino.sirenofshame.frontend.javafx.resources.UIMessage.SETTINGS_LABEL_USERNAME;
import static app.arduino.sirenofshame.frontend.javafx.resources.UIMessage.SETTINGS_TITLE;

import java.util.regex.Pattern;

import app.arduino.sirenofshame.frontend.javafx.exception.util.ErrorHelper;
import app.arduino.sirenofshame.frontend.javafx.resources.UIImage;
import app.sirenofshame.common.host.service.jenkins.client.http.JenkinsHttpClientConfig;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SettingsDialog extends Dialog<JenkinsApiJsonResourceScannerConfig> {

  // ... constructors

  public SettingsDialog(final JenkinsApiJsonResourceScannerConfig data) {

    initModality(Modality.APPLICATION_MODAL);

    setResizable(false);

    setTitle(SETTINGS_TITLE.getText());
    final Stage currentStage = (Stage) getDialogPane().getScene().getWindow();
    currentStage.getIcons().add(new Image(UIImage.BUTTON_CONFIGURATION_32.getAsStream()));

    final GridPane dialogPane = new GridPane();

    int gridRowIndex = 0;

    gridRowIndex++;
    final TextField nodeHostUrl = new TextField();
    nodeHostUrl.setText(data.getJenkinsHttpClientConfig().getHostURL());
    dialogPane.add(new Label(SETTINGS_LABEL_HOST_URL.getText()), 1, gridRowIndex);
    dialogPane.add(nodeHostUrl, 2, gridRowIndex);

    gridRowIndex++;
    final TextField nodeUsername = new TextField();
    nodeUsername.setText(data.getJenkinsHttpClientConfig().getUsername());
    dialogPane.add(new Label(SETTINGS_LABEL_USERNAME.getText()), 1, gridRowIndex);
    dialogPane.add(nodeUsername, 2, gridRowIndex);

    gridRowIndex++;
    final PasswordField nodePassword = new PasswordField();
    nodePassword.setText(data.getJenkinsHttpClientConfig().getPassword());
    dialogPane.add(new Label(SETTINGS_LABEL_PASSWORD.getText()), 1, gridRowIndex);
    dialogPane.add(nodePassword, 2, gridRowIndex);

    gridRowIndex++;
    final TextField nodeJobsFolderPath = new TextField();
    nodeJobsFolderPath.setText(data.getJobsFolderPath());
    dialogPane.add(new Label(SETTINGS_LABEL_JOBS_FOLDER_PATH.getText()), 1, gridRowIndex);
    dialogPane.add(nodeJobsFolderPath, 2, gridRowIndex);

    gridRowIndex++;
    final TextField nodePollingPeriodInSec = new TextField();
    nodePollingPeriodInSec.setText(Long.toString(data.getPollingPeriodInSec()));
    dialogPane.add(new Label(SETTINGS_LABEL_POLLING_PERIOD_IN_SEC.getText()), 1, gridRowIndex);
    dialogPane.add(nodePollingPeriodInSec, 2, gridRowIndex);

    gridRowIndex++;
    final TextField nodeJobsNameRegExPattern = new TextField();
    nodeJobsNameRegExPattern.setText(data.getJobsNameRegExPattern().pattern());
    dialogPane.add(new Label(SETTINGS_LABEL_JOBS_NAME_REGEX_PATTERN.getText()), 1, gridRowIndex);
    dialogPane.add(nodeJobsNameRegExPattern, 2, gridRowIndex);

    final ButtonType buttonOk = new ButtonType(COMMON_BUTTON_OK.getText(), ButtonData.OK_DONE);
    final ButtonType buttonCancel = new ButtonType(COMMON_BUTTON_CANCEL.getText(), ButtonData.CANCEL_CLOSE);

    this.getDialogPane().setContent(dialogPane);
    this.getDialogPane().getButtonTypes().add(buttonOk);
    this.getDialogPane().getButtonTypes().add(buttonCancel);

    this.setResultConverter(new Callback<ButtonType, JenkinsApiJsonResourceScannerConfig>() {

      @Override
      public JenkinsApiJsonResourceScannerConfig call(ButtonType buttonType) {

        switch (buttonType.getButtonData()) {
        case OK_DONE:

          return JenkinsApiJsonResourceScannerConfig.of(JenkinsHttpClientConfig.of( //
              nodeHostUrl.getText(), //
              nodeUsername.getText(), //
              nodePassword.getText() //
          ), //
              Long.valueOf(nodePollingPeriodInSec.getText()), //
              nodeJobsFolderPath.getText(), //
              Pattern.compile(nodeJobsNameRegExPattern.getText()) //
          );
        case CANCEL_CLOSE:

          return null;
        default:
          throw ErrorHelper.handleFatalException(
              String.format("The button type \"%s\" is not supported.", buttonType.getButtonData()));
        }
      }
    });
  }

}
