package com.secureftpdbconnection.SecureDbConnection.Services;

import com.secureftpdbconnection.SecureDbConnection.Models.UploadPayload;
import com.secureftpdbconnection.SecureDbConnection.Repositories.FtpSettingsRepository;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class FTPUploadService {

    private final FtpSettingsRepository ftpSettingsRepository;

    public FTPUploadService(FtpSettingsRepository ftpSettingsRepository) {
        this.ftpSettingsRepository = ftpSettingsRepository;
    }

    public ResponseEntity<String> uploadFile(UploadPayload uploadPayload) throws IOException {
        ResponseEntity<String> response = null;
        InputStream inputStream = null;
        FTPClient ftpClient = null;
        try {

            var ftpSettings = ftpSettingsRepository.findItemByToken(uploadPayload.getToken());
            if (ftpSettings == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] fileArray = Base64.getDecoder().decode(uploadPayload.getBase64FileString());
            inputStream = new ByteArrayInputStream(fileArray);

            ftpClient = new FTPClient();

            ftpClient.connect(ftpSettings.getHost());
            ftpClient.login(ftpSettings.getUserName(), ftpSettings.getPassword());
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            var path = (uploadPayload.getPath().isEmpty()) ? "/" : uploadPayload.getPath();
            var fileName = uploadPayload.getFileName().isEmpty()  ? LocalDateTime.now().toString() : uploadPayload.getFileName();
            var fileType = uploadPayload.getFileType().isEmpty() ? "" : uploadPayload.getFileType();

            String remoteFile = path + fileName + "." + fileType;
            ftpClient.storeFile(remoteFile, inputStream);

            response = ResponseEntity.ok().build();
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().build();
        }
        finally {
            inputStream.close();
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }

            return (response != null) ? response : ResponseEntity.internalServerError().build();
        }
    }
}
