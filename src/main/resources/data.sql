INSERT INTO role (id, name) VALUES (1, 'USER') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO role (id, name) VALUES (2, 'ADMIN') ON DUPLICATE KEY UPDATE name=name;
