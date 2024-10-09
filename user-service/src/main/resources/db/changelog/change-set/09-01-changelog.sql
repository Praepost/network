-- liquibase formatted sql

-- changeset user:1728422876816-1
CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

-- changeset user:1728422876816-2
CREATE TABLE db.role
(
    id   BIGINT NOT NULL,
    name VARCHAR(20) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

-- changeset user:1728422876816-3
CREATE TABLE db.user
(
    id   BIGINT NOT NULL,
    email    VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- changeset user:1728422876816-4
CREATE TABLE db.user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT       NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, roles_id)
);

-- changeset user:1728422876816-5
ALTER TABLE db.user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES db.role(id);

-- changeset user:1728422876816-6
ALTER TABLE db.user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES db.user (id);

