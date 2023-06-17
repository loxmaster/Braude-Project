package logic;
import java.io.Serializable;

public class FileUploadMessage implements Serializable {
    private String fileId,filename;

    public String getFilename() {
        return filename;
    }

    private byte[] fileContent;

    public FileUploadMessage(String fileId, byte[] fileContent, String filename) {
        this.fileId = fileId;
        this.fileContent = fileContent;
        this.filename = filename;
    }

    public String getFileId() {
        return fileId;
    }

    public byte[] getFileContent() {
        return fileContent;
    }
}