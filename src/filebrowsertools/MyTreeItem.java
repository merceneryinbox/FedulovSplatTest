package filebrowsertools;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

import java.nio.file.Path;

/**
 * Created by mercenery on 12.07.2017.
 */
public class MyTreeItem<P, N, B> extends TreeItem {
    private Path path;
    private boolean yetVisited = false;

    public MyTreeItem(Path value) {
        super(value);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public MyTreeItem(Path value, Node graphic, Boolean yetVisited) {
        super(value, graphic);
        this.path = value;
        this.yetVisited = yetVisited;
    }

    public MyTreeItem(Path value, Boolean yetVisited) {
        super(value);
        this.yetVisited = yetVisited;
    }

    public boolean isYetVisited() {
        return yetVisited;
    }

    public void setYetVisited(boolean yetVisited) {
        this.yetVisited = yetVisited;
    }
}
