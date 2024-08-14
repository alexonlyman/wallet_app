create schema if not exists private_schema;

CREATE TABLE IF NOT EXISTS private_schema.t_user
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL

);

CREATE TABLE IF NOT EXISTS private_schema.t_wallet
(
    id      SERIAL PRIMARY KEY,
    amount  INT,
    id_user INT NOT NULL REFERENCES private_schema.t_user (id)

);



