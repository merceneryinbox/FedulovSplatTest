package sample;

import filebrowsertools.MyTreeItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static sample.Controller.selectedTreeItem;

/**
 * Created by mercenery on 21.07.2017.
 */
public interface RenameController {


     void initialize() ;
//        oldName = (((MyTreeItem) selectedTreeItem).getValue()).toString();
//        txtRenameFld.setText(oldName);
//        oldSelectedPath = (Path) (selectedTreeItem).getValue();


     void renameFile(ActionEvent actionEvent) ;
//        newName = txtRenameFld.getText();
//        Files.copy(oldSelectedPath, (Paths.get(newName)));
//        Files.delete(oldSelectedPath);
//
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Hello World!");
//        alert.setHeaderText("Info");
//        alert.setContentText("You had renamed file - " + oldName + " into -  " + newName + " !");
//        alert.initModality(Modality.WINDOW_MODAL);
//        alert.showAndWait();
//
//        Stage stage = (Stage) okRenameParent.getScene().getWindow();
//        stage.close();

     void cancelRename(ActionEvent actionEvent) ;
//        Stage stage = (Stage) cancelRenameButton.getScene().getWindow();
//        stage.close();
}
