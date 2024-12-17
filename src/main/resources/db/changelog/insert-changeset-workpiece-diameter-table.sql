--liquibase formatted sql

-- changeset workpiece_diameter:1
INSERT INTO workpiece_diameter (milimeters_diameter, board_count) VALUES ('200', 3);

-- changeset workpiece_diameter:2
INSERT INTO workpiece_diameter (milimeters_diameter, board_count) VALUES ('500', 7);

-- changeset workpiece_diameter:3
INSERT INTO workpiece_diameter (milimeters_diameter, board_count) VALUES ('700', 12);
