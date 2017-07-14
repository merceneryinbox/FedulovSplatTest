package filebrowsertools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mercenery on 13.07.2017.
 */
public class FullFilListItemsByIcoes {
    private String imagePathCloseF = "icoes\\folder_closed.png";
    private String imagePathOpenF = "icoes\\folder_opened.png";
    private String imageReductableFile = "icoes\\text.png";

    private ImageView iconOpenFolder;
    private ImageView iconClosedFolder;
    private ImageView iconReductableFile;

    private Image imgOpF;
    private Image imgCloF;
    private Image imgReducF;

    private MyTreeItem treeItem;

    private List<MyTreeItem> incomeList;
    private List<MyTreeItem> outcomeList;

    {

        imgOpF = new Image(imagePathOpenF);
        imgCloF = new Image(imagePathCloseF);
        imgReducF = new Image(imageReductableFile);

        iconClosedFolder = new ImageView(imgCloF);
        iconOpenFolder = new ImageView(imgOpF);
        iconReductableFile = new ImageView(imgReducF);

        outcomeList = new ArrayList<>();
    }

    public FullFilListItemsByIcoes(MyTreeItem treeItem) {
        this.treeItem = treeItem;
    }

    public FullFilListItemsByIcoes(List list) {
        incomeList = list;
    }

    public List<MyTreeItem> fillingListOfClosedDir() {
        for (MyTreeItem it :
                incomeList) {
            if (Files.isDirectory((Path) it.getValue())) {
                it.setGraphic(iconClosedFolder);
                outcomeList.add(it);
            } else {
                it.setGraphic(iconReductableFile);
            }
            outcomeList.add(it);
        }
        return outcomeList;
    }
}
