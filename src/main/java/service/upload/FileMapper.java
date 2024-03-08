package service.upload;

import core.FileUtil;
import core.config.ApplicationPropertiesReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * One to one mapping between a file path (relative to the upload folder) and a URI.
 * For example : if UPLOAD_URI_PREFIX = "/uploads" and UPLOAD_ROOT = "C:\\uploads",
 * then the file path "images/1.jpg" will be mapped to the URI "/uploads/images/1.jpg",
 * and the file will be stored in "C:\\uploads\images\1.jpg"
 */
public class FileMapper {
    private static final String UPLOAD_ROOT =
            ApplicationPropertiesReader.getInstance().getProperty("upload.root.path");

    private static final String UPLOAD_URI_PREFIX = "/uploads";

    private String containingFolder;
    private String fileName;

    protected FileMapper(String containingFolder, String fileName) {
        this.containingFolder = containingFolder;
        this.fileName = fileName;
    }

    /**
     * Creates a new file mapper and generate random file name
     *
     * @param containFolder the folder where the file will be stored (relative to the upload folder)
     * @param extension     the file extension
     */
    public static FileMapper mapRandomFile(String containFolder, String extension) {
        return new FileMapper(containFolder,
                randomFileName(containFolder, extension));
    }

    /**
     * Creates a new file mapper of an existing file associative with the given URI
     *
     * @param URI the URI associated with the file
     */
    public static FileMapper mapURI(String URI) {
        //remove prefix
        URI = URI.replaceFirst(UPLOAD_URI_PREFIX.toString(), "");

        String filePath = Path.of(URI).toString();
        //get folder name
        String containingFolder = FileUtil.getFolderName(filePath);
        String fileName = FileUtil.getFileName(filePath);

        return new FileMapper(containingFolder, fileName);
    }

    /**
     * Generates a random file name
     *
     * @param containingFolder the folder where the file will be stored (relative to the upload folder)
     * @param extension        the file extension
     * @return the random file name
     */
    private static String randomFileName(String containingFolder, String extension) {
        String randFileName = "";
        do {
            randFileName = String.format("%s.%s", UUID.randomUUID(), extension);
        } while (new File(Path.of(UPLOAD_ROOT, containingFolder, randFileName).toString()).exists());
        return randFileName;
    }

    /**
     * Gets the URI of the file associated with this file mapper
     *
     * @return the URI of the file associated with this file mapper
     */
    public String getURI() {
        String URI = Path.of(UPLOAD_URI_PREFIX, containingFolder, fileName).toString();
        return URI.replace("\\", "/");
    }

    /**
     * Uploads a file to the server
     *
     * @param inputStream the input stream of the file
     * @throws IOException if the file already exists or an error occurs while uploading
     */
    public void uploadFile(InputStream inputStream) throws IOException {
        uploadFile(inputStream, false);
    }

    /**
     * Uploads a file to the server, the file path is associated with the mapper
     *
     * @param inputStream       the input stream of the file
     * @param overwriteExisting if true, the file will be overwritten if it already exists
     * @throws IOException if the file already exists and overwriteExisting is false or an error occurs while uploading
     */
    public void uploadFile(InputStream inputStream, boolean overwriteExisting) throws IOException {
        //create containing folder if not exists
        File uploadFile = new File(Path.of(UPLOAD_ROOT, containingFolder, fileName).toString());
        Path uploadFolderPath = Path.of(UPLOAD_ROOT, containingFolder);
        if (!Files.exists(uploadFolderPath)) {
            File uploadFolder = new File(uploadFolderPath.toString());
            uploadFolder.mkdirs();
        }

        if (overwriteExisting == false && uploadFile.exists()) {
            throw new IOException(String.format("File %s already exists", fileName));
        }

        //upload file
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        Path filePath = Path.of(UPLOAD_ROOT, containingFolder, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath.toString());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        int byteRead;
        while ((byteRead = bufferedInputStream.read()) != -1) {
            bufferedOutputStream.write(byteRead);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    /**
     * Deletes the file associated with this file mapper
     */
    public void delete() {
        File file = new File(Path.of(UPLOAD_ROOT, containingFolder, fileName).toString());
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Gets the file associated with this file mapper
     *
     * @return the file associated with this file mapper
     */
    public File getFile() {
        String URI = this.getURI();

        //remove prefix
        URI = URI.replaceFirst(UPLOAD_URI_PREFIX, "");
        String filePath = Path.of(UPLOAD_ROOT, URI).toString();
        return new File(filePath);
    }

    public void copyFrom(FileMapper fileMapperToCopy) throws IOException {
        File fileToCopy = fileMapperToCopy.getFile();
        File fileToCopyTo = this.getFile();

        InputStream inputStream = new FileInputStream(fileToCopy);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        FileOutputStream fileOutputStream = new FileOutputStream(fileToCopyTo);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        int byteRead;
        while ((byteRead = bufferedInputStream.read()) != -1) {
            bufferedOutputStream.write(byteRead);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        bufferedInputStream.close();
    }

}
