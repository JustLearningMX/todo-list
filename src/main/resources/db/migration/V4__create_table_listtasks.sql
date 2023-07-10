create table list_tasks (
       id bigint not null auto_increment,
       name varchar(255) not null,
       description varchar(255) not null,

       primary key (id)
);