CREATE TABLE roles(
    id BIGSERIAL PRIMARY KEY,
    name varchar(60)
);

CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(60),
    password INT,
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(id)

ALTER TABLE users ALTER COLUMN password TYPE VARCHAR(60);


