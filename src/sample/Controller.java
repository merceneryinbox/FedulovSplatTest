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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Controller {

    public static MyTreeItem selectedTreeItem;
    public static MyTreeItem parentOfSelectedTreeItem;

    //Menu -> Help -> About
    @FXML
    private MenuItem mnHelpAbout;

    ////////////// ProgressIndicator
    @FXML
    private ProgressIndicator progressIndicator;

    //////////////TREEVIEW
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

    ///////// MAIN MENU VARS
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mnFileNew;
    @FXML
    private MenuItem mnFileOpen;
    @FXML
    private MenuItem mnFileClose;

    ////////CONTROLLERS vars
    private int counter = 0;
    private List<Path> pathsOnDemandList;
    private String nameOfStartPath = "d:\\";
    private Path startPathInControl;
    private List<MyTreeItem> itemsListByPaths;

    // Start Controller initialization block
    public void initialize() throws InterruptedException {
        progressIndicator.setVisible(false);
        itemsListByPaths = new ArrayList<>();

        // creating Path object by String path in filesystem
        startPathInControl = new StartPathGenerator(nameOfStartPath).generatePath();

        // Init root TreeItem to witch others were set
        MyTreeItem rootItem = new MyTreeItem(startPathInControl);
        rootItem.setExpanded(true);
        rootItem.setGraphic(new ImageView(new Image("icoes\\folder_opened.png")));

        // getting sorted MyTreeItemList of sub items in start directory
        itemsListByPaths = new ItemPopulator(rootItem).populate();
        Collections.sort(itemsListByPaths, new Comparator<MyTreeItem>() {
            @Override
            public int compare(MyTreeItem o1, MyTreeItem o2) {
                return o1.getValue().toString().compareTo(o2.toString());
            }
        });
        // set up all subitems at root
        rootItem.getChildren().addAll(itemsListByPaths);

        // set root TreeItem at TreeView
        tvLeft.setRoot(rootItem);

        // set single selection mode et MyTreeItem
        tvLeft.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // describe behavior of selected TreeItem in TreeView and behavior of TableView if TreeItem is Selected
        tvLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                tvLeft.setShowRoot(false);
                selectedTreeItem = (MyTreeItem) newValue; // for outbound methods using
                parentOfSelectedTreeItem = (MyTreeItem) selectedTreeItem.getParent(); //....
                if (selectedTreeItem.getValue() != null) {
                    // getting List of elements in selected MyTreeItem if it consists of them

                    // getting variables for internal use

                    MyTreeItem selectedItem = (MyTreeItem) newValue;
                    Path p = (Path) selectedItem.getValue();
                    selectedItem.setYetVisited(true);
                    selectedItem.setGraphic(new ImageView(new Image("icoes\\refresh.gif")));

                    Thread fillingItemThread = new Thread(new Runnable() {
                        /**
                         * Run method in Listener
                         */
                        @Override
                        public void run() {
                            // progress indicator init
                            try {
//                            progressIndicator.setVisible(true); // set indicator visible for two seconds
                                Task task = taskWorker(200);


                                progressIndicator.progressProperty().bind(task.progressProperty());

                                Thread threadProgressIndicator = new Thread(task); // fork separate thread
                                threadProgressIndicator.start();
                                Thread.sleep(1000);
                                new FulFillIcoByType(selectedItem).filInTheIconInMyTreeItem();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    fillingItemThread.start(); // start progress indicator thread

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

                        // populate list of sub MyTreeItems's paths
                        for (MyTreeItem mt :
                                subItemsList) {
                            pathListOfMyTreeItemsInListener.add((Path) mt.getValue());
                        }

                        // setting sub MyTreeItems in selected parent Paths onto right TableView
                        tableView.setItems(FXCollections.observableArrayList(pathListOfMyTreeItemsInListener));
                        // refreshing tableView
                        tableView.refresh();

                        // else if path is a file creating list of one single element and pass it in table view
                    } else {
                        pathListOfMyTreeItemsInListener.add(((Path) selectedItem.getValue()));
                        tableView.setItems(FXCollections.observableArrayList(pathListOfMyTreeItemsInListener));

                        // refreshing tableView
                        tableView.refresh();
                    }
                } else {
                    rootItem.getChildren().remove((MyTreeItem) newValue);
//                    rootItem.
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

    /**
     * Create path  Menu File -> New
     *
     * @param actionEvent
     * @throws IOException
     */

    public void makeFolder(ActionEvent actionEvent) throws IOException {
        Scene addDialogScene = new Scene((new FXMLLoader(getClass().getResource("AlertDialog_css.fxml"))).load());

        Stage addDialogStage = new Stage();
        addDialogStage.setTitle("Make new folder dialog");
        addDialogStage.initModality(Modality.WINDOW_MODAL);
        addDialogStage.setScene(addDialogScene);
        addDialogStage.show();
    }

    /**
     * Open file in with fileChooser invoke by Menu File-> Open
     *
     * @param actionEvent
     */
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

    /**
     * Close window invoke by Menu File-> Close
     *
     * @param actionEvent
     */
    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Delete selected path in filesystem and delete selected MyTreeItem in TreeView invoke by Menu Edit -> Delete
     *
     * @param actionEvent
     */
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

    ////////

    /**
     * Show about description of this programm invoke by Menu Help -> About
     *
     * @param actionEvent
     * @throws IOException
     */

    public void showAbout(ActionEvent actionEvent) throws IOException {
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("about.fxml"));
        Parent addDialogRoot = aboutLoader.load();
        Stage aboutDialogStage = new Stage();
        aboutDialogStage.setResizable(false);
        aboutDialogStage.setTitle("Description program working");
        aboutDialogStage.initModality(Modality.WINDOW_MODAL);
        Scene aboutScene = new Scene(addDialogRoot);
        aboutDialogStage.setScene(aboutScene);
        aboutDialogStage.show();
    }

    /**
     * Invoke rename method to rename selected MyTreeItem element in TreeView and relevant path in file system
     * by clicking on Menu Edit -> Rename
     *
     * @param actionEvent
     * @throws IOException
     */
    public void renameFileInSample(ActionEvent actionEvent) throws IOException {
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