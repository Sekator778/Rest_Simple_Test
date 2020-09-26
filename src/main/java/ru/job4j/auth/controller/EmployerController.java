package ru.job4j.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employer")
@Slf4j
public class EmployerController {
    private final RestTemplate rest;
    @Value("${api}")
    private String API;
    @Value("${api.id}")
    private String API_ID;

    public EmployerController(RestTemplate rest) {
        this.rest = rest;
    }


    @GetMapping("/")
    public List<Employee> findAll() {
        List<Employee> rsl = new ArrayList<>();
        List<Person> persons = rest.exchange(
                API,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                }
        ).getBody();
        for (Person person : persons) {
            Employee employee = Employee.of("1","Some name", "Some lastname", person);
            rsl.add(employee);
        }
        log.info("View all Employers");
        return rsl;
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person rsl = rest.postForObject(API, person, Person.class);
        log.info("Person created use method create in EmployerControl");
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        rest.put(API, person);
        log.info("Person with id: " + person.getId() + " updated use method update in EmployerControl");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        log.info("Person with id: " + id + " deleted");
        return ResponseEntity.ok().build();
    }
}
