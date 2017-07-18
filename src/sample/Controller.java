package sample;

import filebrowsertools.FulFillIcoByType;
import filebrowsertools.ItemPopulator;
import filebrowsertools.MyTreeItem;
import filebrowsertools.StartPathGenerator;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    // Declaration needed variables
    @FXML
    private TreeView tvLeft;

    @FXML
    private TableView<Path> tableView;

    @FXML
    private TableColumn<Path, String> tableViewName;

    @FXML
    private TableColumn<Path, String> tableViewSize;

    @FXML
    private TableColumn<Path, String> tableViewDate;
    @FXML

    private MenuBar menuBar;

    @FXML
    private MenuItem mnFileNew;

    @FXML
    private MenuItem mnFileOpen;

    @FXML
    private MenuItem mnFileClose;


    private List<Path> pathsOnDemandList;
    private String nameOfStartPath = ".";
    private Path startPathInControl;
    private List<MyTreeItem> itemsListByPaths;

    // Start Controller initialization block
    public void initialize() {

        // creating Path object by String path in filesystem
        startPathInControl = new StartPathGenerator(nameOfStartPath).generatePath();

        // Init root TreeItem to witch others were set
        MyTreeItem rootItem = new MyTreeItem(startPathInControl);
        rootItem.setExpanded(true);
        rootItem.setGraphic(new ImageView(new Image("icoes\\folder_opened.png")));
        // getting MyTreeItemList of sub items in start directory
        itemsListByPaths = new ItemPopulator(rootItem).populate();

        // set up all subitems at root
        rootItem.getChildren().addAll(itemsListByPaths);

        // set root TreeItem at TreeView
        tvLeft.setRoot(rootItem);
        tvLeft.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // describe behavior of selected TreeItem in  TreeView and behavior of ListView if TreeItem is Selected
        tvLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                // getting List of elements in selected MyTreeItem if it consists of them
                MyTreeItem selectedItem = (MyTreeItem) newValue;
                selectedItem.setYetVisited(true);
                selectedItem = new FulFillIcoByType(selectedItem).filInTheIconInMyTreeItem();
                Path p = (Path) selectedItem.getValue();
/**
 * if we have a list of MyTreeItems handled it but if "p" is one single file handled it too
 */
                List<Path> pathListOfMyTreeItemsInListener = new ArrayList<>();
                // if we have a list of MyTreeItems handled it but if "p" is one single file handled it too
                if (Files.isDirectory(p)) {
                    // populating subMyTreeItems List in "p" - selected Directory
                    List<MyTreeItem> subItemsList = new ItemPopulator(selectedItem).populate();

                    // setting list of MyTreeItems to root Item
                    selectedItem.getChildren().addAll(subItemsList);

                    // populate list of sub MyTreeItems
                    for (MyTreeItem mt :
                            subItemsList) {
                        pathListOfMyTreeItemsInListener.add((Path) mt.getValue());
                    }

                    // setting sub MyTreeItems in selected parent Paths onto right TableView
                    tableView.setItems(FXCollections.observableArrayList(pathListOfMyTreeItemsInListener));

                    // refreshing tableView
                    tableView.refresh();

                    // else if path is a file creating list of ine single element and pass it in table view
                } else {
                    pathListOfMyTreeItemsInListener.add(((Path) selectedItem.getValue()));
                    tableView.setItems(FXCollections.observableArrayList(pathListOfMyTreeItemsInListener));

                    // refreshing tableView
                    tableView.refresh();
                }
            }
        });


//populate columns in table by values from pathinTable
        tableViewName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Path, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Path, String> c) {
                return new SimpleStringProperty((c.getValue()).toString());
            }
        });

        tableViewSize.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Path, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Path, String> c) {
                return new SimpleStringProperty(String.valueOf(c.getValue().toFile().length() / 1024));
            }
        });

        tableViewDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Path, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Path, String> c) {
                return new SimpleStringProperty(String.valueOf(new SimpleDateFormat("dd.MM.yyyy  hh:mm:ss").format(c.getValue().toFile().lastModified())));
            }
        });
    }


    public void makeFolder(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AlertDialog_css.fxml"));
        Stage addDialogStage = new Stage();
        addDialogStage.setTitle("Make new file dialog");
        Scene addDialogScene ;
        try {
            Parent addDialogRoot = (Parent) fxmlLoader.load();
            addDialogScene = new Scene(addDialogRoot);
            addDialogStage.setScene(addDialogScene);
            addDialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFolder(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Folder Dialog");
        File file = fileChooser.showOpenDialog(null);
        tableView.getItems().add(file.toPath());
        tableView.refresh();
    }

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void deleteFile(ActionEvent actionEvent) {
        MyTreeItem selectedToDelete = (MyTreeItem) tvLeft.getSelectionModel().getSelectedItem();
        Path pp = (Path) selectedToDelete.getValue();
        File f = pp.toFile();
        MyTreeItem parentOfselectedToDelete = (MyTreeItem) selectedToDelete.getParent();
        if (parentOfselectedToDelete != null) {
            parentOfselectedToDelete.getChildren().remove(selectedToDelete);
// recursive deleting all files in directory and directory itself
            pathRecursiveDelete(pp);
        }
    }

    public void showAbout(ActionEvent actionEvent) {

    }

    public void renameFile(ActionEvent actionEvent) {

    }
// ending deleting after close aapplication
    public void pathRecursiveDelete(Path path) {
        File convertPath = path.toFile();
        try {
            if (!Files.isDirectory(convertPath.toPath())) {
                convertPath.delete();
            } else {
                for (File ppp :
                        convertPath.listFiles()) {
                    pathRecursiveDelete(ppp.toPath());
                }
                Files.delete(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}