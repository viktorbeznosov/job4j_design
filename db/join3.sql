create table if not exists car_bodies (
                                          id serial primary key,
                                          name varchar(255)
);

create table if not exists car_engines (
                                           id serial primary key,
                                           name varchar(255)
);

create table if not exists car_transmissions (
                                                 id serial primary key,
                                                 name varchar(255)
);

create table if not exists cars (
                                    id serial primary key,
                                    name varchar(255),
                                    body_id int references car_bodies(id),
                                    engine_id int references car_engines(id),
                                    transmission_id int references car_transmissions(id)
);

insert into car_bodies (name) values ('седан'), ('хэтчбек'), ('пикап');
insert into car_engines (name) values ('бензин'), ('дизель'), ('электрический');
insert into car_transmissions (name) values ('механика'), ('автомат'), ('вариатор');

insert into cars (name, body_id, engine_id, transmission_id)
values
('Lada', 1, 1, 1),
('Москвич', 1, 1, 1),
('Mersedes', 1, 1, 2),
('BNW', 1, 1, 2),
('Volvo', 1, 2, 2),
('Jeep', 3, 2, 2),
('BMW X5', 3, 2, 2),
('Golf car', null, 1, null),
('Volks Vagen', 1, 1, 1),
('ГАЗ 66', null, 1, 1);

select c.id, c.name as car_name, b.name as body_name, e.name as engine_name, t.name as transmission_name
from cars c
         left join car_bodies b on b.id = c.body_id
         left join car_engines e on e.id = c.engine_id
         left join car_transmissions t on t.id = c.transmission_id;

select b.id, b.name from car_bodies b
                             left join cars c on c.body_id = b.id
where c.id is null

select e.id as engine_id, e.name as engine_name
from cars c
         right join car_engines e on e.id = c.engine_id
where c.id is null;

select t.id as transmission_id, t.name as transmission_name
from cars c
         right join car_transmissions t on t.id = c.engine_id
where c.id is null;

