
create table if not exists users
(
    id serial not null,
    name varchar(255) not null,
    surname varchar(255) not null,
    age int4,
    gender varchar(1),
    interests varchar(255),
    city varchar(255),
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