package com.cozi.soft.listing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
public class RequestParameters {
    private String uri;
    private Object body;
    private String role;
    private String userIdp;
    private boolean authenticated;
    @Builder.Default
    private HttpHeaders headers = new HttpHeaders();
    @Builder.Default
    private MediaType bodyMediaType = MediaType.APPLICATION_JSON;
    @Builder.Default
    private MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

    public static RequestParameters withoutAuthentication(String uri) {
        return RequestParameters.builder()
                .uri(uri)
                .authenticated(false)
                .build();
    }

    public static RequestParameters withAuthentication(String uri) {
        return RequestParameters.builder()
                .uri(uri)
                .authenticated(true)
                .build();
    }
}
