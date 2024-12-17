--liquibase formatted sql

-- changeset board:1
CREATE TABLE boards (
    id            BIGSERIAL PRIMARY KEY,
    wood_types_id BIGINT,
    workpieces_id BIGINT,
    CONSTRAINT FK_boards_wood_types FOREIGN KEY (wood_types_id) REFERENCES wood_types (id),
    CONSTRAINT FK_boards_workpieces FOREIGN KEY (workpieces_id) REFERENCES workpieces (id)
);