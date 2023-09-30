create table if not exists roles (
    id serial primary key,
    name varchar
);

create table if not exists rules (
    id serial primary key,
    name varchar
);

create table if not exists role_rules (
    role_id int references roles(id),
    rule_id int references rules(id)
);

create table if not exists users (
    id serial primary key,
    name varchar,
    role_id int references roles(id)
);

create table if not exists states (
    id serial primary key,
    name varchar
);

create table if not exists categories (
    id serial primary key,
    name varchar
);

create table if not exists items (
    id serial primary key,
    category_id int references categories(id),
    state_id int references states(id),
    name varchar,
    user_id int references users(id)
);

create table if not exists comments (
    id serial primary key,
    text text,
    item_id int references items(id)
);

create table if not exists attachs (
    id serial primary key,
    filename varchar,
    item_id int references items(id)
);

