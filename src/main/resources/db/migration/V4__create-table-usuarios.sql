create table usuario(
     id bigint not null auto_increment primary key,
     login varchar(100) not null  unique,
     senha varchar(100) not null
);