insert into main.authors(id, authorName)
values (1, 'A. Dumas'),
       (2, 'Д.И. Крылов')
;

insert into main.genres(id, genreTitle)
values (1, 'Роман'),
       (2, 'Басня')
;

insert into main.books(id, author, title, genre)
values (1, 1, 'The Three Musketeers', 1),
       (2, 1, 'Twenty Years After', 1),
       (3, 1, 'La Dame de Monsoreau', 1)
;
