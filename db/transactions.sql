create table products (
    id serial primary key,
    name varchar(50),
    count integer default 0,
    price integer
);

insert into products (name, count, price) VALUES ('product_1', 3, 50);
insert into products (name, count, price) VALUES ('product_2', 15, 32);
insert into products (name, count, price) VALUES ('product_3', 8, 115);

set session transaction isolation level read uncommitted;

drop table products;

create table products (
    id serial primary key,
    name varchar(50),
    producer varchar(50),
    count integer default 0,
    price integer
);

insert into products (name, producer, count, price) VALUES ('product_1', 'producer_1', 3, 50);
insert into products (name, producer, count, price) VALUES ('product_2', 'producer_2', 15, 32);
insert into products (name, producer, count, price) VALUES ('product_3', 'producer_3', 8, 115);

set session transaction isolation level read committed;

drop table products;

create table products (
    id serial primary key,
    name varchar(50),
    producer varchar(50),
    count integer default 0,
    price integer
);

insert into products (name, producer, count, price) VALUES ('product_1', 'producer_1', 3, 50);
insert into products (name, producer, count, price) VALUES ('product_2', 'producer_2', 15, 32);
insert into products (name, producer, count, price) VALUES ('product_3', 'producer_3', 8, 115);

create table employees (
    id serial primary key,
    name varchar(50),
    birth_date date,
    post varchar(100),
    salary integer
);

insert into employees(name, birth_date, post, salary) values ('John', '1990-10-10', 'Officiant', 50000);
insert into employees(name, birth_date, post, salary) values ('Dave', '1995-01-12', 'Seller', 10000);
insert into employees(name, birth_date, post, salary) values ('Anna', '2000-10-10', 'Director', 300000);
insert into employees(name, birth_date, post, salary) values ('Stive', '1980-10-10', 'Developer', 200000);
insert into employees(name, birth_date, post, salary) values ('Mari', '1990-10-10', 'Courier', 50000);



