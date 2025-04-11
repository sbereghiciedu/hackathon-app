package org.hexed.hackathonapp.service.api;

import org.hexed.hackathonapp.model.api.medical.LocationWithQuantityModel;
import org.hexed.hackathonapp.model.api.calls.CallsNextResponseModel;
import org.hexed.hackathonapp.model.api.control.ControlResponseModel;
import org.hexed.hackathonapp.model.api.location.LocationModel;
import org.hexed.hackathonapp.model.api.control.ResetParamsModel;
import org.hexed.hackathonapp.model.api.medical.DispatchModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    public CallsNextResponseModel getCallsNext() {
        return webClient.get()
                .uri("/calls/next")
                .retrieve()
                .bodyToMono(CallsNextResponseModel.class).block();
    }

    public List<CallsNextResponseModel> getCallsQueue() {
        ParameterizedTypeReference<List<CallsNextResponseModel>> typeReference = new ParameterizedTypeReference<>() {
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
    public List<LocationWithQuantityModel> getMedicalSearch() {
        ParameterizedTypeReference<List<LocationWithQuantityModel>> typeReference = new ParameterizedTypeReference<>() {
        };
        return webClient.get()
                .uri("/medical/search")
                .retrieve()
                .bodyToMono(typeReference).block();
    }

    public Integer getMedicalSearchByCity(String county, String city) {
        String uri = String.format("/medical/searchbycity?county=%s&city=%s", county, city);
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
                .uri("/control/reset")
                .bodyValue(resetParamsModel)
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