package filebrowsertools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mercenery on 14.07.2017.
 */
public class FulFillIcoByType {


    private List<MyTreeItem> incomeList = null;
    private List<MyTreeItem> outcomeList = null;
    private MyTreeItem resultTreeItem = null;

    {
        outcomeList = new ArrayList<>();
    }

    /**
     * Constructor inject dependency with single MyTreeItem
     *
     * @param incomTreeItem
     */
    public FulFillIcoByType(MyTreeItem incomTreeItem) {
        resultTreeItem = incomTreeItem;
    }

    /**
     * Constructor inject dependency with MyTreeItems list
     *
     * @param incomeList
     */
    public FulFillIcoByType(List incomeList) {
        this.incomeList = incomeList;
    }

    /**
     * Main method in FulFillIcoByType witch select what really has been injected list or single MyTreeItem
     * and return handled essence
     *
     * @return
     */
    public MyTreeItem filInTheIconInMyTreeItem() {

        Path p = (Path) resultTreeItem.getValue();
        String fullFileName = p.toString();

        // Getting the type of selected MyTreeItem
        String test = new FileTypesFilter(fullFileName).filterFileByType();

        /**
         * Handle MyTreeItem by setting icons according to its value extensions
         */
        switch (test) {
            case "folder":
                if (resultTreeItem.isYetVisited() == true) {
                    String imageFolderOpenF = "icoes\\folder_opened.png";
                    Image imgOpenFolder = new Image(imageFolderOpenF);
                    ImageView iconOpenFolder = new ImageView(imgOpenFolder);
                    resultTreeItem.setGraphic(iconOpenFolder);
                    break;
                } else if (resultTreeItem.isYetVisited() == false) {
                String imageFolderCloseF = "icoes\\folder_closed.png";
                Image imgClosedFolder = new Image(imageFolderCloseF);
                ImageView iconClosedFolder = new ImageView(imgClosedFolder);
                resultTreeItem.setGraphic(iconClosedFolder);
                break;
            }
            case "archive":
                String imageArchiveFile = "icoes\\archive.png";
                Image imgArchiveFile = new Image(imageArchiveFile);
                ImageView iconArchiveFile = new ImageView(imgArchiveFile);
                resultTreeItem.setGraphic(iconArchiveFile);
                break;
            case "exe":
                String imageExeFile = "icoes\\exe.png";
                Image imgExeFile = new Image(imageExeFile);
                ImageView iconExeFile = new ImageView(imgExeFile);
                resultTreeItem.setGraphic(iconExeFile);
                break;
            case "office":
                String imageOfficeFile = "icoes\\office.png";
                Image imgOfficeFile = new Image(imageOfficeFile);
                ImageView iconOfficeFile = new ImageView(imgOfficeFile);
                resultTreeItem.setGraphic(iconOfficeFile);
                break;
            case "web":
                String imageWebFile = "icoes\\web.png";
                Image imgWebFile = new Image(imageWebFile);
                ImageView iconWebFile = new ImageView(imgWebFile);
                resultTreeItem.setGraphic(iconWebFile);
                break;
            case "picture":
                String imagePictureFile = "icoes\\picture.png";
                Image imgPictureFile = new Image(imagePictureFile);
                ImageView iconPictureFile = new ImageView(imgPictureFile);
                resultTreeItem.setGraphic(iconPictureFile);
                break;
            case "multimedia":
                String imageMultimediaFile = "icoes\\multimedia.png";
                Image imgMultimediaFile = new Image(imageMultimediaFile);
                ImageView iconMultimediaFile = new ImageView(imgMultimediaFile);
                resultTreeItem.setGraphic(iconMultimediaFile);
                break;
            case "others":
                String imageOtherFile = "icoes\\other.png";
                Image imgOtherFile = new Image(imageOtherFile);
                ImageView iconOtherFile = new ImageView(imgOtherFile);
                resultTreeItem.setGraphic(iconOtherFile);
                break;
        }
        return resultTreeItem;
    }

    public List<MyTreeItem> fillInTheIconsInMyTreeItemsList() {

        // recursive filling MyTreeItems elements of list one-by-one with help of domestic method
        for (MyTreeItem it :
                incomeList) {
            MyTreeItem mti = new FulFillIcoByType(it).filInTheIconInMyTreeItem();
            mti.setYetVisited(false);
            outcomeList.add(mti);
        }
        // returning fulfilled list back
        return outcomeList;
    }
}