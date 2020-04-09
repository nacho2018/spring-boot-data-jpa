insert into clientes (id, nombre, apellido, email, create_at, foto) values (1, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
insert into clientes (id, nombre, apellido, email, create_at, foto) values (2, 'John', 'Doe', 'john.doe@gmail.com', '2017-08-28', '');
insert into clientes (id, nombre, apellido, email, create_at, foto) values (3, 'Alfred', 'Toledo', 'alfred@bolsadeideas.com', '2017-07-11', '');
insert into clientes (id, nombre, apellido, email, create_at, foto) values (4, 'Peter', 'Gamuza', 'peter.gamu@gmail.com', '2016-07-28', '');
insert into clientes (id, nombre, apellido, email, create_at, foto) values (5, 'Sam', 'Guilter', 'sammy@bolsadeideas.com', '2013-12-28', '');
insert into clientes (id, nombre, apellido, email, create_at, foto) values (6, 'Jonny', 'Gunner', 'gunner@gmail.com', '2017-12-12', '');
insert into clientes (id, nombre, apellido, email, create_at, foto) values (7, 'Mary', 'Walls', 'm.waals@bolsadeideas.com', '2014-09-28', '');
insert into clientes (id, nombre, apellido, email, create_at, foto) values (8, 'Gerney', 'Wash', 'wash78@gmail.com', '2017-10-13', '');
insert into clientes (id, nombre, apellido, email, create_at, foto) values (9, 'Jose', 'Torres', 'jos.torres@bolsadeideas.com', '2015-03-28', '');
insert into clientes (id, nombre, apellido, email, create_at, foto) values (10, 'John2', 'Doe2', 'john.doe@gmail.com', '2017-08-28', '');
--insert into clientes (id, nombre, apellido, email, create_at) values (11, 'Andres2', 'Guzman2', 'profesor2@bolsadeideas.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (13, 'John3', 'Doe3', 'john3.doe@gmail.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (14, 'Sammy', 'Guzman', 'guzman@bolsadeideas.com', '2017-09-12');
--insert into clientes (id, nombre, apellido, email, create_at) values (15, 'Peter2', 'Doe6', 'doe4@gmail.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (16 ,'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (17, 'John', 'Doe', 'john.doe@gmail.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (18, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (19, 'John', 'Doe', 'john.doe@gmail.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (20, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (21, 'John', 'Doe', 'john.doe@gmail.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (22, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (23, 'John', 'Doe', 'john.doe@gmail.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (24, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28');
--insert into clientes (id, nombre, apellido, email, create_at) values (25, 'John', 'Doe', 'john.doe@gmail.com', '2017-08-28');

/*Populate tabla de productos*/

insert into productos (nombre, precio, create_at)  values ('Panasonic Pantalla LCD', 300.55, NOW());
insert into productos (nombre, precio, create_at)  values ('Sony Camara Digital DSC-W3208', 90.45, NOW());
insert into productos (nombre, precio, create_at)  values ('Apple iPod shuffle', 45.90, NOW());
insert into productos (nombre, precio, create_at)  values ('Sony Notebook Z110', 200.70, NOW());
insert into productos (nombre, precio, create_at)  values ('Hewlett Packard Multifunctional F2280', 120.99, NOW());
insert into productos (nombre, precio, create_at)  values ('Mica Comoda 5 Cajones', 45.88, NOW());

/*populate facturas*/
insert into facturas(descripcion, observacion, cliente_id, create_at) values ('Factura Equipos Digitales', null, 1,  NOW());
insert into facturas_item (cantidad, factura_id, producto_id) values (1, 1, 1);
insert into facturas_item (cantidad, factura_id, producto_id) values (2, 1, 2);
insert into facturas_item (cantidad, factura_id, producto_id) values (1, 1, 3);
insert into facturas_item (cantidad, factura_id, producto_id) values (1, 1, 4);

insert into facturas(descripcion, observacion, cliente_id, create_at) values ('Factura Cajones', 'Alguna nota importante!', 1, NOW());
insert into facturas_item (cantidad, factura_id, producto_id) values (3, 2, 6);

/*creamos algunos usuarios con sus roles*/
INSERT INTO users (username, password, enabled) VALUES('andres', '$2a$10$O9wxmH/AeyZZzIS09Wp8YOEMvFnbRVJ8B4dmAMVSGloR62lj.yqXG', 1);
INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS', 1);

INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_ADMIN');
