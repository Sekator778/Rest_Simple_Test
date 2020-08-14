package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonRepository persons;

    @Test
    public void whenFindAllPersonsAndReturnJsonWithPersons() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person();
        Person person1 = new Person();
        ArrayList<Person> list = new ArrayList<>(Lists.newArrayList(person, person1));
        String json = mapper.writeValueAsString(list);
        given(this.persons.findAll())
                .willReturn(list);
        this.mvc.perform(get("/person/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }

    @Test
    public void whenFindOnePersonThenStatusOkAndJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person();
        person.setId(1);
        String json = mapper.writeValueAsString(person);
        given(this.persons.findById(1))
                .willReturn(Optional.of(person));
        this.mvc.perform(get("/person/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }

    @Test
    public void whenCreatePersonThenStatusCreatedAndJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person();
        String requestJson = mapper.writeValueAsString(person);
        person.setId(1);
        String json = mapper.writeValueAsString(person);
        given(this.persons.save(person)).willReturn(person);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson).accept(MediaType.APPLICATION_JSON);
        this.mvc.perform(builder)
                .andExpect(status().isCreated())
                .andExpect(content().string(json));
    }

    @Test
    public void whenUpdatePersonAndStatusIsOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person();
        person.setId(1);
        String requestJson = mapper.writeValueAsString(person);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson).accept(MediaType.APPLICATION_JSON);
        this.mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void whenDeletePersonReturnStatusIsOk() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/person/{id}", 1);
        this.mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}