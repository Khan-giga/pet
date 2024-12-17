--liquibase formatted sql

-- changeset wood_type:1
CREATE TABLE wood_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(128)
);

-- changeset workpiece_diameter:1
CREATE TABLE workpiece_diameter (
    id BIGSERIAL PRIMARY KEY,
    milimeters_diameter INTEGER,
    board_count INTEGER
);

-- changeset workpiece:1
CREATE TABLE workpieces (
    id BIGSERIAL PRIMARY KEY,
    wood_types_id BIGINT,
    workpiece_diameter_id BIGINT NOT NULL,
    meters_length BIGINT NOT NULL,
    CONSTRAINT FK_workpieces_wood_types FOREIGN KEY (wood_types_id) REFERENCES wood_types (id),
    CONSTRAINT FK_workpiece_diameter FOREIGN KEY (workpiece_diameter_id) REFERENCES workpiece_diameter (id)
);

-- changeset board:1
CREATE TABLE boards (
    id BIGSERIAL PRIMARY KEY,
    wood_types_id BIGINT,
    workpieces_id BIGINT,
    CONSTRAINT FK_boards_wood_types FOREIGN KEY (wood_types_id) REFERENCES wood_types (id),
    CONSTRAINT FK_boards_workpieces FOREIGN KEY (workpieces_id) REFERENCES workpieces (id)
);

