package com.secureftpdbconnection.SecureDbConnection.Models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Settings")
public class FtpSettings {

    private String host;
    private String userName;
    private String password;
    private String token;
    private String tokenPassword;
    private String _id;

    public FtpSettings(String host, String userName, String password, String token, String tokenPassword, String _id) {
        super();
        this.host = host;
        this.userName = userName;
        this.password = password;
        this.token = token;
        this.tokenPassword = tokenPassword;
        this._id = _id;
    }

    // Getter for host
    public String getHost() {
        return host;
    }

    // Setter for host
    public void setHost(String host) {
        this.host = host;
    }

    // Getter for userName
    public String getUserName() {
        return userName;
    }

    // Setter for userName
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for token
    public String getToken() {
        return token;
    }

    // Setter for token
    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public String getId() {
        return _id;
    }

    public Boolean isValid() {
        return this.host != null && this.userName != null && this.password != null && this.token != null && this.tokenPassword != null;
    }
}
