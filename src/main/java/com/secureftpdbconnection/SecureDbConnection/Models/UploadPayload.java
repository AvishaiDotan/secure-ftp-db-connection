package com.secureftpdbconnection.SecureDbConnection.Models;

public class UploadPayload {
    private String token;
    private String base64FileString;
    private String fileName;
    private String path;
    private String fileType;

    public UploadPayload(String token, String base64FileString, String fileName, String path, String fileType) {
        this.token = token;
        this.base64FileString = base64FileString;
        this.fileName = fileName;
        this.path = path;
        this.fileType = fileType;
    }

    public String getToken() {
        return token;
    }

    public String getBase64FileString() {
        return base64FileString;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getFileType() {
        return fileType;
    }
}
