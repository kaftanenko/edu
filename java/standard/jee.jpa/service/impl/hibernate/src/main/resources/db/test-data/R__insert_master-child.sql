
insert into  MODEL_MASTER (id) values (1);
insert into  MODEL_MASTER (id, some_data_field) values (2, 2);
insert into  MODEL_MASTER (id, some_data_field) values (3, 2);

insert into  MODEL_CHILD (id, master_id, some_data_field) values (11, 3, 2);
insert into  MODEL_CHILD (id, master_id) values (12, 3);
insert into  MODEL_CHILD (id, master_id, some_data_field) values (13, 3, 5);
