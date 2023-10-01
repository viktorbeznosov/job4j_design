create table devices(
    id serial primary key,
    name varchar(255),
    price float
);

create table people(
    id serial primary key,
    name varchar(255)
);

create table devices_people(
    id serial primary key,
    device_id int references devices(id),
    people_id int references people(id)
);

insert into people (name) values
    ('Viktor'),
    ('Ivan'),
    ('Dmitry');
insert into devices (name, price) values
    ('Televosor', 20000),
    ('Computer', 50000),
    ('X Box', 60000),
    ('Mixer', 3000),
    ('Microwave', 5000);
insert into devices_people (device_id, people_id) values
    (1, 1),
    (2, 1),
    (3, 1),
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (3, 3),
    (4, 3),
    (5,3);

select p.name, avg(d.price)
from people as p
join devices_people as dp on dp.people_id = p.id
join devices as d on d.id = dp.device_id
group by p.id;

select p.name, avg(d.price)
from people as p
join devices_people as dp on dp.people_id = p.id
join devices as d on d.id = dp.device_id
group by p.id
having avg(d.price) > 5000;



