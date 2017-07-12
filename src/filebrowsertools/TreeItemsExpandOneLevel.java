//package filebrowsertools;
//
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.scene.control.TreeItem;
//import javafx.scene.control.TreeView;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Created by mercenery on 12.07.2017.
// */
//public class TreeItemsExpandOneLevel {
//    private final Path path;
//    private final TreeView<Path> treeView;
//    private List<Path> pathsOnDemandList;
//    private List<TreeItem> itemsListByPaths;
//
//    public TreeItemsExpandOneLevel(Path path, TreeView<Path> treeView) {
//        this.path = path;
//        this.treeView = treeView;
//
//    }
//
//    public TreeView<Path> expandOne() {
//        pathsOnDemandList = new ArrayList<>();
//        itemsListByPaths = new ArrayList<>();
//
//        NioFolderObserver nfo = new NioFolderObserver(path);
//        pathsOnDemandList = nfo.getFSElementsOnLevelDownOnDemand();
//
//        TreeItem<Path> rootItem = new TreeItem<>(path);
//
//        //  populate itemsList to fulfil tree(lambda + stream expression JDK8)
//        itemsListByPaths = pathsOnDemandList.stream().filter(p -> Files.isDirectory(p)).map(TreeItem::new).collect(Collectors.toList());
//
//        for (TreeItem ti :
//                itemsListByPaths) {
//            rootItem.getChildren().add(ti);
//        }
//
//        treeView.setRoot(rootItem);
//        treeView.setVisible(true);
//
//        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                List<Path> pathinTable = new NioFolderObserver(((TreeItem<Path>) newValue).getValue()).getFSElementsOnLevelDownOnDemand();
//                treeView.setItems(FXCollections.observableArrayList(pathinTable));
//                treeView.refresh();
//            }
//        });
//    }
//}
