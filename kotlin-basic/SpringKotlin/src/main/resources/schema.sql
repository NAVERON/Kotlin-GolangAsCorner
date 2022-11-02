

create table if not exists messages (
    id integer auto_increment,
    text varchar(200),
    primary key(id)
);

insert into messages(text) values('test1');
insert into messages(text) values('test2');
insert into messages(text) values('test3');




