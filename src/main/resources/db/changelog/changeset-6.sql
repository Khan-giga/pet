--liquibase formatted sql

-- changeset file_status:1
create table file_status (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(255) NOT NULL,
    comment VARCHAR(255)
)
