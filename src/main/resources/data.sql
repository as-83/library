insert into main.authors(id, name)
values (1, 'А. Дюма'),
       (2, 'Д.И. Крылов')
;

insert into main.genres(id, title)
values (1, 'Роман'),
       (2, 'Басня')
;

insert into main.books(id, author, title, genre)
values (1, 1, 'Три мушкетёра', 1),
       (2, 1, 'Двадцать лет спустя', 1),
       (3, 2, 'Мартышка и очки', 2)
;
