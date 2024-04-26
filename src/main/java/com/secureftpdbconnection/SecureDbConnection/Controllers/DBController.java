package com.secureftpdbconnection.SecureDbConnection.Controllers;

import com.secureftpdbconnection.SecureDbConnection.Models.FtpSettings;
import com.secureftpdbconnection.SecureDbConnection.Models.UploadPayload;
import com.secureftpdbconnection.SecureDbConnection.Services.FTPUploadService;
import com.secureftpdbconnection.SecureDbConnection.Services.FtpSettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1") // Base URI for the controller
public class DBController {

    private final FtpSettingsService ftpSettingsService;
    private final FTPUploadService ftpUploadService;

    public DBController(FtpSettingsService ftpSettingsService, FTPUploadService ftpUploadService) {
        this.ftpSettingsService = ftpSettingsService;
        this.ftpUploadService = ftpUploadService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createData(@RequestBody FtpSettings settings) {
        return ftpSettingsService.postFtpSettings(settings);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateData(@RequestBody FtpSettings settings) {
        return ftpSettingsService.putFtpSettings(settings);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteData(@RequestHeader("Token") String token,
                                             @RequestHeader("TokenPassword") String tokenPassword) {
        if (token == null && tokenPassword == null) {
            return ResponseEntity.badRequest().body("Token and TokenPassword are required");
        }

        return  ftpSettingsService.deleteFtpSettings(token, tokenPassword);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> Upload(@RequestBody UploadPayload uploadPayload) {
        try {
            return ftpUploadService.uploadFile(uploadPayload);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
