import java.io.File;

public class WordFileInfo {
    public static void main(String[] args) {
        String filePath = args[0];
        File wordFile = new File(filePath);

        if (wordFile.exists()) {
            //printFileInfo(filePath);
        } else {
            System.out.println("Word file not found.");
        }
    }

}
