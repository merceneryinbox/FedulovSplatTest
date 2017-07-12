package filebrowsertools;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mercenery on 11.07.2017.
 */
public class MyDirectoryWalk extends SimpleFileVisitor<Path> {
    public List<Path> list;
    private Path path;

    public MyDirectoryWalk(Path path) {
        list = new ArrayList<>();
        this.path = path;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileVisitResult fileVisitResult = FileVisitResult.CONTINUE;
        if (!Files.isDirectory(file)) {
            list.add(file);
            fileVisitResult = FileVisitResult.TERMINATE;
        }
        return fileVisitResult;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        list.add(file);
        return FileVisitResult.TERMINATE;
    }
}
