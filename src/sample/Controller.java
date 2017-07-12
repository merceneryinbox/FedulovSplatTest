package sample;

import filebrowsertools.NioFolderObserver;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<TreeItem> itemsListByPaths;
    private String nameOfStartPath;
    private Path startPath;

    private FileSystem fs;

    // Start Controller Initialization block
    public void initialize() {

        // Initialize start path
        nameOfStartPath = "d:\\";
        // Initialize contain in chosen TreeItem paths
        pathsOnDemandList = new ArrayList<>();
        // Initialize TreeItem List with value of paths
        itemsListByPaths = new ArrayList<>();

        // getting filesystem
        fs = FileSystems.getDefault();
        // getting start path from chosen filesysytem
        startPath = fs.getPath(nameOfStartPath);
        // getting an Object to further internal reading its contains
        NioFolderObserver nfo = new NioFolderObserver(startPath);
        // getting fs elements(Path) List to populate TreeItems
        pathsOnDemandList = nfo.getFSElementsOnLevelDownOnDemand();
        // Init root TreeItem to witch others Items were set
        TreeItem<Path> rootItem = new TreeItem<>(startPath);

        // populate itemsList to fulfil tree TreeItems <Path> List in loop (Old Style)
//        for (Path p :
//                pathsOnDemandList) {
//            if (Files.isDirectory(p)) {
//                itemsListByPaths.add(new TreeItem<>(p));
//
//            }
//        }
        //  populate itemsList to fulfil tree(lambda + stream expression JDK8 style)
        itemsListByPaths = pathsOnDemandList.stream().filter(p -> p.toFile().isDirectory()).map(TreeItem::new).collect(Collectors.toList());
        // fulfil TreeItems in loop
        for (TreeItem ti :
                itemsListByPaths) {
            rootItem.getChildren().add(ti);
        }
        // set root TreeItem
        tvLeft.setRoot(rootItem);
        // describe behavior of selected TreeItem in  TreeView and behavior of ListView if TreeItem is Selected
        tvLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                // getting List of elements in selected TreeItem
                TreeItem<Path> forSubItems = (TreeItem<Path>) newValue;
                List<Path> pathinTable = new NioFolderObserver((forSubItems).getValue()).getFSElementsOnLevelDownOnDemand();
                // setting all element to the root TreeItem
                if (Files.isDirectory(forSubItems.getValue())) {
                    List<Path> subPathList  = new NioFolderObserver((forSubItems).getValue()).getFSElementsOnLevelDownOnDemand();
                    for (Path subP :
                            subPathList) {
                        forSubItems.getChildren().add(new TreeItem<>(subP));
                    }
                }
                tableView.setItems(FXCollections.observableArrayList(pathinTable));
                // refreshing TreeView
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
