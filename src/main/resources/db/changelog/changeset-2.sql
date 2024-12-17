--liquibase formatted sql

-- changeset workpiece_diameter:1
CREATE TABLE workpiece_diameter (
    id                  BIGSERIAL PRIMARY KEY,
    milimeters_diameter INTEGER,
    board_count         INTEGER
);