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

    public void initialize() {

        vbView = new WebView();
        WebEngine engine = vbView.getEngine();
        engine.load(this.getClass().getResource("file:///D:/IdeaEEProj/testFX/src/JavaDocs/index.html").toExternalForm());

    }
}
