
create table if not exists users
(
    id serial not null,
    name varchar(255) not null,
    surname varchar(255) not null,
    age int4 not null,
    gender varchar(1) not null,
    interests varchar(255) not null,
    city varchar(255) not null,
    primary key (id)
);

create table if not exists personal_pages
(
    id serial not null,
    user_id serial not null references users(id),
    name varchar(255),
    surname varchar(255),
    age int4,
    gender varchar(1),
    interests varchar(255),
    city varchar(255),
    languages varchar(255),
    education varchar(255),
    job varchar(255),
    life_phylosophy varchar(255),
    primary key (id)
);

create table if not exists friends
(
    id serial not null,
    first_friend int4 not null references users(id),
    second_friend int4 not null references users(id),
    status varchar(10),
    primary key (id)
)