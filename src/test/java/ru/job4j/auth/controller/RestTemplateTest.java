package ru.job4j.auth.controller;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Person;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * For run this test please run class AuthApp
 */
public class RestTemplateTest {
    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    @Test
    public void whenGetAllPersonsThenList() {
        RestTemplate restTemplate = new RestTemplate();
        Person person = new Person();
        person.setLogin("foo");
        person.setPassword("secret");
        Person entity = restTemplate.postForObject(API, person, Person.class);
        List<Person> persons = restTemplate.exchange(
                API,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                }
        ).getBody();
        assertThat(entity.getLogin(), is(person.getLogin()));
        assertThat(entity.getPassword(), is(person.getPassword()));
        assertNotNull(persons);
        assertTrue(persons.size() > 0);
    }

    @Test
    public void whenEntityIdTest() {
        RestTemplate restTemplate = new RestTemplate();
        Person person = new Person();
        person.setLogin("foo");
        person.setPassword("secret");
        Person entity = restTemplate.postForObject(API, person, Person.class);
        assertNotNull(entity);
        assertTrue(entity.getId() > 0);
    }

    @Test
    public void whenCreatePersonAndGetPersonAtIdEqual() {
        RestTemplate rest = new RestTemplate();
        Person person = new Person();
        person.setLogin("foo");
        person.setPassword("secret");
        Person entity = rest.postForObject(API, person, Person.class);
        Person remote = rest.getForObject(API_ID, Person.class, entity.getId());
        assertNotNull(remote);
        assertEquals(remote.getLogin(), entity.getLogin());
    }

    @Test
    public void whenUpdatePerson() {
        RestTemplate rest = new RestTemplate();
        Person person = new Person();
        person.setLogin("foo");
        person.setPassword("secret");
        Person entity = rest.postForObject(API, person, Person.class);
        String newName = "updateName";
        entity.setLogin(newName);
        rest.put(API, entity);
        Person remote = rest.getForObject(API_ID, Person.class, entity.getId());
        assertNotNull(remote);
        assertEquals(remote.getLogin(), newName);
    }

    @Test
    public void whenDeletePersonStatusNOT_FOUND() {
        RestTemplate rest = new RestTemplate();
        Person person = new Person();
        person.setLogin("foo");
        person.setPassword("secret");
        Person entity = rest.postForObject(API, person, Person.class);
        rest.delete(API_ID, entity.getId());
        HttpStatus statusCode = HttpStatus.FOUND;
        try {
            rest.getForObject(API_ID, Person.class, entity.getId());
        } catch (final HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
        assertSame(statusCode, HttpStatus.NOT_FOUND);
    }
}
