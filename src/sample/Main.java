package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.getStylesheets().add("caspian.css");
        primaryStage.setTitle("Fedulov Oleg (mercenery@inbox.ru; +7 921 989 68 90) for SPLAT test. (Stage one)");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
