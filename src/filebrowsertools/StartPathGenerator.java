package filebrowsertools;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by mercenery on 13.07.2017.
 */
public class StartPathGenerator {
    private String startDirName;
    private FileSystem fileSystem;

    public StartPathGenerator(String startDirName) {
        this.startDirName = startDirName;
        fileSystem = FileSystems.getDefault();

    }

    public Path generatePath(){

        return fileSystem.getPath(startDirName);
    }
}
