package sample;

import filebrowsertools.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
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


    private List<Path> pathsOnDemandList;
    private String nameOfStartPath = "d://";
    private Path startPathInControl;
    private List<MyTreeItem> itemsListByPaths;


    // Start Controller Initialization block
    public void initialize() {
        startPathInControl = new StartPathGenerator(nameOfStartPath).generatePath();
        // Init root TreeItem to witch others were set
        MyTreeItem<Path, Node, Boolean> rootItem = new MyTreeItem<Path, Node, Boolean>(startPathInControl, false);
        // getting itemsList in start directory
        itemsListByPaths = new ItemListPopulator(startPathInControl).populateTreeItemListForController();

        // fulfil TreeItems by icons
        itemsListByPaths = new FullFilItemsByIcoes(itemsListByPaths).filling();
        // set root TreeItem
        tvLeft.setRoot(rootItem);
        // describe behavior of selected TreeItem in  TreeView and behavior of ListView if TreeItem is Selected
        tvLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                // getting List of elements in selected TreeItem
                TreeItem<Path> forSubItems = (TreeItem<Path>) newValue;
                forSubItems.setGraphic(iconClosedFolder);
                forSubItems.getGraphic().setVisible(true);
                List<TreeItem<Path>> subItems = new ArrayList<>();
                List<Path> pathinTable = new NioFolderObserver((forSubItems).getValue()).getpathList();
                // find if selected TreeItem consists of subdirs or filesthen populate this selected Item in treeView
                if (Files.isDirectory(forSubItems.getValue())) {
                    for (Path subP :
                            pathinTable) {
                        TreeItem<Path> tempI = new TreeItem<>(subP, iconClosedFolder);
                        tempI.getGraphic().setVisible(true);
                        forSubItems.getChildren().add(tempI);
                        subItems.add(tempI);
                    }
                }

                tableView.setItems(FXCollections.observableArrayList(pathinTable));
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
}
