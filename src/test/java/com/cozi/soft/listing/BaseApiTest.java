package com.cozi.soft.listing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public abstract class BaseApiTest {

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public DbResetService dbResetService;

    protected ResultActions postReq(RequestParameters requestParameters, HttpStatus expectedStatus) {
        return makeRequest(MockMvcRequestBuilders.post(requestParameters.uri()), requestParameters, expectedStatus)
                .get();
    }

    protected <T> T postReq(RequestParameters requestParameters, HttpStatus expectedStatus, TypeReference<T> typeReference) {
        return makeRequest(MockMvcRequestBuilders.post(requestParameters.uri()), requestParameters, expectedStatus)
                .map(response -> parseResponse(response, typeReference))
                .get();
    }

    @SneakyThrows
    protected ResultActions patchReq(RequestParameters requestParameters, HttpStatus expectedStatus) {
        return makeRequest(MockMvcRequestBuilders.patch(requestParameters.uri()), requestParameters, expectedStatus)
                .get();
    }

    @SneakyThrows
    protected <T> T patchReq(RequestParameters requestParameters, HttpStatus expectedStatus, TypeReference<T> typeReference) {
        return makeRequest(MockMvcRequestBuilders.patch(requestParameters.uri()), requestParameters, expectedStatus)
                .map(response -> parseResponse(response, typeReference))
                .get();
    }

    @SneakyThrows
    protected ResultActions putReq(RequestParameters requestParameters, HttpStatus expectedStatus) {
        return makeRequest(MockMvcRequestBuilders.put(requestParameters.uri()), requestParameters, expectedStatus)
                .get();
    }

    @SneakyThrows
    protected <T> T putReq(RequestParameters requestParameters, HttpStatus expectedStatus, TypeReference<T> typeReference) {
        return makeRequest(MockMvcRequestBuilders.put(requestParameters.uri()), requestParameters, expectedStatus)
                .map(response -> parseResponse(response, typeReference))
                .get();
    }

    @SneakyThrows
    protected ResultActions getReq(RequestParameters requestParameters, HttpStatus expectedStatus) {
        return makeRequest(MockMvcRequestBuilders.get(requestParameters.uri()), requestParameters, expectedStatus)
                .get();
    }

    @SneakyThrows
    protected <T> T getReq(RequestParameters requestParameters, HttpStatus expectedStatus, TypeReference<T> typeReference) {
        return makeRequest(MockMvcRequestBuilders.get(requestParameters.uri()), requestParameters, expectedStatus)
                .map(response -> parseResponse(response, typeReference))
                .get();
    }

    @SneakyThrows
    protected ResultActions deleteReq(RequestParameters requestParameters, HttpStatus expectedStatus) {
        return makeRequest(MockMvcRequestBuilders.delete(requestParameters.uri()), requestParameters, expectedStatus)
                .get();
    }

    @SneakyThrows
    protected <T> T deleteReq(RequestParameters requestParameters, HttpStatus expectedStatus, TypeReference<T> typeReference) {
        return makeRequest(MockMvcRequestBuilders.delete(requestParameters.uri()), requestParameters, expectedStatus)
                .map(response -> parseResponse(response, typeReference))
                .get();
    }

    @SneakyThrows
    protected <T> T parseResponse(ResultActions response, Class<T> clazz) {
        String contentAsString = response.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(contentAsString, clazz);
    }

    @SneakyThrows
    protected <T> T parseResponse(ResultActions response, TypeReference<T> clazz) {
        String contentAsString = response.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(contentAsString, clazz);
    }

    protected Try<ResultActions> makeRequest(MockHttpServletRequestBuilder requestBuilder, RequestParameters requestParameters, HttpStatus expectedStatus) {
        return Try.of(() -> requestBuilder
                .contentType(requestParameters.bodyMediaType())
                .content(objectMapper.writeValueAsString(requestParameters.body()))
                .queryParams(requestParameters.queryParams())
                .headers(requestParameters.headers()))
                .mapTry(mockMvc::perform)
                .andThenTry(result -> result.andExpect(status().is(expectedStatus.value())));
    }

}
