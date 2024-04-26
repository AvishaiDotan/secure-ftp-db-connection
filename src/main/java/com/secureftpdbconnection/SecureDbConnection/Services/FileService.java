package com.secureftpdbconnection.SecureDbConnection.Services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class FileService {

    /**
     * Converts a base64 encoded string into a file.
     *
     * @param base64String The base64 encoded string to decode.
     * @param filePath     The file path where the decoded file will be saved.
     * @return File object pointing to the newly created file.
     */
    public File convertToFile(String base64String, String filePath) {
        // Decoding the base64 string into byte array
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // Creating a file with the specified path
        File file = new File(filePath);

        // Writing the decoded bytes to the file
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the case where the file could not be written
            return null;
        }

        // Return the file which can now be uploaded to FTP
        return file;
    }
}
