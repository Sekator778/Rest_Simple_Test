DROP TABLE IF EXISTS person;;
DROP TABLE IF EXISTS employee;;
create table employee
(
    snn             serial not null,
    date_employment timestamp,
    lastname        varchar(255),
    login           varchar(255),
    name            varchar(255),
    password        varchar(255),
    primary key (snn)
);
create table if not exists person
(
    id       serial primary key not null,
    login    varchar(2000),
    password varchar(2000)
);
insert into person (login, password)
values ('rest', '123');
insert into person (login, password)
values ('spring', '123');
insert into person (login, password)
values ('webFlux', '123');
