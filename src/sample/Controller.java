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
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    public MenuItem mnHelpAbout;

    @FXML
    public ProgressIndicator progressIndicator;

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

    private int counter = 0;
    private List<Path> pathsOnDemandList;
    private String nameOfStartPath = "d:\\";
    private Path startPathInControl;
    private List<MyTreeItem> itemsListByPaths;

    // Start Controller initialization block
    public void initialize() throws InterruptedException {
        progressIndicator.setVisible(false);

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
                // Progress indicator initialize(2 seconds loading process) in separate thread not freeze application


                // getting List of elements in selected MyTreeItem if it consists of them
                MyTreeItem selectedItem = (MyTreeItem) newValue;
                selectedItem.setYetVisited(true);
                selectedItem.setGraphic(new ImageView(new Image("icoes\\refresh.gif")));

                MyTreeItem finalSelectedItem = selectedItem;
                Thread fillingItemThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressIndicator.setVisible(true); // set indicator visible for two seconds
                            Task task = taskWorker(200);
                            progressIndicator.progressProperty().bind(task.progressProperty());
                            Thread threadProgressIndicator = new Thread(task); // fork separate thread
                            threadProgressIndicator.start();
                            new FulFillIcoByType(finalSelectedItem).filInTheIconInMyTreeItem();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                fillingItemThread.start();

//                try {
//                    selectedItem = new FulFillIcoByType(selectedItem).filInTheIconInMyTreeItem();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Path p = (Path) selectedItem.getValue();
/**
 * if we have a list of MyTreeItems handled it but if "p" is one single file handled it too
 */


                List<Path> pathListOfMyTreeItemsInListener = new ArrayList<>();
                // if we have a list of MyTreeItems handled it but if "p" is one single file handled it too
                if (Files.isDirectory(p)) {

                    // Populating subMyTreeItems List in "p" - selected Directory
                    List<MyTreeItem> subItemsList = null;
                    try {
                        subItemsList = new ItemPopulator(selectedItem).populate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

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

            /**
             * Start progress indicator filling by percents from 0 to 100 sharply in two seconds
             * @param j
             * @return
             */
            private Task taskWorker(int j) {
                return new Task() {

                    @Override
                    protected Object call() throws Exception {
                        progressIndicator.setVisible(true); // set progress indicator visible for runtime
                        for (int i = 0; i < j; i++) {
                            updateProgress(i, j);
                            Thread.sleep(10);
                        }
                        progressIndicator.setVisible(false); // setting progressindicator invisible after runtime
                        return true;
                    }
                };
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

//        tableView.getSelectionModel().selectedItemProperty().addListener(new MouseEvent());
    }


    public void makeFolder(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AlertDialog_css.fxml"));
        Stage addDialogStage = new Stage();
        addDialogStage.setTitle("Make new folder dialog");
        addDialogStage.initModality(Modality.APPLICATION_MODAL);
        Parent addDialogRoot = fxmlLoader.load();
        Scene addDialogScene = new Scene(addDialogRoot);
        addDialogStage.setScene(addDialogScene);
        addDialogStage.show();
    }

    public void openFolder(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Folder Dialog");
        File file = fileChooser.showOpenDialog(null);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void deleteFile(ActionEvent actionEvent) {
        MyTreeItem selectedToDelete = (MyTreeItem) tvLeft.getSelectionModel().getSelectedItem();
        Path pp = (Path) selectedToDelete.getValue();
        MyTreeItem parentOfselectedToDelete = (MyTreeItem) selectedToDelete.getParent();
        if (parentOfselectedToDelete != null) {
            parentOfselectedToDelete.getChildren().remove(selectedToDelete);
// recursive deleting all files in directory and directory itself
            pathRecursiveDelete(pp);
        }
    }

    public void showAbout(ActionEvent actionEvent) throws IOException {
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("about.fxml"));
        Parent addDialogRoot = aboutLoader.load();
        Stage aboutDialogStage = new Stage();
        aboutDialogStage.setResizable(false);
        aboutDialogStage.setTitle("Description program working");
        aboutDialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene aboutScene = new Scene(addDialogRoot);
        aboutDialogStage.setScene(aboutScene);
        aboutDialogStage.show();

    }

    public void renameFile(ActionEvent actionEvent) {
        tvLeft.getSelectionModel().getSelectedItem();

    }

    /**
     * Mouse Single onClick event handler in table view (right side in application).
     * Using default system applications to open relevant files
     *
     * @param mouseEvent
     */
    public void openFileDeskInTable(MouseEvent mouseEvent) {
        Path selected = tableView.getSelectionModel().getSelectedItem();
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(selected.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openInDeskByClick(MouseEvent mouseEvent) {
    }

    public void showJavaDoc(ActionEvent actionEvent) throws IOException {
        Parent javaDocDialogRoot = FXMLLoader.load(getClass().getResource("webViewJavadoc.fxml"));
        Scene javaDocScene = new Scene(javaDocDialogRoot);
        Stage javadocDialogStage = new Stage();
        javadocDialogStage.setResizable(true);
        javadocDialogStage.setTitle("JavaDoc");
        javadocDialogStage.initModality(Modality.APPLICATION_MODAL);
        javadocDialogStage.setScene(javaDocScene);
        javadocDialogStage.centerOnScreen();
        javadocDialogStage.show();
    }

    /**
     * Method of recursive deleting to delete any files and folders even not empty.
     * It works if you have anough privileges to work with these files.
     *
     * @param path
     */
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

    public int counting() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            counter = i++;
            Thread.sleep(10);
        }
        return counter;
    }

}