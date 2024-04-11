create database student;
use student;
create table student_info(id int not null auto_increment, firstName varchar(60) default null, secondName varchar(60) default null,email  varchar(100) default null,PRIMARY KEY (id));
insert into student_info values(1,"Zoro","Roronova","zoro@gmail.com"),(2,"luffy","monkey.d","meat@gmail.com");

