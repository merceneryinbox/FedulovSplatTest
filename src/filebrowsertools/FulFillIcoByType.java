package filebrowsertools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by mercenery on 14.07.2017.
 */
public class FulFillIcoByType {
    private List<MyTreeItem> incomeList = null;
    private List<MyTreeItem> outcomeList;

    private MyTreeItem resultTreeItem = null;
    private MyTreeItem incomTreeItem = null;

    private String imageFolderCloseF = "icoes\\folder_closed.png";
    private String imageFolderOpenF = "icoes\\folder_opened.png";
    private String imageArchiveFile = "icoes\\archive.png";
    private String imageExeFile = "icoes\\exe.png";
    private String imageOfficeFile = "icoes\\office.png";
    private String imageWebFile = "icoes\\web.png";
    private String imagePictureFile = "icoes\\picture.png";
    private String imageMultimediaFile = "icoes\\multimedia.png";
    private String imageOtherFile = "icoes\\other.png";

    private ImageView iconOpenFolder;
    private ImageView iconClosedFolder;
    private ImageView iconArchiveFile;
    private ImageView iconExeFile;
    private ImageView iconOfficeFile;
    private ImageView iconWebFile;
    private ImageView iconPictureFile;
    private ImageView iconMultimediaFile;
    private ImageView iconOtherFile;

    private Image imgOpenFolder;
    private Image imgClosedFolder;
    private Image imgArchiveFile;
    private Image imgExeFile;
    private Image imgOfficeFile;
    private Image imgWebFile;
    private Image imgPictureFile;
    private Image imgMultimediaFile;
    private Image imgOtherFile;

    {

        imgOpenFolder = new Image(imageFolderOpenF);
        imgClosedFolder = new Image(imageFolderCloseF);
        imgArchiveFile = new Image(imageArchiveFile);
        imgExeFile = new Image(imageExeFile);
        imgOfficeFile = new Image(imageOfficeFile);
        imgWebFile = new Image(imageWebFile);
        imgPictureFile = new Image(imagePictureFile);
        imgMultimediaFile = new Image(imageMultimediaFile);
        imgOtherFile = new Image(imageOtherFile);

        iconOpenFolder = new ImageView(imgOpenFolder);
        iconClosedFolder = new ImageView(imgClosedFolder);
        iconArchiveFile = new ImageView(imgArchiveFile);
        iconExeFile = new ImageView(imgExeFile);
        iconOfficeFile = new ImageView(imgOfficeFile);
        iconWebFile = new ImageView(imgWebFile);
        iconPictureFile = new ImageView(imgPictureFile);
        iconMultimediaFile = new ImageView(imgMultimediaFile);
        iconOtherFile = new ImageView(imgOtherFile);
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
     * @param list
     */
    public FulFillIcoByType(List list) {
        incomeList = list;
    }

    /**
     * Main method in FulFillIcoByType witch select what really has been injected list or single MyTreeItem
     * and return handled essence
     *
     * @return
     */
    public MyTreeItem filInTheIconInMyTreeItem() {
        if (incomeList == null) {

            Path p = (Path) resultTreeItem.getValue();
            System.out.println(p);
            String fullFileName = p.toString();
            String test = new FileTypesFilter(fullFileName).filterFileByType();
            switch (test) {
                case "folder":
                    if (resultTreeItem.isYetVisited()) {
                        resultTreeItem.setGraphic(iconOpenFolder);
                        break;
                    } else {
                        resultTreeItem.setGraphic(iconClosedFolder);
                        break;
                    }
                case "archive":
                    resultTreeItem.setGraphic(iconArchiveFile);
                    break;
                case "exe":
                    resultTreeItem.setGraphic(iconExeFile);
                    break;
                case "office":
                    resultTreeItem.setGraphic(iconOfficeFile);
                    break;
                case "web":
                    resultTreeItem.setGraphic(iconWebFile);
                    break;
                case "picture":
                    resultTreeItem.setGraphic(iconPictureFile);
                    break;
                case "multimedia":
                    resultTreeItem.setGraphic(iconMultimediaFile);
                    break;
                case "others":
                    resultTreeItem.setGraphic(iconOtherFile);
                    break;
                default:
                    resultTreeItem.setGraphic(iconOtherFile);
                    break;
            }
        }
        return resultTreeItem;
    }

    public List<MyTreeItem> fillInTheIconsInMyTreeItemsList() {
        if (incomTreeItem == null) {
            // recursive filling MyTreeItems elements of list one-by-one with help of domestic method
            for (MyTreeItem it :
                    incomeList) {
                outcomeList.add(new FulFillIcoByType(it).filInTheIconInMyTreeItem());
            }
        }
        // returning fulfilled list back
        return outcomeList;
    }
}