package sample;

import filebrowsertools.FulFillIcoByType;
import filebrowsertools.ItemPopulator;
import filebrowsertools.MyTreeItem;
import filebrowsertools.StartPathGenerator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

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
    private MenuItem mnNew;

    private List<Path> pathsOnDemandList;
    private String nameOfStartPath = "D:\\Kali\\";
    private Path startPathInControl;
    private List<MyTreeItem> itemsListByPaths;

    // Start Controller initialization block
    public void initialize() {

        // creating Path object by String path in filesystem
        startPathInControl = new StartPathGenerator(nameOfStartPath).generatePath();

        // Init root TreeItem to witch others were set
        MyTreeItem rootItem = new MyTreeItem(startPathInControl);

        // getting MyTreeItemList of sub items in start directory
        itemsListByPaths = new ItemPopulator(rootItem).populate();

        // set up all subitems at root
        rootItem.getChildren().addAll(itemsListByPaths);

        // set root TreeItem at TreeView
        tvLeft.setRoot(rootItem);

        // describe behavior of selected TreeItem in  TreeView and behavior of ListView if TreeItem is Selected
        tvLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                // getting List of elements in selected MyTreeItem if it consists of them
                MyTreeItem selectedItem = (MyTreeItem) newValue;
                selectedItem.setYetVisited(true);
                selectedItem = new FulFillIcoByType(selectedItem).filInTheIconInMyTreeItem();
                Path p = (Path) selectedItem.getValue();
                List<Path> pathListOfMyTreeItemsInListener = new ArrayList<>();

/**
 * if we have a list of MyTreeItems handled it but if "p" is one single file handled it too
 */
                // if we have a list of MyTreeItems handled it but if "p" is one single file handled it too
                if (Files.isDirectory(p)) {
                    List<MyTreeItem> subItemsList = new ItemPopulator(selectedItem).populate();

                    // setting list of MyTreeItems to root Item
                    selectedItem.getChildren().addAll(subItemsList);

                    // populate list of sub MyTreeItems
                    for (MyTreeItem mt :
                            subItemsList) {
                        pathListOfMyTreeItemsInListener.add((Path) mt.getValue());
                    }

                    // setting sub MyTreeItems in selected parent MyTreeItem onto right TableView
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
                return new SimpleStringProperty(((Path) c.getValue()).toString());
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

    }

    public void openFolder(ActionEvent actionEvent) {

    }

    public void closeWindow(ActionEvent actionEvent) {

    }

    public void deleteFile(ActionEvent actionEvent) {

    }

    public void showAbout(ActionEvent actionEvent) {

    }
}