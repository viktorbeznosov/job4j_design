create table if not exists factories (
     id serial primary key,
     name varchar,
     address text
);



create table if not exists cars (
    id serial primary key,
    name varchar,
    price numeric(10,2),
    factory_id int references factories(id)
);

insert into factories (name, address) values
('Тольятинский авто завод', 'г. Тольяти'),
('Ульяновский авто завод', 'г. Ульяновск'),
('Камский авто завод', null);

insert into cars (name, price, factory_id) values
('Ваз 2101', 1000000, 1),
('Niva', 2000000, 1),
('Lada Kalina', 2500000, 1),
('Uaz Patriot', 2500000, 2),
('УАЗ Буханка', 3500000, 2),
('Камаз', 10000000, 3);

select c.name, c.price, f.name as factory_name
from cars as c
join factories as f on (c.factory_id = f.id)
where f.id = 1;


