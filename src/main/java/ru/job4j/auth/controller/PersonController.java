package ru.job4j.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository persons;
    private RestTemplate rest;

    public PersonController(PersonRepository persons) {
        rest = new RestTemplate();
        this.persons = persons;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return StreamSupport.stream(
                this.persons.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<Person>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<Person>(
                this.persons.save(person),
                HttpStatus.CREATED
        );
    }

    /**
     * update
     */
    @PatchMapping(path = "/{Id}", consumes = "application/json")
    public ResponseEntity<Void> patchOrder(@PathVariable("Id") int orderId,
                                           @RequestBody Person patch) {
        Person person = persons.findById(orderId).get();
        if (patch.getLogin() != null) {
            person.setLogin(patch.getLogin());
        }
        if (patch.getPassword() != null) {
            person.setPassword(patch.getPassword());
        }
        this.persons.save(person);
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/")
//    public ResponseEntity<Void> update(@RequestBody Person person) {
//        this.persons.save(person);
//        return ResponseEntity.ok().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        this.persons.delete(person);
        return ResponseEntity.ok().build();
    }
    /*
     * Use RestTemplate method
     */

    /**
     * Specify parameter as varargs argument
     */
    @GetMapping("/rest/{id}")
    public Person getIngredientById(@PathVariable String id) {
        rest = new RestTemplate();

        return rest.getForObject("http://localhost:8080/person/{id}",
                Person.class, id);
    }

    //
    // PUT examples
    //
    @PutMapping(path = "/rest/{Id}")
    public void updateIngredient(Person person) {
        rest.put("http://localhost:8080/person/{id}",
                person, person.getId());
    }

    //
    // POST examples
    //
    public Person createPerson(Person person) {
        return rest.postForObject("http://localhost:8080/persons",
                person, Person.class);
    }

    //
    // another method
    //
    @GetMapping("login/{id}")
    public String findByIdOnlyLoginPerson(@PathVariable int id) {
        var person = this.persons.findById(id);
        return person.map(Person::getLogin).orElse("not found person");
    }
}