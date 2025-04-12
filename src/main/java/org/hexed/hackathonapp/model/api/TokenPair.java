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
}
