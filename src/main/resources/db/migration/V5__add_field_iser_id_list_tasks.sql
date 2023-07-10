ALTER table list_tasks ADD COLUMN user_id bigint NOT NULL;
ALTER table list_tasks MODIFY COLUMN description varchar(500);