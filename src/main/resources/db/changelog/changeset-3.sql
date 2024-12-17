--liquibase formatted sql

-- changeset workpiece:1
CREATE TABLE workpieces (
    id                    BIGSERIAL PRIMARY KEY,
    wood_types_id         BIGINT,
    workpiece_diameter_id BIGINT NOT NULL,
    meters_length         BIGINT NOT NULL,
    CONSTRAINT FK_workpieces_wood_types FOREIGN KEY (wood_types_id) REFERENCES wood_types (id),
    CONSTRAINT FK_workpiece_diameter FOREIGN KEY (workpiece_diameter_id) REFERENCES workpiece_diameter (id)
);