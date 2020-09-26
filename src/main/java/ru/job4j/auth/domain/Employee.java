package ru.job4j.auth.domain;

import lombok.Data;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 *
 */
@Data
@Entity
public class Employee {
    private String name;
    private String lastname;
    @Id
    private String snn;
    private LocalDateTime dateEmployment;
    @Transient
    private Person person;
    private String login;
    private String password;


    public static Employee of(String snn, String name, String lastname, Person person) {
        Employee employee = new Employee();
        employee.snn = snn;
        employee.name = name;
        employee.lastname = lastname;
//        employee.person = person;
        employee.setLogin(person.getLogin());
        employee.setPassword(person.getPassword());
        employee.setDateEmployment(LocalDateTime.now());
        return employee;
    }

    @PrePersist
    void onCreate() {
        setDateEmployment(LocalDateTime.now());
    }

}
