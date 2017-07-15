package filebrowsertools;

import java.util.ArrayList;
import java.util.List;

/**
 * This class getting a list in constructor, filling it with relevant icons and return it back in fillingListOfMyTreeItems() method
 * Created by mercenery on 13.07.2017.
 */
public class FulFilListItemsIcoByTypes {

    private List<MyTreeItem> incomeList;
    private List<MyTreeItem> outcomeList;

    {
        outcomeList = new ArrayList<>();
    }

    /**
     * Inject income list of MyTreeItems list to handle
     *
     * @param list
     */
    public FulFilListItemsIcoByTypes(List list) {
        incomeList = list;
    }

    /**
     * Handle List of MyTreeItems list(setting the icons to each) with help of FullFillOneItemIcoByTypes
     *
     * @return
     */
    public List<MyTreeItem> fillingListOfMyTreeItems() {
        for (MyTreeItem it :
                incomeList) {
            outcomeList.add(new FulFillIcoByType(it).filInTheIconInMyTreeItem());
        }
        // returning fulfilled list back
        return outcomeList;
    }
}
