--liquibase formatted sql

-- changeset wood_type:1
CREATE TABLE wood_types (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128)
);