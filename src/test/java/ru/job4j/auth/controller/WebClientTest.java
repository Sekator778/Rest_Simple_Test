package ru.job4j.auth.controller;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
/**
 *
 */

public class WebClientTest {
    private static final String API = "http://localhost:8080/resource/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    @Ignore
    public void start() {
        WebClient client = WebClient
                .builder()
                .baseUrl(API)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", API))
                .build();
    }
}
