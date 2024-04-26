package com.secureftpdbconnection.SecureDbConnection.Repositories;

import com.secureftpdbconnection.SecureDbConnection.Models.FtpSettings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface FtpSettingsRepository extends MongoRepository<FtpSettings, String> {

    @Query("{token:'?0'}")
    FtpSettings findItemByToken(String token);

    public long count();
}
