create table if not exists humans (
    id serial primary key,
    name varchar
);

create table if not exists nations (
    id serial primary key,
    title varchar
);

alter table humans add column nation_id int references nations(id);

create table if not exists countries (
    id serial primary key,
    name varchar
);

create table if not exists human_country (
    human_id int references humans(id),
    country_id int references countries(id)
);

create table if not exists capitals (
    id serial primary key,
    name varchar,
    country_id int references countries(id) unique not null
);