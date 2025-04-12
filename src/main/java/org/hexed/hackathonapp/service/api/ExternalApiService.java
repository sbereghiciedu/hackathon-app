package org.hexed.hackathonapp.service.api;

import org.hexed.hackathonapp.model.api.LoginModel;
import org.hexed.hackathonapp.model.api.TokenPair;
import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.control.ControlResponseModel;
import org.hexed.hackathonapp.model.api.control.ResetParamsModel;
import org.hexed.hackathonapp.model.api.exceptions.CallLimitException;
import org.hexed.hackathonapp.model.api.exceptions.NoMoreCallsException;
import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;
import org.hexed.hackathonapp.model.api.location.LocationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ExternalApiService {

    private Logger logger = LoggerFactory.getLogger(ExternalApiService.class);
    private final WebClient webClient;

    private TokenPair tokenPair = null;
    private int serverVersion = 0;

    public ExternalApiService() {
        String url = System.getProperties().getProperty("external.api.url");

        this.webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public RequestModel getCallsNext() {
        return retry(() -> webClient.get()
                        .uri("/calls/next")
                        .headers(headers -> {
                            if (tokenPair != null) {
                                headers.setBearerAuth(tokenPair.token);
                            }
                        })
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError) // Handle 4xx errors
                        .bodyToMono(RequestModel.class)
                        .block()); // Block to fetch the result synchronously
    }

    private Mono<Throwable> handle4xxError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    HttpStatus status = (HttpStatus) response.statusCode();
                    return switch (status) {
                        case BAD_REQUEST -> Mono.error(new CallLimitException("400 Bad Request: " + errorBody));
                        case NOT_FOUND -> Mono.error(new NoMoreCallsException("404 Not Found: " + errorBody));
                        default -> Mono.error(new RuntimeException("HTTP " + status + ": " + errorBody));
                    };
                });
    }

    public List<RequestModel> getCallsQueue() {
        ParameterizedTypeReference<List<RequestModel>> typeReference = new ParameterizedTypeReference<>() {
        };
        return retry(() -> webClient.get()
                        .uri("/calls/queue")
                        .headers(headers -> {
                            if (tokenPair != null) {
                                headers.setBearerAuth(tokenPair.token);
                            }
                        })
                        .retrieve()
                        .bodyToMono(typeReference).block());
    }

    // LOCATIONS
    public List<LocationModel> getLocations() {
        ParameterizedTypeReference<List<LocationModel>> typeReference = new ParameterizedTypeReference<>() {
        };
        return retry(() -> webClient.get()
                        .uri("/locations")
                        .headers(headers -> {
                            if (tokenPair != null) {
                                headers.setBearerAuth(tokenPair.token);
                            }
                        })
                        .retrieve()
                        .bodyToMono(typeReference).block());
    }

    public List<InterventionCenterModel> getInterventionCenters(RequestType type) {
        ParameterizedTypeReference<List<InterventionCenterModel>> typeReference = new ParameterizedTypeReference<>() {
        };
        return retry(() -> webClient.get()
                        .uri("/" + type.getKey() + "/search")
                        .headers(headers -> {
                            if (tokenPair != null) {
                                headers.setBearerAuth(tokenPair.token);
                            }
                        })
                        .retrieve()
                        .bodyToMono(typeReference).block());
    }

    public Integer getInterventionCentersByCity(RequestType type, String county, String city) {
        String uri = String.format("/" + type.getKey() + "/searchbycity?county=%s&city=%s", county, city);
        return retry(() -> webClient.get()
                        .uri(uri)
                        .headers(headers -> {
                            if (tokenPair != null) {
                                headers.setBearerAuth(tokenPair.token);
                            }
                        })
                        .retrieve()
                        .bodyToMono(Integer.class).block());
    }

    public String postDispatch(RequestType type, DispatchModel dispatchModel) {
        return retry(() -> webClient.post()
                        .uri("/" + type.getKey() + "/dispatch")
                        .bodyValue(dispatchModel)
                        .headers(headers -> {
                            if (tokenPair != null) {
                                headers.setBearerAuth(tokenPair.token);
                            }
                        })
                        .retrieve()
                        .bodyToMono(String.class)
                        .block());
    }

    // CONTROL
    public ControlResponseModel postControlReset(ResetParamsModel resetParamsModel) {
        return retry(() -> webClient.post()
                        .uri("/control/reset?seed=%s&targetDispatches=%d&maxActiveCalls=%d".formatted(resetParamsModel.getSeed(), resetParamsModel.getTargetDispatches(), resetParamsModel.getMaxActiveCalls()))
                        .bodyValue(Map.of())
                        .retrieve()
                        .bodyToMono(ControlResponseModel.class)
                        .block());
    }

    public ControlResponseModel postControlStop() {
        return retry(() -> webClient.post()
                        .uri("/control/stop")
                        .bodyValue(Map.of())
                        .retrieve()
                        .bodyToMono(ControlResponseModel.class)
                        .block());
    }

    public ControlResponseModel getControlStatus() {
        return retry(() -> webClient.get()
                        .uri("/control/status")
                        .retrieve()
                        .bodyToMono(ControlResponseModel.class).block());
    }

    public TokenPair postLogin(LoginModel loginModel) {
        return webClient.post()
                        .uri("/auth/login")
                        .bodyValue(loginModel)
                        .retrieve()
                        .bodyToMono(TokenPair.class)
                        .block();
    }

    public TokenPair postRefreshToken(TokenPair token) {
        return webClient.post()
                        .uri("auth/refreshtoken")
                        .header("refresh_token", token.refreshToken)
                        .retrieve()
                        .bodyToMono(TokenPair.class)
                        .block();
    }

    public void resetServer(ResetParamsModel resetParamsModel) {
        resetServer(resetParamsModel, 0);
    }

    public void resetServer(ResetParamsModel resetParamsModel, int serverVersion) {
        LoginModel loginModel = new LoginModel("distancify", "hackathon");

        logger.info("Resetting server");
        ControlResponseModel controlResponseModel = postControlReset(resetParamsModel);
        logger.info(controlResponseModel.toString());
        if (serverVersion == 5) {
            tokenPair = postLogin(loginModel);
        }
        if (serverVersion == 0) {
            tokenPair = null;
            try {
                tokenPair = postLogin(loginModel);
                serverVersion = 5;
            } catch (Exception e) {
                logger.info("No authorization, server version less than 5");
            }
        }
        if (serverVersion == 0) {
            int retryCount = 3;
            while (retryCount-- > 0) {
                try {
                    getLocations();
                } catch (Exception e) {
                    logger.info("Unexpected error, server version must be 4");
                    serverVersion = 4;
                }
            }
        }
        if (serverVersion == 0) {
            try {
                getInterventionCenters(RequestType.UTILITY);
                serverVersion = 3;
            } catch (Exception e) {
                logger.info("no utilities, server version less than 3");
            }
        }

        if (serverVersion == 0) {
            try {
                getInterventionCenters(RequestType.POLICE);
                serverVersion = 2;
            } catch (Exception e) {
                logger.info("no police, server version less than 2");
            }
        }

        this.serverVersion = serverVersion;
        logger.info("Server version is " + serverVersion);
    }

    public int getServerVersion() {
        return serverVersion;
    }

    public <T> T retry(Supplier<T> supplier) {
        int attempts = 0;
        while (true) {
            try {
                T result = supplier.get();
                if (result instanceof Integer && (Integer) result < 0) {
                    logger.info("Invalid result: negative. Retrying");
                    throw new RuntimeException("Retry triggered: result < 0");
                }

                return result;
            } catch (WebClientResponseException.Unauthorized e) {
                logger.info("Unauthorized, refresh token");
                tokenPair = postRefreshToken(tokenPair);
            } catch (Exception e) {
                logger.info("Server error, retrying");
                attempts++;
                if (attempts > getMaxRetries()) {
                    throw e;
                }
            }
        }
    }

    int getMaxRetries() {
        return serverVersion == 4 ? 100 : 1;
    }
}

