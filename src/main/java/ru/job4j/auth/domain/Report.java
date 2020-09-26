package ru.job4j.auth.domain;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 *
 */
@Data
@Slf4j
public class Report {
    private int id;
    private String name;
    private Timestamp created;
    private Person person;

    public static Report of(int id, String name, Person person) {
        Report report = new Report();
        report.id = id;
        report.name = name;
        report.person = person;
        report.created = new Timestamp(System.currentTimeMillis());
        log.info("Report created: " + report);
        return report;
    }
}
