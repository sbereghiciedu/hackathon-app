package org.hexed.hackathonapp.service;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DataProcessingService {

    private final WebClient dsServiceWebClient;

    public DataProcessingService() {
        this.dsServiceWebClient = WebClient.create("http://localhost:8081");
    }

    public String ping() {
        return dsServiceWebClient
                .get()
                .uri("/ping")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}