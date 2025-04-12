package org.hexed.hackathonapp.service.api;

public class SessionStore {

    private static SessionStore instance;

    private String token;
    private String refreshToken;

    private SessionStore() {}

    public static synchronized SessionStore getInstance() {
        if (instance == null) {
            instance = new SessionStore();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
