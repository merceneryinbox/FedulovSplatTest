package sample;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Created by mercenery on 18.07.2017.
 */
public class WebViewController {
    public WebView vbView;

    public void initializer() {
        vbView = new WebView();
        WebEngine engine = vbView.getEngine();
        engine.load("JavaDocs/index.html");

    }
}
