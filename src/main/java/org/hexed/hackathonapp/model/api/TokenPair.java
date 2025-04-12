package org.hexed.hackathonapp.model.api;

public class TokenPair {
    public String token;
    public String refreshToken;

    public TokenPair() {
    }

    public TokenPair(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
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
