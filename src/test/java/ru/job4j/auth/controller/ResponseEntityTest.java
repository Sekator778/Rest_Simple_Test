package ru.job4j.auth.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.job4j.auth.domain.Person;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
    private final int IdPerson = 1;
    private RestTemplate restTemplate;

    @Before
    public void init() {
        restTemplate = new RestTemplate();
    }

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

    /*
     * Alternate implementations...
     * The next three methods are alternative implementations of
     * getPersonById()***. If you'd like to try
     * any of them out, comment out the previous method and uncomment
     * the variant you want to use.
     */

    /*
     * Specify parameters with a map
     */
    @Test
    public void getPersonByIdWithMap() {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", String.valueOf(IdPerson));
        Person result = restTemplate.getForObject(API_ID, Person.class, urlVariables);
        assertThat(result.getId(), is(1));
    }

    /*
     * Request with URI instead of String
     */
    @Test
    public void getPersonByIdWithURI() {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", String.valueOf(IdPerson));
        URI url = UriComponentsBuilder
                .fromHttpUrl(API_ID)
                .build(urlVariables);
        Person result = restTemplate.getForObject(url, Person.class);
        assertThat(result.getId(), is(1));
    }

    /*
     * Use getForEntity() instead of getForObject()
     */
    @Test
    public void getPersonById() {
        ResponseEntity<Person> responseEntity = restTemplate.getForEntity(API_ID, Person.class, 1);
        System.out.println("Fetched time: " + responseEntity.getHeaders().getDate());
        Person result = responseEntity.getBody();
        assertThat(result.getId(), is(1));
    }
}
