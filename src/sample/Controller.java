package sample;

import filebrowsertools.*;
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
    TreeView tvLeft;

    @FXML
    TableView<Path> tableView;

    @FXML
    TableColumn<Path, String> tableViewName;

    @FXML
    TableColumn<Path, String> tableViewSize;

    @FXML
    TableColumn<Path, String> tableViewDate;

    @FXML
    MenuItem mnNew;


    private List<Path> pathsOnDemandList;
    private String nameOfStartPath = "d://";
    private Path startPathInControl;
    private List<MyTreeItem> itemsListByPaths;

    // Start Controller Initialization block
    public void initialize() {
        // creating Path object by String path in filesystem
        startPathInControl = new StartPathGenerator(nameOfStartPath).generatePath();

        // Init root TreeItem to witch others were set
        MyTreeItem rootItem = new MyTreeItem(startPathInControl, false);

        // getting MyTreeItem of subPaths list itemsList in start directory
        itemsListByPaths = new ItemListPopulator(startPathInControl).populateTreeItemList();

        // set all subitems at root
        rootItem.getChildren().addAll(itemsListByPaths);

        // set root TreeItem at TreeView
        tvLeft.setRoot(rootItem);

        // describe behavior of selected TreeItem in  TreeView and behavior of ListView if TreeItem is Selected
        tvLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                // getting List of elements in selected MyTreeItem if it consists of
                MyTreeItem subItem = (MyTreeItem) newValue;
                Path p = (Path) subItem.getValue();
                subItem.setYetVisited(true);
                subItem = new FulFillIcoByType(subItem).filInTheIconInMyTreeItem();
                List<MyTreeItem> subItemsList = new ArrayList<>();
                List<Path> pathListOfMyTreeItemsInListener = new ArrayList<>();

                if (Files.isDirectory(p)) {

                    // getting list of Paths in selected directory of MyTreeItem
                    pathListOfMyTreeItemsInListener = new NioFolderObserver(p).getSubPathsList();

                    // Getting list of handled MyTreeItems
                    subItemsList = new FulFillIcoByType(subItemsList).fillInTheIconsInMyTreeItemsList();

                    // recursive setting list of MyTreeItems to root Item
                    subItem.getChildren().addAll(subItemsList);

                    // setting sub MyTreeItems in selected parent MyTreeItem onto right TableView
                    tableView.setItems(FXCollections.observableArrayList(pathListOfMyTreeItemsInListener));

                    // refreshing tableView
                    tableView.refresh();
                }
                pathListOfMyTreeItemsInListener.add(((Path) subItem.getValue()));
                tableView.setItems(FXCollections.observableArrayList(pathListOfMyTreeItemsInListener));

                // refreshing tableView
                tableView.refresh();
            }
        });
//populate columns in table by values from pathinTable
        tableViewName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Path, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Path, String> c) {
                return new SimpleStringProperty(c.getValue().getFileName().toString());
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