package filebrowsertools;

/**
 * Created by mercenery on 14.07.2017.
 */
public class FileTypesFilter {

    private String fullFileName;
    private String resultType;

    private boolean isFolder = false;
    private boolean isExecutable = false;
    private boolean isOffice = false;
    private boolean isArchive = false;
    private boolean isWeb = false;
    private boolean isOthers = false;
    private boolean isMultimedia = false;
    private boolean isPicture = false;


    public FileTypesFilter(String fullFileName) {
        this.fullFileName = fullFileName;
        isFolder = fullFileName.endsWith("/") || fullFileName.endsWith("");
        isArchive = fullFileName.endsWith(".zip") || fullFileName.endsWith(".arj") || fullFileName.endsWith("rar");
        isExecutable = fullFileName.endsWith(".exe") || fullFileName.endsWith(".msi") || fullFileName.endsWith(".bat");
        isOffice = fullFileName.endsWith(".doc") || fullFileName.endsWith(".xls") || fullFileName.endsWith(".txt") || fullFileName.endsWith(".ppt") || fullFileName.endsWith(".pdf");
        isWeb = fullFileName.endsWith(".html");
        isPicture = fullFileName.endsWith(".jpg") || fullFileName.endsWith(".bmp") || fullFileName.endsWith(".tiff") || fullFileName.endsWith(".gif");
        isMultimedia = fullFileName.endsWith(".mp3") || fullFileName.endsWith(".avi") || fullFileName.endsWith(".mp4") || fullFileName.endsWith(".wmv");
        isOthers = (isFolder == false) && (isArchive == false) && (isExecutable == false) && (isOffice == false) && (isWeb == false) && (isPicture == false) && (isMultimedia == false);
    }

    public String filterFileByType() {
        if (isFolder) {
            resultType = "folder";
        } else if (isArchive) {
            resultType = "archive";
        } else if (isExecutable) {
            resultType = "exe";
        } else if (isOffice) {
            resultType = "office";
        } else if (isWeb) {
            resultType = "web";
        } else if (isPicture) {
            resultType = "picture";
        } else if (isMultimedia) {
            resultType = "multimedia";
        } else if (isOthers) {
            resultType = "other";
        }
        return resultType;
    }
}
