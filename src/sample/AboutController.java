package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.*;

/**
 * Created by mercenery on 18.07.2017.
 */
public class AboutController {
    @FXML
    public Pane pnAbout;

    @FXML
    public TextArea txtAreaAbout;

    public void initialize() {

        File aboutFile = new File("Descriptionofprogramm.txt");
        try {
            String s = "";
            BufferedReader br = new BufferedReader(new FileReader(aboutFile));
            while (br.ready()) {
                s = s + "\n" + br.readLine();
            }
            txtAreaAbout.setText(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
