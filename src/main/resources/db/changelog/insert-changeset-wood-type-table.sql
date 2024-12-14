--liquibase formatted sql

-- changeset wood_type:1
INSERT INTO wood_types (name) VALUES ('Дуб');

-- changeset wood_type:2
INSERT INTO wood_types (name) VALUES ('Ель');

-- changeset wood_type:3
INSERT INTO wood_types (name) VALUES ('Сосна');
