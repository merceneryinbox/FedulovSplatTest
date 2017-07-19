package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by mercenery on 14.07.2017.
 */
public class AddDialogController {
    @FXML
    Button okButton;

    @FXML
    TextField inptTxtInAddingDialog;

    public void modalWindowAddFolder(ActionEvent actionEvent) throws IOException {
        File file = new File(inptTxtInAddingDialog.getText());
        file.mkdirs();
        // get a handle to the stage
        Stage stage = (Stage) okButton.getScene().getWindow();
// do what you have to do
        stage.close();

    }

    public void modalWindowAddClose(ActionEvent actionEvent) {
        // get a handle to the stage
        Stage stage = (Stage) okButton.getScene().getWindow();
// do what you have to do
        stage.close();
    }
}
