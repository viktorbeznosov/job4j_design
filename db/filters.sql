
create table if not exists "type" (
    id serial primary key,
    name varchar(255)
);

create table if not exists product (
    id serial primary key,
    name varchar(255),
    type_id int references type(id),
    expired_date date,
    price numeric (8, 2)
);

INSERT INTO "type" ("name") VALUES('СЫР');
INSERT INTO "type" ("name") VALUES('МОЛОЧКА');
INSERT INTO "type" ("name") VALUES('РЫБА');
INSERT INTO "type" ("name") VALUES('МЯСО');
INSERT INTO "type" ("name") VALUES('ГМО');

INSERT INTO product ("name", type_id, expired_date, price) VALUES('Пармезан', 1, '2023-10-15', 200.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('мороженое', 2, '2023-10-15', 100.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Сыр Российский', 1, '2023-09-28', 300.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Ряженка', 2, '2023-09-28', 50.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Кефир', 2, '2023-10-10', 60.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Селедка', 3, '2023-09-28', 100.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Путассу', 3, '2023-10-10', 150.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Кура', 4, '2023-10-10', 100.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Индейка', 4, '2023-10-10', 200.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Свинина', 4, '2023-09-28', 300.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Говядина', 4, '2023-10-10', 500.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Кукуруза', 5, '2023-10-10', 100.00);
INSERT INTO product ("name", type_id, expired_date, price) VALUES('Горошек', 5, '2023-10-10', 200.00);

select p.name
from product as p
join "type" as t on t.id = p.type_id
where t.name like 'СЫР';

select *
from product as p
where p.name like '%мороженое%';

select * from product as p
where p.expired_date < now();

select t.name, max(p.price)
from product as p
join "type" as t on t.id = p.type_id
group by t.id;

select t.name, count(p.id)
from product as p
join "type" as t on t.id = p.type_id
group by t.id;

select p.name, t.name as type_name
from product as p
join "type" as t on t.id = p.type_id
where t.name like 'СЫР' or t.name like 'МОЛОКО'

select t.name
from product as p
join "type" as t on t.id = p.type_id
group by t.id
having count(p.id) < 10;

select p.name, t.name as type_name
from product as p
join "type" as t on t.id = p.type_id;





