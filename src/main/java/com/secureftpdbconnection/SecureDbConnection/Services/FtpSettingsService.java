package com.secureftpdbconnection.SecureDbConnection.Services;

import com.secureftpdbconnection.SecureDbConnection.Models.FtpSettings;
import com.secureftpdbconnection.SecureDbConnection.Repositories.FtpSettingsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FtpSettingsService {

    private final FtpSettingsRepository ftpSettingsRepository;
    private final EncryptionService encryptionService;
    public FtpSettingsService(FtpSettingsRepository FtpSettingsRepository, EncryptionService encryptionService) {
        this.ftpSettingsRepository = FtpSettingsRepository;
        this.encryptionService = encryptionService;
    }
    public ResponseEntity<String> deleteFtpSettings(String token, String tokenPassword) {
        try {
            if (token == null || tokenPassword == null) {
                return ResponseEntity.badRequest().body("Invalid data");
            }

            token = encryptionService.encrypt(token);

            var dbSettings = ftpSettingsRepository.findItemByToken(token);
            if (dbSettings == null) {
                return ResponseEntity.badRequest().body("Ftp Settings not found");
            }

            if (!dbSettings.getTokenPassword().equals(tokenPassword)) {
                return ResponseEntity.badRequest().body("Invalid token password");
            }

            ftpSettingsRepository.deleteById(dbSettings.getId());

            // Implementation logic to create data
            return ResponseEntity.ok("Data deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> postFtpSettings(FtpSettings settings) {
        try {
            if (!settings.isValid()) {
                return ResponseEntity.badRequest().body("Invalid data");
            }

            settings = encryptionService.getEncryptSettings(settings);

            var dbSettings = ftpSettingsRepository.findItemByToken(settings.getToken());
            if (dbSettings != null) {
                return ResponseEntity.badRequest().body("Token already exists");
            }

            ftpSettingsRepository.insert(settings);

            // Implementation logic to create data
            return ResponseEntity.ok("Data created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload to FTP server");
        }
    }

    public ResponseEntity<String> putFtpSettings(FtpSettings settings) {
        try {
            if (!settings.isValid()) {
                return ResponseEntity.badRequest().body("Invalid data");
            }

            settings = encryptionService.getEncryptSettings(settings);

            var dbSettings = ftpSettingsRepository.findItemByToken(settings.getToken());
            if (dbSettings == null) {
                return ResponseEntity.badRequest().body("Settings are not exists");
            }

            if (!settings.getTokenPassword().equals(dbSettings.getTokenPassword())) {
                return ResponseEntity.badRequest().body("Token passwords do not match");
            }

            dbSettings.setHost(settings.getHost());
            dbSettings.setPassword(settings.getPassword());
            dbSettings.setUserName(settings.getUserName());

            ftpSettingsRepository.save(dbSettings);

            // Implementation logic to create data
            return ResponseEntity.ok("Data updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
