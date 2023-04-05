import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FileWatcher {
    public static void main(String[] args) throws Exception {
        TextFileInfo textFileInfo = new TextFileInfo();

        // Requests the directory
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the directory to watch: ");
        String folderPath = scanner.nextLine();

        Path watchedPath = Paths.get(folderPath);
        Path processedPath = watchedPath.resolve("processed");

        // Create the processed directory if it does not exist
        if (!Files.exists(processedPath)) {
            try {
                Files.createDirectory(processedPath);
            } catch (IOException e) {
                System.err.println("Error creating directory: " + e.getMessage());
            }
        }

        // Process existing files
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(watchedPath, "*.txt")) {
            for (Path filePath : stream) {
                textFileInfo.printFileInfo(filePath.toString());
            }
        } catch (IOException e) {
            System.err.println("Error processing existing files: " + e.getMessage());
        }

        // watching routine
        WatchService watchService = FileSystems.getDefault().newWatchService();
        watchedPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        while (true) {
            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path filename = ev.context();

                // Check if the new file is a .txt file, and if so, process it
                if (filename.toString().endsWith(".txt")) {

                    textFileInfo.printFileInfo(watchedPath.resolve(filename).toString());
                   Path filePath = watchedPath.resolve(filename);
                   try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

                        // Move the processed file to the processed directory
                        Path processedFilePath = processedPath.resolve(filename);
                        try {
                            Files.move(filePath, processedFilePath);
                            System.out.println("Moved processed file to: " + processedFilePath);
                        } catch (IOException e) {
                            System.err.println("Error moving file: " + e.getMessage());
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading file: " + e.getMessage());
                    }
                }

                // Check if the new file is a .pdf file, and if so, process it
                if (filename.toString().endsWith(".pdf")) {
                    //in case we want to scale the app to accept various types of files, this is how I'd approach it
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        }
    }
}