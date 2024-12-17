--liquibase formatted sql

-- changeset part:1
CREATE TABLE part
(
    id          BIGSERIAL PRIMARY KEY,
    part_number BIGINT NOT NULL UNIQUE
);

-- changeset part_result_planks:1
CREATE TABLE part_result_planks (
    part_result_id BIGINT       NOT NULL,
    wood_type      VARCHAR(128) NOT NULL,
    quantity       BIGINT       NOT NULL,
    PRIMARY KEY (wood_type, quantity),
    CONSTRAINT fk_part_result_planks_part FOREIGN KEY (part_result_id) REFERENCES part (id)
);