package ru.job4j.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Person;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * For success testing please up server
 */
public class RestTemplateBasicTest {
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();


    private Person getPerson() {
        Person person = new Person();
        person.setLogin("Sekator");
        person.setPassword("secret");
        return person;
    }

    private static String BASE_URL;

    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    @Before
    public void beforeTest() {
        restTemplate = new RestTemplate();
    }

    // GET
    @Test
    public void givenTestRestTemplate_whenSendGetForEntity_thenStatusOk() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Person> response = testRestTemplate.getForEntity(API_ID, Person.class, 1);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    /**
     * get
     */
    @Test
    public void whenSimpleGet() {
        RestTemplate restTemplate = new RestTemplate();
        Person object = restTemplate
                .getForObject(API_ID, Person.class, 1);
        assertThat(object.getPassword(), notNullValue());
        assertThat(object.getId(), notNullValue());

    }

    /**
     * headForHeaders
     */
    @Test
    public void headForHeadersTest() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = restTemplate.headForHeaders(API);
        assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    /**
     * PostForObject
     */
    @Test
    public void whenPostReturnObject() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Person> request = new HttpEntity<>(getPerson());
        Person person1 = restTemplate.postForObject(API, request, Person.class);
        assertThat(person1, notNullValue());
        assertThat(person1.getPassword(), is("secret"));
    }

    /**
     * !!!
     * тут видим как можно двигать JSON
     * т.е. это строка которая описывает POJO  *************
     * видимо поэтому так сказать унифицировали передачу данных
     * и как кой то умник назвал это REST
     */
    @Test
    public void givenDataIsJson_whenDataIsPostedByPostForEntity_thenResponseBodyIsNotNull() throws JSONException, JsonProcessingException {
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("id", 1);
        personJsonObject.put("login", "John");
        personJsonObject.put("password", "strong");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), headers);

        String personResultAsJsonStr = restTemplate.postForObject(API, request, String.class);

        JsonNode root = objectMapper.readTree(personResultAsJsonStr);

        Person person = objectMapper.treeToValue(root, Person.class);

        assertNotNull(personResultAsJsonStr);
        assertNotNull(root);
        assertNotNull(root.path("password").asText());

        assertThat(person.getPassword(), is("strong"));

        assertTrue(request.getBody().contains("strong"));
    }
}
