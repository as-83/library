create schema  if not exists main;

create sequence if not exists main.books_sequence
    minvalue 1
    maxvalue 999999999
    increment by 1
    start with 10
    nocache
nocycle;

create table if not exists main.authors (
    id int primary key,
    authorName varchar
);

create table if not exists main.genres (
    id int primary key,
    genreTitle varchar
);

create table if not exists main.books (
    id int primary key,
    author int references main.authors,
    title varchar,
    genre int references main.genres
);
