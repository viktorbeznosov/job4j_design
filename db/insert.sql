insert into roles (name) values ('admin'), ('user');
insert into rules (name) values ('create item'), ('leave comment');
insert into role_rules (role_id, rule_id) values (1, 1), (1, 2), (2, 2);
insert into categories (name) values ('urgent'), ('usual');
insert into states (name) values ('new'), ('in_work'), ('done');