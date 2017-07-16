package filebrowsertools;

import java.nio.file.Path;

/**
 * Created by mercenery on 14.07.2017.
 */
public class FileTypesFilter {
    private Path path;

    private String fullFileName;
    private String resultType;

    private boolean isFolder = false;
    private boolean isExecutable = false;
    private boolean isOffice = false;
    private boolean isArchive = false;
    private boolean isWeb = false;
    private boolean isPicture = false;
    private boolean isMultimedia = false;
    private boolean isOthers = false;

    /**
     * FileTypesFilter constructor take String name of Path to witch apply filter of types
     *
     * @param fullFileName
     */
    public FileTypesFilter(String fullFileName) {
        this.fullFileName = fullFileName;
        if (fullFileName.contains(".")) {
            isArchive = fullFileName.endsWith(".zip") || fullFileName.endsWith(".arj") || fullFileName.endsWith("rar");
            isExecutable = fullFileName.endsWith(".exe") || fullFileName.endsWith(".msi") || fullFileName.endsWith(".bat");
            isOffice = fullFileName.endsWith(".djvu") || fullFileName.endsWith(".doc") || fullFileName.endsWith(".xls") || fullFileName.endsWith(".txt") || fullFileName.endsWith(".ppt") || fullFileName.endsWith(".pdf");
            isWeb = fullFileName.endsWith(".html") || fullFileName.endsWith(".xml") || fullFileName.endsWith(".fxml");
            isPicture = fullFileName.endsWith(".jpg") || fullFileName.endsWith(".bmp") || fullFileName.endsWith(".tiff") || fullFileName.endsWith(".gif") || fullFileName.endsWith(".png");
            isMultimedia = fullFileName.endsWith(".mp3") || fullFileName.endsWith(".avi") || fullFileName.endsWith(".mp4") || fullFileName.endsWith(".wmv");
            isOthers = (isFolder == false) && (isArchive == false) && (isExecutable == false) && (isOffice == false) && (isWeb == false) && (isPicture == false) && (isMultimedia == false);
        } else {
            isFolder = true;
        }
    }

    /**
     * FileTypesFilter constructor Path to witch apply filter of types
     *
     * @param givenPath
     */
    public FileTypesFilter(Path givenPath) {
        path = givenPath;
        fullFileName = path.getFileName().toString();
    }

    /**
     * Single method of FiletypesFilter witch is really handle the filename type, and return result in String answer -
     * its tell what type of file by its filename
     *
     * @return
     */

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
            resultType = "others";
        } else {
            resultType = "others";
        }
        return resultType;
    }
}
