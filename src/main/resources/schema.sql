create schema  if not exists main;

create sequence if not exists main.books_sequence
    minvalue 1
    maxvalue 999999999
    increment by 1
    start with 10
    nocache
nocycle;

create sequence if not exists main.authors_sequence
    minvalue 1
    maxvalue 999999999
    increment by 1
    start with 3
    nocache
nocycle;

create sequence if not exists main.genres_sequence
    minvalue 1
    maxvalue 999999999
    increment by 1
    start with 3
    nocache
nocycle;

create table if not exists main.authors (
    id int primary key,
    name varchar not null unique
);

create table if not exists main.genres (
    id int primary key,
    title varchar  not null unique
);

create table if not exists main.books (
    id int primary key,
    author int references main.authors on delete cascade,
    title varchar,
    genre int references main.genres  on delete cascade
);
