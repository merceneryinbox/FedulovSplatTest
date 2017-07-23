package sample;

import filebrowsertools.MyTreeItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by mercenery on 23.07.2017.
 */
public class RenameController {

    @FXML
    public TextField txtRenameFld;
    @FXML
    public Button okRenameParent;
    @FXML
    public Button cancelRenameButton;

    private String oldName;

    public void initialize() throws IOException {
        oldName = ((Controller.selectedTreeItem).getValue()).toString();
        txtRenameFld.setText(oldName);

        FXMLLoader renameLoader = new FXMLLoader(getClass().getResource("RenameCustom.fxml"));
        Parent renameParentLayer = renameLoader.load();
        Stage renameDialogStage = new Stage();
        renameDialogStage.setResizable(false);
        renameDialogStage.setTitle("Rename file dialog");
        renameDialogStage.initModality(Modality.WINDOW_MODAL);
        Scene renameDialogScene = new Scene(renameParentLayer);
        renameDialogStage.setScene(renameDialogScene);
        renameDialogStage.show();
    }


    /**
     * @param actionEvent
     */
    public void renameFile(ActionEvent actionEvent) {
        Path oldSelectedPath = (Path) (Controller.selectedTreeItem).getValue();
        String newName = txtRenameFld.getText();
        FileSystem fs = FileSystems.getDefault();
        Path newPath = fs.getPath(newName);

        try {
            Files.copy(oldSelectedPath, newPath);
            Files.delete(oldSelectedPath);
            Controller.parentOfSelectedTreeItem.getChildren().remove(Controller.selectedTreeItem);
            Controller.parentOfSelectedTreeItem.getChildren().add(new MyTreeItem(newPath));


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hello World!");
            alert.setHeaderText("Info");
            alert.setContentText("You had renamed file - " + oldName + " into -  " + newName + " !");
            alert.initModality(Modality.WINDOW_MODAL);
            alert.showAndWait();

            Stage stage = (Stage) okRenameParent.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param actionEvent
     */
    public void cancelRename(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelRenameButton.getScene().getWindow();
        stage.close();
    }
}
