package filebrowsertools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mercenery on 13.07.2017.
 */
public class FullFilItemsByIcoes {
    private String imagePathCloseF = "folder_new.png";
    private String imagePathOpenF = "folder_new.png";

    private Image imgOpF = new Image(imagePathOpenF);
    private ImageView iconOpenFolder;
    private ImageView iconClosedFolder;
    private Image imgCloF = new Image(imagePathCloseF);

    private MyTreeItem treeItem;

    private List<MyTreeItem> incomeList;
    private List<MyTreeItem> outcomeList;

    public FullFilItemsByIcoes(MyTreeItem treeItem) {
        this.treeItem = treeItem;
    }

    public FullFilItemsByIcoes(List list) {
        iconClosedFolder = new ImageView(imgCloF);
        incomeList = list;
        outcomeList = new ArrayList<>();
    }

    public List<MyTreeItem> fillingClosedDir() {
        for (MyTreeItem it :
                incomeList) {
            if (Files.isDirectory(it.getPath())) {
                it.setGraphic(iconClosedFolder);
                outcomeList.add(it);
            } else if (Files.isRegularFile(it.getPath())) {
                it.setGraphic(iconClosedFolder);
            }
            outcomeList.add(it);
        }
        return outcomeList;
    }

//    public
}
