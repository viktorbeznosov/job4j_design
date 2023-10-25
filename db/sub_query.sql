CREATE TABLE companies(
    id int primary key,
    city text
);

CREATE TABLE goods(
    id int primary key,
    name text,
    company_id int references companies(id),
    price int
);

CREATE TABLE sales_managers(
    id int primary key,
    last_name text,
    first_name text,
    company_id int references companies(id),
    manager_fee int
);

CREATE TABLE managers(
    id int primary key,
    company_id int references companies(id)
);



INSERT INTO companies VALUES
(1, 'Москва'),
(2, 'Нью-Йорк'),
(3, 'Мюнхен');

INSERT INTO goods VALUES
(1, 'Небольшая квартира', 3, 5000),
(2, 'Квартира в центре', 1, 4500),
(3, 'Квартира у метро', 1, 3200),
(4, 'Лофт', 2, 6700),
(5, 'Загородный дом', 2, 9800);

INSERT INTO sales_managers VALUES
(1, 'Доу', 'Джон', 2, 2250),
(2, 'Грубер', 'Ганс', 3, 3120),
(3, 'Смит', 'Сара', 2, 1640),
(4, 'Иванов', 'Иван', 1, 4500),
(5, 'Купер', 'Грета', 3, 2130);

INSERT INTO managers VALUES
(1, 2),
(2, 3),
(4, 1);

SELECT name AS real_estate, price, (SELECT AVG(price) FROM goods) AS avg_price FROM goods;

SELECT AVG(manager_fee)
FROM sales_managers WHERE sales_managers.id NOT IN (SELECT managers.id FROM managers);

SELECT city, (SELECT count(*) FROM goods as g
              WHERE c.id = g.company_id) as total_goods FROM companies c;

SELECT c.city, COUNT(g.name) AS total_goods FROM companies c
                                                     JOIN goods g ON c.id = g.company_id GROUP BY c.city;

SELECT last_name, first_name, manager_fee
FROM sales_managers sm1
WHERE sm1.manager_fee >= (SELECT AVG(manager_fee)
                          FROM sales_managers sm2
                          WHERE sm2.company_id = sm1.company_id);

SELECT company_id, AVG(price) AS average_price
FROM goods
GROUP BY company_id
HAVING AVG(price) > (SELECT MAX(price) FROM goods) / 2;

-- Homework --

CREATE TABLE customers(
    id serial primary key,
    first_name text,
    last_name text,
    age int,
    country text
);

insert into customers (first_name, last_name, age, country)
values
('Viktor', 'Beznosov', 45, 'Russia'),
('John', 'Doe', 30, 'USA'),
('Dick','Burger', 25, 'Germany'),
('Anna', 'Smirnova', 30, 'Russia'),
('Mary', 'Smith', 40, 'England');

CREATE TABLE orders(
    id serial primary key,
    amount int,
    customer_id int references customers(id)
);

insert into orders (amount, customer_id)
values
(1000, 1),
(2000, 3),
(1000, 1),
(3000, 4),
(3500, 3),
(3000, 1),
(2000, 4);

select c.id, c.first_name, c.last_name
from customers c
where c.id not in (select distinct o.customer_id from orders o);

select c.id, c.first_name, c.last_name
from customers c
left join orders o on o.customer_id = c.id
where o.id is null;
