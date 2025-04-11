package org.hexed.hackathonapp.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

public class WebFluxApiClient {

    private final WebClient webClient;

    public WebFluxApiClient(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public <T> Mono<T> get(String endpoint, Class<T> responseType) {
        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(responseType);
    }

//    public <T> Mono<List<T>> getList(String endpoint, Class<T> elementType) {
//        ParameterizedTypeReference<List<T>> typeReference = new ParameterizedTypeReference<>() {};
//        return webClient.get()
//                .uri(endpoint)
//                .retrieve()
//                .bodyToMono(typeReference);
//    }

    public <T> Mono<T> post(String endpoint, Object requestBody, Class<T> responseType) {
        return webClient.post()
                .uri(endpoint)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType);
    }
}