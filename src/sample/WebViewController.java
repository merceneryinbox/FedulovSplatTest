package sample;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Created by mercenery on 18.07.2017.
 */
public class WebViewController {

    @FXML
    private WebView vbView;

    /**
     * Initializing and show JavaDocs
     */
    public void initialize() {

        vbView = new WebView();
        WebEngine webEngine = vbView.getEngine();
        webEngine.load("file:///index.html");

    }
}
