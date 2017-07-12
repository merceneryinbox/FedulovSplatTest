package filebrowsertools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * Created by mercenery on 13.07.2017.
 */
public class FullFilItemsByIcoes {
    private String imagePathOpenF = "folder-open_16.ico";
    private String imagePathCloseF = "folder-close_16.ico";

    private ImageView iconClosedFolder;
    private ImageView iconOpenFolder;

    private Image imgOpF = new Image(imagePathOpenF);
    private Image imgCloF = new Image(imagePathCloseF);

    private List<MyTreeItem> incomeList;
    private List<MyTreeItem> outcomeList;


    public FullFilItemsByIcoes(List list) {

        iconClosedFolder = new ImageView(imgCloF);
        iconOpenFolder = new ImageView(imgOpF);

        incomeList = list;
    }

    public List<MyTreeItem> filling(){
        for (MyTreeItem it :
                incomeList) {
            it.setGraphic(iconClosedFolder);
            outcomeList.add(it);
        }
        return outcomeList;
    }

}
