-- 1. Создать таблицу с гостями: name, email, phone (id само собой)
create table guests (
	id serial primary key,
	name varchar(100) not null,
	email varchar(100) not null,
	phone varchar(100) not null
);

-- 2. Создать пользователя manager. Он может заносить данные в таблицу с гостями, а так же смотреть список гостей.
create user manager with password '123456';
grant select, update, insert on guests to manager;
grant usage, select on sequence guests_id_seq to manager;
grant select on guest_name to manager;

-- 3. Создать view guest_name. Должны быть только имена гостей.
create view guest_name as select name from guests;

-- 4. Создать пользователя guard. Он может только смотреть только guest_name.
create user guard with password 'q1w2e3r4';
grant select on guest_name to guard;