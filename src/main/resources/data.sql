insert into main.authors(id, name)
values (1, 'A. Dumas'),
       (2, 'D.I. Krilov')
;

insert into main.genres(id, title)
values (1, 'Roman'),
       (2, 'Fable')
;

insert into main.books(id, author, title, genre)
values (1, 1, 'Three Musketeers', 1),
       (2, 1, 'Twenty years later', 1),
       (3, 2, 'The Monkey and the Glasses', 2)
;
