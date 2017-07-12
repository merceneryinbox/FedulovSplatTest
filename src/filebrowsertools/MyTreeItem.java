package filebrowsertools;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

/**
 * Created by mercenery on 12.07.2017.
 */
public class MyTreeItem<P, N, B> extends TreeItem {
    private boolean yetVisited = false;

    public MyTreeItem(Object value) {
        super(value);
    }

    public MyTreeItem(Object value, Node graphic, boolean yetVisited) {
        super(value, graphic);
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
