package com.secureftpdbconnection.SecureDbConnection;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
@EnableEncryptableProperties
public class SecureDbConnectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureDbConnectionApplication.class, args);
    }

}