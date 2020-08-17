package ru.job4j.auth.controller;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Person;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.Matchers.is;


/**
 *
 */

public class ResponseEntityTest {
    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";
    private RestTemplate restTemplate;

    @Test
    public void person() {
        restTemplate = new RestTemplate();
        ResponseEntity<Person> responseEntity = restTemplate.getForEntity(API_ID, Person.class, 1);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        Person body = responseEntity.getBody();
        HttpHeaders headers = responseEntity.getHeaders();
        MediaType mediaType = MediaType.APPLICATION_JSON;
        System.out.println("=== " + headers.toString());
        assertNotNull(headers);
        assertThat(headers.getContentType(), is(mediaType));
        assertNotNull(body.getPassword());

    }
}
