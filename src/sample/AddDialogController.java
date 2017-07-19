package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mercenery on 14.07.2017.
 */
public class AddDialogController {
    @FXML
    Button okButton;

    @FXML
    TextField inptTxtInAddingDialog;

    public void modalWindowAddFolder(ActionEvent actionEvent) throws IOException {
        String filename = inptTxtInAddingDialog.getText();

        if (!filename.contains(".")) {
            Path path = Paths.get(filename);
            try {
                Files.createDirectory(path);
            } catch (FileAlreadyExistsException e) {
                // the directory already exists.
            } catch (IOException e) {
                //something else went wrong
                e.printStackTrace();
            }
        } else {
            Path path = Paths.get(filename);
            try {
                Files.createFile(path);
            } catch (FileAlreadyExistsException e) {
                // the directory already exists.
            } catch (IOException e) {
                //something else went wrong
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();

    }

    public void modalWindowAddClose(ActionEvent actionEvent) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
