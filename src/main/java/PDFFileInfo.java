import java.io.File;

public class PDFFileInfo {
    public static void main(String[] args) {
        String filePath = args[0];
        File wordFile = new File(filePath);

        if (wordFile.exists()) {
            //printFileInfo(filePath);
        } else {
            System.out.println("PDF file not found.");
        }
    }
}
