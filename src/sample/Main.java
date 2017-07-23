package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
        primaryStage.setTitle("Fedulov Oleg (mercenery@inbox.ru; +7 921 989 68 90) file commander");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
