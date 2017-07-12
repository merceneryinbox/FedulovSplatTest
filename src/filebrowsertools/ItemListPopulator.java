package filebrowsertools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mercenery on 13.07.2017.
 */
public class ItemListPopulator {
    private final Path startPathInPopulator;
    private List<Path> pathsOnDemandList = new ArrayList<>();
    private List<MyTreeItem> itemsListByPaths;

    private FileSystem fs;
    private String nameOfStartPath;


// Constructor -----------------------------------------------------

    public ItemListPopulator(Path startPathInPopulator) {
        itemsListByPaths = new ArrayList<MyTreeItem>();
        this.startPathInPopulator = startPathInPopulator;

        // getting an Object to further internal reading its contains
        NioFolderObserver nfo = new NioFolderObserver(startPathInPopulator);
        // getting PathList to populate TreeItems and return TreeItemsList with populated Items
        pathsOnDemandList = nfo.getpathList();
    }
    public List<MyTreeItem> populateTreeItemListForController(){
        for (Path p :
                pathsOnDemandList) {
                itemsListByPaths.add(new MyTreeItem(p,false));
        }
        return itemsListByPaths;
    }
}
