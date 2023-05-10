package core;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    public static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            throw new IllegalArgumentException("File name has no extension");
        }
        return fileName.substring(dotIndex + 1);
    }

    public static String getFolderName(String filePath) {
        int slashIndex = filePath.lastIndexOf(File.separator);
        if (slashIndex == -1) {
            throw new IllegalArgumentException("File path has no folder");
        }
        return filePath.substring(0, slashIndex);
    }

    public static String getFileName(String filePath) {
        int slashIndex = filePath.lastIndexOf(File.separator);
        if (slashIndex == -1) {
            throw new IllegalArgumentException("File path has no folder");
        }
        return filePath.substring(slashIndex + 1);
    }

    public static boolean isImage(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return false;
        }

        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return bufferedImage != null;
    }

}
