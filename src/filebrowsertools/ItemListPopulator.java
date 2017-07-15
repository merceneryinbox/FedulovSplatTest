package filebrowsertools;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * ItemListPopulator catch Path , look up for subfolders in it and create MyTreeItems by injecting
 * each element of MyTreeItems with value = Path and icon relevant by its extensions.
 * Created by mercenery on 13.07.2017.
 */
public class ItemListPopulator {
    private List<Path> pathsOnDemandList = new ArrayList<>();
    private List<MyTreeItem> itemsListByPaths;

// Constructor -----------------------------------------------------

    public ItemListPopulator(Path startPathInPopulator) {
        itemsListByPaths = new ArrayList<MyTreeItem>();

        // getting path List one level down
        pathsOnDemandList = new NioFolderObserver(startPathInPopulator).getSubPathsList();
    }

    /**
     * Filling elements in MyItems list by relevant icons
     * @return
     */
    public List<MyTreeItem> populateTreeItemList() {
        for (Path p :
                pathsOnDemandList) {

            // inject value(Path) into MyTreeItem
            MyTreeItem mti = new MyTreeItem(p, false);

            // inject relevant icon into creating MyTreeItem
            mti = new FulFillOneItemIcoByType(mti).filInTheIcon();
            mti.setYetVisited(false);
            // adding handled MyTreeItem in MyTreeItem List
            itemsListByPaths.add(mti);
        }
        // return handeled MyTreeItems List
        return itemsListByPaths;
    }
}
