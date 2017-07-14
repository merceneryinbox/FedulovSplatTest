package sample;

import filebrowsertools.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private String imagePathCloseF = "folder_new.png";
    private String imagePathOpenF = "folder_new.png";

    private ImageView iconOpenFolder;
    private ImageView iconClosedFolder;

    private Image imgOpF = new Image(imagePathOpenF);
    private Image imgCloF = new Image(imagePathCloseF);

    // Start Controller Initialization block
    public void initialize() {


        iconOpenFolder = new ImageView(imgOpF);
        iconClosedFolder = new ImageView(imgCloF);

        startPathInControl = new StartPathGenerator(nameOfStartPath).generatePath();

        // Init root TreeItem to witch others were set
        MyTreeItem<Path, Node, Boolean> rootItem = new MyTreeItem<Path, Node, Boolean>(startPathInControl, false);

        // getting itemsList in start directory
        itemsListByPaths = new ItemListPopulator(startPathInControl).populateTreeItemListForController();

        // fulfil TreeItems by icons
        itemsListByPaths = new FulFilListItemsIcoByTypes(itemsListByPaths).fillingListOfMyTreeItems();

        // set all subitems on root
        rootItem.getChildren().addAll(itemsListByPaths);

        // set root TreeItem on TreeView
        tvLeft.setRoot(rootItem);

        // describe behavior of selected TreeItem in  TreeView and behavior of ListView if TreeItem is Selected
        tvLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                // getting List of elements in selected MyTreeItem if it consists of
                MyTreeItem subItem = (MyTreeItem) newValue;

                // but only if it is directory< not file
                if (Files.isDirectory(subItem.getPath())&&(!subItem.isYetVisited())) {
                    subItem.setGraphic(iconOpenFolder);

                    // init subItems list from selected Path
                    List<MyTreeItem> subItemsList = new ArrayList<>();

                    // getting list of Paths in selected directory of MyTreeItem
                    List<Path> pathListOfMyTreeItemsInListener = new NioFolderObserver(subItem.getPath()).getpathList();

                    // walking tree of paths in selected directory
                    for (Path subP :
                            pathListOfMyTreeItemsInListener) {

                        // creating MyTreeItem with taken Path and default other properties
                        MyTreeItem tmpIinSub = new MyTreeItem(subP);
                        tmpIinSub = new FulFillOneItemIcoByType(subItem).filInTheIcon();
                        subItemsList.add(tmpIinSub);
                    }
                    subItem.getChildren().addAll(subItemsList);
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
