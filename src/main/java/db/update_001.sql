create table if not exists person
(
    id       serial primary key not null,
    login    varchar(2000),
    password varchar(2000)
);
TRUNCATE table person restart identity;

insert into person (login, password)
values ('rest', '123');
insert into person (login, password)
values ('spring', '123');
insert into person (login, password)
values ('webFlux', '123');