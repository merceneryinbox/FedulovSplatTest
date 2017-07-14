package filebrowsertools;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Path;

/**
 * MyTreeItem class extends the TreeItem class, MyTreeItem has new static fields - yetVisited.
 * This field will be used to check if Item was already visited.
 * For not handle already handled Items
 * Created by mercenery on 12.07.2017.
 */
public class MyTreeItem<P, N, B> extends TreeItem {

    // new field
    private static boolean yetVisited = false;
    private static Path path;



    // inject MyTreeItem in constructor by Path
    public MyTreeItem(Path value) {
        super(value);
    }

    // inject MyTreeItem in constructor by Path, image and visited or not flag
    public MyTreeItem(Path value, Node graphic, Boolean yetVisited) {
        super(value, graphic);
        this.yetVisited = yetVisited;
    }

    // inject MyTreeItem in constructor by Path and visited or not flag
    public MyTreeItem(Path value, Boolean yetVisited) {
        super(value);
        this.yetVisited = yetVisited;
    }

    // getting Path value from MyTreeItem
    public Path getPath() {
        return path;
    }

    // setting Path value
    public void setPath(Path path) {
        this.path = path;
    }

    public boolean isYetVisited() {
        return yetVisited;
    }

    public void setYetVisited(boolean yetVisited) {
        this.yetVisited = yetVisited;
    }


}