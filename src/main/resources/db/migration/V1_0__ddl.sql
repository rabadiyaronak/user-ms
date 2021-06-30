CREATE  SEQUENCE hibernate_sequence;

CREATE TABLE user
(
    id BIGINT PRIMARY KEY,
    name VARCHAR(200),
    email VARCHAR(200),
    is_active boolean
);