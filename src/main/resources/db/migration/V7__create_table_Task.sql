create table task (
    id bigint not null auto_increment,
    title varchar(255) not null,
    description varchar(255),
    expiration_date DATE not null,
    state varchar(255) not null,
    priority varchar(255) not null,
    list_task_id bigint not null,

    primary key (id),
    foreign key (list_task_id) references list_tasks(id)
);

ALTER table list_tasks ADD foreign key (user_id) references users(id);