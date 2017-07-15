import filebrowsertools.NioFolderObserver;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by mercenery on 11.07.2017.
 */
public class tester {
    public static void main(String[] args) {
        for (Path t :
                new NioFolderObserver(FileSystems.getDefault().getPath("d:\\")).getSubPathsList()) {
            if (Files.isDirectory(t)){
                new NioFolderObserver(t).getSubPathsList();
                System.out.println("----------------------------------------------");
            }
        }
    }
}
