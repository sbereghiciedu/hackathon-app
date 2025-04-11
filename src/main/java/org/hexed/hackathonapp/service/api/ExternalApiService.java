package org.hexed.hackathonapp.service.api;

import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.exceptions.CallLimitException;
import org.hexed.hackathonapp.model.api.exceptions.NoMoreCallsException;
import org.hexed.hackathonapp.model.api.medical.InterventionCenterModel;
import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.control.ControlResponseModel;
import org.hexed.hackathonapp.model.api.location.LocationModel;
import org.hexed.hackathonapp.model.api.control.ResetParamsModel;
import org.hexed.hackathonapp.model.api.medical.DispatchModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ExternalApiService {

    private final WebClient webClient;

    public ExternalApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:5000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public RequestModel getCallsNext() {
        return webClient.get()
                .uri("/calls/next")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError) // Handle 4xx errors
                .bodyToMono(RequestModel.class)
                .block(); // Block to fetch the result synchronously
    }

    private Mono<Throwable> handle4xxError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    HttpStatus status = (HttpStatus) response.statusCode();
                    switch (status) {
                        case BAD_REQUEST:
                            return Mono.error(new CallLimitException("400 Bad Request: " + errorBody));
                        case NOT_FOUND:
                            return Mono.error(new NoMoreCallsException("404 Not Found: " + errorBody));
                        default:
                            return Mono.error(new RuntimeException("HTTP " + status + ": " + errorBody));
                    }
                });
    }

    public List<RequestModel> getCallsQueue() {
        ParameterizedTypeReference<List<RequestModel>> typeReference = new ParameterizedTypeReference<>() {
        };
        return webClient.get()
                .uri("/calls/queue")
                .retrieve()
                .bodyToMono(typeReference).block();
    }

    // LOCATIONS
    public List<LocationModel> getLocations() {
        ParameterizedTypeReference<List<LocationModel>> typeReference = new ParameterizedTypeReference<>() {
        };
        return webClient.get()
                .uri("/locations")
                .retrieve()
                .bodyToMono(typeReference).block();
    }

    // MEDICAL
    public List<InterventionCenterModel> getRequestSearch(RequestType type) {
        ParameterizedTypeReference<List<InterventionCenterModel>> typeReference = new ParameterizedTypeReference<>() {
        };
        return webClient.get()
                .uri("/"+ type.getKey() + "/search")
                .retrieve()
                .bodyToMono(typeReference).block();
    }

    public Integer getMedicalSearchByCity(RequestType type, String county, String city) {
        String uri = String.format("/" + type.getKey() + "/searchbycity?county=%s&city=%s", county, city);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Integer.class).block();
    }

    public String postMedicalDispatch(DispatchModel dispatchModel) {
        return webClient.post()
                .uri("/medical/dispatch")
                .bodyValue(dispatchModel)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // CONTROL
    public ControlResponseModel postControlReset(ResetParamsModel resetParamsModel) {
        return webClient.post()
                .uri("/control/reset?seed=%s&targetDispatches=%d&maxActiveCalls=%d".formatted(resetParamsModel.getSeed(), resetParamsModel.getTargetDispatches(), resetParamsModel.getMaxActiveCalls()))
                .bodyValue(Map.of())
                .retrieve()
                .bodyToMono(ControlResponseModel.class)
                .block();
    }

    public ControlResponseModel postControlStop() {
        return webClient.post()
                .uri("/control/stop")
                .bodyValue(Map.of())
                .retrieve()
                .bodyToMono(ControlResponseModel.class)
                .block();
    }

    public ControlResponseModel getControlStatus() {
        return webClient.get()
                .uri("/control/status")
                .retrieve()
                .bodyToMono(ControlResponseModel.class).block();
    }
}