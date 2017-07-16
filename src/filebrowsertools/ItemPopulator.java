package filebrowsertools;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * ItemPopulator catch Path , look up for subfolders in it and create MyTreeItems by injecting
 * each element of MyTreeItems with value = Path and icon relevant by its extensions.
 * Created by mercenery on 13.07.2017.
 */
public class ItemPopulator {

    private Path path;
    private List<MyTreeItem> itemsList;
    private MyTreeItem item;
    private List<Path> listOfSubPaths;

    /**
     * DI in constructor by MyTreeItem
     *
     * @param item
     */
    public ItemPopulator(MyTreeItem item) {
        itemsList = new ArrayList<>();
        this.item = item;
        path = (Path) item.getValue();
        listOfSubPaths = new NioFolderObserver(path).getSubPathsList();
    }

    /**
     * Main method in ItemPopulator witch returns list of MyTreeItems if injected TreeItem has subItems(subdirs in dir)
     *
     * @return
     */
    public List<MyTreeItem> populate() {
        /**
         * creating MyTreeItems list using existing path.
         * In constructor.
         */
        for (Path p :
                listOfSubPaths) {
            itemsList.add(new MyTreeItem(p));
        }

        /**
         * Filling list of MyTreeItems with relevant icoes
         */
        itemsList = new FulFillIcoByType(itemsList).fillInTheIconsInMyTreeItemsList();
        // return handled MyTreeItems List
        return itemsList;
    }
}
