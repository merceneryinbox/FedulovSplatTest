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

    public MyTreeItem(Object value) {
        super(value);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public MyTreeItem(Object value, Node graphic, boolean yetVisited) {
        super(value, graphic);
        path = (Path) value;

        this.yetVisited = yetVisited;
    }

    public MyTreeItem(Object object, Boolean yetVisited) {
        super (object);
        this.yetVisited = yetVisited;
    }

    public boolean isYetVisited() {
        return yetVisited;
    }

    public void setYetVisited(boolean yetVisited) {
        this.yetVisited = yetVisited;
    }
}
