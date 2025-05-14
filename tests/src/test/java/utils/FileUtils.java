package utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class FileUtils {
    public static void deleteFiles(String directory) throws IOException {
        Files.walkFileTree(Paths.get(directory), new SimpleFileVisitor<java.nio.file.Path>() {
            @Override
            public FileVisitResult visitFile(
                java.nio.file.Path file, BasicFileAttributes attrs
            ) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
    
            @Override
            public FileVisitResult postVisitDirectory(
                java.nio.file.Path dir, IOException exc
            ) throws IOException {
                if (!dir.equals(Paths.get(directory))) {
                    Files.delete(dir);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static int countFiles(String directory) throws IOException {
    AtomicInteger fileCount = new AtomicInteger(0);
    Files.walkFileTree(Paths.get(directory), new SimpleFileVisitor<java.nio.file.Path>() {
        @Override
        public FileVisitResult visitFile(
            java.nio.file.Path file, BasicFileAttributes attrs
        ) throws IOException {
            fileCount.incrementAndGet();
            return FileVisitResult.CONTINUE;
        }
    });
    return fileCount.get();
    }
}
