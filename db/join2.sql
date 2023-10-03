create table if not exists departments (
    id serial primary key,
    name varchar(255)
);

create table if not exists employees (
    id serial primary key,
    name varchar(255),
    department_id int references departments(id)
);

insert into departments (name) values
('Test'),
('Support'),
('Development');

insert into employees (name, department_id) values
('Viktor', 3),
('Ivan', 3),
('Sergey', null),
('Tatiana', 2),
('Dmitry', 2),
('Anton', null),
('Igor', 1),
('Vasily', 1),
('Anna', 1),
('Stas', null);

select * from employees e left join departments d on d.id = e.department_id;
select * from departments d right join employees e on d.id = e.department_id;

select * from employees e right join departments d on d.id = e.department_id;
select * from departments d left join employees e on d.id = e.department_id;

select * from employees e full join departments d on d.id = e.department_id;

select * from employees e cross join departments d;

create type gender AS ENUM ('М', 'Ж');

create table if not exists teens (
    id serial primary key,
    name varchar(255),
    gender gender
);

insert into teens (name, gender) values
('Ivan', 'М'),
('Viktor', 'М'),
('Sergey', 'М'),
('Dmitry', 'М'),
('Svetlana', 'Ж'),
('Elena', 'Ж'),
('Anna', 'Ж');

select men.name as man, women.name as woman
from teens men
cross join teens women
where men.gender = 'М' and women.gender = 'Ж';



