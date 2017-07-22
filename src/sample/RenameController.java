package sample;

import filebrowsertools.MyTreeItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.*;

import static sample.Controller.selectedTreeItem;

/**
 * Created by mercenery on 21.07.2017.
 */
public class RenameController {

    @FXML
    public Button okRenameParent;
    @FXML
    public TextField txtRenameFld;
    @FXML
    public Button cancelRenameButton;

    private String oldName;
    private String newName;
    private Path oldSelectedPath;

    public void initialize() {
        oldName = (((MyTreeItem) selectedTreeItem).getValue()).toString();
        txtRenameFld.setText(oldName);

        oldSelectedPath = (Path)(selectedTreeItem).getValue();
    }


    public void renameFile(ActionEvent actionEvent) throws IOException {
        newName = txtRenameFld.getText();
        Files.copy(oldSelectedPath, (Paths.get(newName)));
        Files.delete(oldSelectedPath);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hello World!");
        alert.setHeaderText("Info");
        alert.setContentText("You just rename file " + oldName + " in " + newName);
        alert.showAndWait();

        Stage stage = (Stage) okRenameParent.getScene().getWindow();
        stage.close();
    }

    public void cancelRename(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelRenameButton.getScene().getWindow();
        stage.close();
    }
}
