package filebrowsertools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by mercenery on 14.07.2017.
 */
public class MyTreeItemsIcoAssigner {
    private MyTreeItem incomItem;

    // block of images for assigning
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


    public MyTreeItemsIcoAssigner(MyTreeItem incomItem) {
        this.incomItem = incomItem;

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

    public MyTreeItem setOpenFolderIco() {
        incomItem.setGraphic(iconOpenFolder);
        return incomItem;
    }

    public MyTreeItem setClosedFolderIco() {
        incomItem.setGraphic(iconClosedFolder);
        return incomItem;
    }

    public MyTreeItem setArchiveFileIco() {
        incomItem.setGraphic(iconArchiveFile);
        return incomItem;
    }

    public MyTreeItem setExeFileIco() {
        incomItem.setGraphic(iconExeFile);
        return incomItem;
    }

    public MyTreeItem setOfficeFileIco() {
        incomItem.setGraphic(iconOfficeFile);
        return incomItem;
    }

    public MyTreeItem setWebFileIco() {
        incomItem.setGraphic(iconWebFile);
        return incomItem;
    }

    public MyTreeItem setPictureFileIco() {
        incomItem.setGraphic(iconPictureFile);
        return incomItem;
    }

    public MyTreeItem setMultimediaFileIco() {
        incomItem.setGraphic(iconMultimediaFile);
        return incomItem;
    }

    public MyTreeItem setOtherFileIco() {
        incomItem.setGraphic(iconOtherFile);
        return incomItem;
    }
}
