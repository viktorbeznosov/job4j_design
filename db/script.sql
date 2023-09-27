CREATE DATABASE test;

create table cars (
    id serial primary key,
    title varchar,
    description text default null,
    price numeric (9,2),
    created_at TIMESTAMP default now()
);

insert into cars (title, price) values ('BMV', 3200000);
update cars set price = 3300000 where title like 'BMW';
delete from cars where id = 1;