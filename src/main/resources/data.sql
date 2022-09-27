-- Usuário: root@email.com
-- Senha: root
INSERT INTO USUARIO(nome, email, senha) VALUES('Usuário Root', 'root@email.com', '$2a$10$OwdE5R9brie2GWObVCTAYe8p5QIRa/PaxIv0l6490/4LcYDTIo0rC');
INSERT INTO PERFIL(authority) VALUES('ROLE_USER');
INSERT INTO USUARIO_PERFIS(USUARIO_ID, PERFIS_ID) VALUES(1, 1);