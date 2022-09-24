package com.skeleton.skeletons.feature.shared.config;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public final class CucumberHttpClient {

    private static final String SERVER_URL = "http://localhost";
    private final RestTemplate restTemplate = new RestTemplate();
    @LocalServerPort
    private int port;

    private static String addParamsToUri(String path, LinkedMultiValueMap<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(path).queryParams(params);

        return builder.build().toUriString();
    }

    public ResponseEntity<?> doRequest(String method, String path, String body,
                                       Optional<LinkedMultiValueMap<String, String>> queryParams, String basicAuthToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (basicAuthToken != null) {
            headers.setBasicAuth(basicAuthToken);
        }

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<?> responseEntity;
        try {
            responseEntity = restTemplate.exchange(this.buildUrl(path, queryParams),
                    Objects.requireNonNull(HttpMethod.resolve(method)), request, Map.class);
        } catch (HttpStatusCodeException e) {
            responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

        return responseEntity;
    }

    private String buildUrl(String path, Optional<LinkedMultiValueMap<String, String>> queryParams) {
        String uri = SERVER_URL + ":" + this.port + path;

        return queryParams.isEmpty() ? uri : addParamsToUri(uri, queryParams.get());
    }
}

