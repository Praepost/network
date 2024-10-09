-- liquibase formatted sql

-- changeset user:1728422876816-7
insert into db.role (id, name)
values (1, 'ROLE_USER');

-- changeset user:1728422876816-8
insert into db.role (id, name)
values (2, 'ROLE_MODERATOR');

-- changeset user:1728422876816-9
insert into db.user (id, password , email) values (1, 'moderator', 'moderator');
-- changeset user:1728422876816-10
insert into db.user (id, password , email) values (2, 'user', 'user');

-- changeset user:1728422876816-11
insert into db.user_roles (user_id, roles_id) values (1, 1);
-- changeset user:1728422876816-12
insert into db.user_roles (user_id, roles_id) values (2, 2);
