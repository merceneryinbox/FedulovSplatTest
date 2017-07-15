package filebrowsertools;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mercenery on 13.07.2017.
 */
public class ItemListPopulator {
    private List<Path> pathsOnDemandList = new ArrayList<>();
    private List<MyTreeItem> itemsListByPaths;

// Constructor -----------------------------------------------------

    public ItemListPopulator(Path startPathInPopulator) {
        itemsListByPaths = new ArrayList<MyTreeItem>();

        // getting path List one level down
        pathsOnDemandList = new NioFolderObserver(startPathInPopulator).getpathList();
    }

    /**
     * Generate list of MyItems filled by icons and relevant Path method
     * @return
     */
    public List<MyTreeItem> populateTreeItemListForController() {
        for (Path p :
                pathsOnDemandList) {
            // inject value(Path) into MyTreeItem
            MyTreeItem mti = new MyTreeItem(p,false);

            // assign icons to MyTreeItem
            mti = new FulFillOneItemIcoByType(mti).filInTheIcon();

            // adding handled MyTreeItem in MyTreeItem List
            itemsListByPaths.add(mti);
        }
        // return handeled MyTreeItems List
        return itemsListByPaths;
    }
}
