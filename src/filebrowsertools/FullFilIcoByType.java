package filebrowsertools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by mercenery on 14.07.2017.
 */
public class FullFilIcoByType {
    private String test;
    private MyTreeItem resultTreeItem;

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

    private Image imgOpF;
    private Image imgCloF;
    private Image imgReducF;

    public FullFilIcoByType(MyTreeItem incomTreeItem) {
        resultTreeItem = incomTreeItem;
        test = incomTreeItem.getPath().getFileName().toString();

        imgOpF = new Image(imageFolderOpenF);
        imgCloF = new Image(imageFolderCloseF);
        imgReducF = new Image(imageReductableFile);

        iconClosedFolder = new ImageView(imgCloF);
        iconOpenFolder = new ImageView(imgOpF);
        iconReductableFile = new ImageView(imgReducF);

    }

    public MyTreeItem fillInTheIcon() {
        switch (test) {
            case "folder":
                if (resultTreeItem.isYetVisited()) {
                    resultTreeItem.setGraphic(iconOpenFolder);
                } else {
                    resultTreeItem.setGraphic(iconClosedFolder);
                }
            case "archive":
                resultTreeItem.setGraphic();
        }
        return resultTreeItem;
    }
}
