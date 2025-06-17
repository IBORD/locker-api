CREATE TABLE USUARIO(    ID_USUARIO NUMBER NOT NULL,
                         LOGIN varchar2(512) UNIQUE NOT NULL,
                         SENHA varchar2(512) NOT NULL,
                         PRIMARY KEY(ID_USUARIO));

CREATE SEQUENCE seq_usuario START WITH     1 INCREMENT BY   1 NOCACHE NOCYCLE;
insert into usuario (id_usuario, login, senha)
values (seq_usuario.nextval, 'user', '123');

UPDATE USUARIO u
SET SENHA = '$2a$12$aYx.QJ75nP4hUHfuarHhNOnVVEuGx6sQBsZEwanHd2oJGM0tgSQ.y'
WHERE ID_USUARIO  = 1;