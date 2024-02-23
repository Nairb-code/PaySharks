INSERT INTO role_entity (nombre) VALUES ('ADMIN');
INSERT INTO role_entity (nombre) VALUES ('USER');

INSERT INTO user_entity (username, email, password, available) VALUES ("brian", "brian@correo.com", "12345", true);
INSERT INTO user_entity (username, email, password, available) VALUES ("gise", "gise@correo.com", "12345", true);
INSERT INTO user_entity (username, email, password, available) VALUES ("boris", "boris@correo.com", "12345", true);

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (3, 2);