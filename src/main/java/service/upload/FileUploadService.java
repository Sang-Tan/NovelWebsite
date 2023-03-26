package service.upload;

import io.github.cdimascio.dotenv.Dotenv;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

public class FileUploadService {
    private static final String UPLOAD_ROOT = Dotenv.load().get("FILE_UPLOAD_PATH").trim();

    /**
     * Uploads a file to the server
     *
     * @param fileName name of the file path (relative to the upload folder). Example: "images/1.jpg"
     * @param filePart the file to upload
     * @throws IOException
     */
    public static void uploadFile(String fileName, Part filePart) throws IOException {
        //remove whitespaces from the file name beginning and end
        fileName = fileName.trim();

        Path filePath = Path.of(UPLOAD_ROOT, fileName);

        File file = new File(filePath.toString());
        if (file.exists()) {
            throw new IOException(String.format("File %s already exists", fileName));
        }

        filePart.write(filePath.toString());
    }

    /**
     * Checks if a http part is an image
     *
     * @param filePart the gttp part to check
     * @return
     * @throws IOException
     */
    public static boolean isImage(Part filePart) throws IOException {
        if (filePart == null)
            return false;

        InputStream inpStream = filePart.getInputStream();
        BufferedImage bufferedImage = ImageIO.read(inpStream);
        return bufferedImage != null;
    }

    /**
     * Get an existing file from upload folder
     *
     * @param relativeFilePath relative path to the file in the upload folder
     * @return the file object if it exists, null otherwise
     */
    public static File getFile(String relativeFilePath) {
        Path filePath = Path.of(UPLOAD_ROOT, relativeFilePath);
        File file = new File(filePath.toString());
        if (file.exists()) {
            return file;
        }
        return null;
    }

    /**
     * Creates a random file name
     *
     * @param relativeFolder the folder where the file will be stored (relative to the upload folder)
     * @param extension      the file extension (without the dot). Example: "jpg"
     * @return the file path (relative to the upload folder)
     */
    public static String randomFileName(String relativeFolder, String extension) {
        String relFilePath;
        do {
            relFilePath = String.format("%s.%s", Path.of(UUID.randomUUID().toString()), extension);
        } while (getFile(Path.of(UPLOAD_ROOT, relFilePath).toString()).exists());
        return relFilePath;
    }
}
