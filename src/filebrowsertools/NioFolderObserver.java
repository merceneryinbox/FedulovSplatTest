package filebrowsertools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by mercenery on 11.07.2017.
 */
public class NioFolderObserver {
    private Path path;
    private List<Path> pathList;
    private List<Path> pathListRecursive;

    /**
     * The NioFO Constructor injects by Path variable to invoke MyDirectoryWalk(Path) extended SimpleFileVisitor<Path>
     * and get back SimpleFileVisitor myDirVisitor witch store data for iterate fs elements in it
     * and return saved List of elements(Path type) to caller from getpathList() method
     * @param path
     */
    public NioFolderObserver(Path path) {
        this.path = path;
        pathList = new ArrayList<>();
        pathListRecursive = new ArrayList<>();
    }

    public List<Path> getpathList() {

        try {
            Files.list(path).forEach(new Consumer<Path>() {
                @Override
                public void accept(Path pa) {
                    pathList.add(pa);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathList;
    }

    public List<Path> getFSElementsAllLevelsDownOnDemand(){
        try {
            Files.list(path).forEach(new Consumer<Path>() {
                @Override
                public void accept(Path paR) {
                    pathListRecursive.add(paR);
                    if (Files.isDirectory(paR)){
                        new NioFolderObserver(paR).getFSElementsAllLevelsDownOnDemand();
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathListRecursive;
    }
}
