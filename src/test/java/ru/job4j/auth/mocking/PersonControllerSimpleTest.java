//package ru.job4j.auth.controller;
//
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import ru.job4j.auth.domain.Person;
//import ru.job4j.auth.repository.PersonRepository;
//
//import java.util.Optional;
//
///**
// *
// */
//@RunWith(MockitoJUnitRunner.class)
//public class PersonControllerSimpleTest {
//    private static final String API = "http://localhost:8080/person/";
//    private static final String API_ID = "http://localhost:8080/person/{id}";
//
//    @Mock
//    private RestTemplate restTemplate;
//    @InjectMocks
//    private PersonRepository repository;
//
//    @Test
//    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() {
//        Person emp = new Person();
//        emp.setId(1);
//        Mockito.when(restTemplate.getForEntity(
//                API_ID, Person.class, 1))
//          .thenReturn(new ResponseEntity(emp, HttpStatus.OK));
//
//        Optional<Person> person = repository.findById(1);
//        Assert.assertEquals(emp, person);
//    }
//}
