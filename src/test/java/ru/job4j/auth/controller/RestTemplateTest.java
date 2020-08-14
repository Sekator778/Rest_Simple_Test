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

    private static final String HTTP_LOCALHOST_8080_PERSON = "http://localhost:8080/person/";
    private static final String HTTP_LOCALHOST_8080_PERSON_ID = "http://localhost:8080/person/{id}";

    @Test
    public void whenGetAllPersonsThenList() {
        RestTemplate template = new RestTemplate();
        Person person = new Person();
        person.setLogin("foo");
        person.setPassword("secret");
        Person entity = template.postForObject(HTTP_LOCALHOST_8080_PERSON, person, Person.class);
        List<Person> persons = template.exchange(
                HTTP_LOCALHOST_8080_PERSON,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                }
        ).getBody();
        System.out.println("==============");
        System.out.println(entity);
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
        Person entity = restTemplate.postForObject(HTTP_LOCALHOST_8080_PERSON, person, Person.class);
        assertNotNull(entity);
        assertTrue(entity.getId() > 0);
    }

    @Test
    public void whenCreatePersonAndGetPersonAtIdEqual() {
        RestTemplate rest = new RestTemplate();
        Person person = new Person();
        person.setLogin("foo");
        person.setPassword("secret");
        Person entity = rest.postForObject(HTTP_LOCALHOST_8080_PERSON, person, Person.class);
        Person remote = rest.getForObject(HTTP_LOCALHOST_8080_PERSON_ID, Person.class, entity.getId());
        assertNotNull(remote);
        assertEquals(remote.getLogin(), entity.getLogin());
    }

    @Test
    public void whenUpdatePerson() {
        RestTemplate rest = new RestTemplate();
        Person person = new Person();
        person.setLogin("foo");
        person.setPassword("secret");
        Person entity = rest.postForObject(HTTP_LOCALHOST_8080_PERSON, person, Person.class);
        String newName = "updateName";
        entity.setLogin(newName);
        rest.put(HTTP_LOCALHOST_8080_PERSON, entity);
        Person remote = rest.getForObject(HTTP_LOCALHOST_8080_PERSON_ID, Person.class, entity.getId());
        assertNotNull(remote);
        assertEquals(remote.getLogin(), newName);
    }

    @Test
    public void whenDeletePersonStatusNOT_FOUND() {
        RestTemplate rest = new RestTemplate();
        Person person = new Person();
        person.setLogin("foo");
        person.setPassword("secret");
        Person entity = rest.postForObject(HTTP_LOCALHOST_8080_PERSON, person, Person.class);
        rest.delete(HTTP_LOCALHOST_8080_PERSON_ID, entity.getId());
        HttpStatus statusCode = HttpStatus.FOUND;
        try {
            rest.getForObject(HTTP_LOCALHOST_8080_PERSON_ID, Person.class, entity.getId());
        } catch (final HttpClientErrorException e) {
            statusCode = e.getStatusCode();
        }
        assertSame(statusCode, HttpStatus.NOT_FOUND);
    }
}
