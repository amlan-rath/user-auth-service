create schema if not exists user;

create table if not exists user.users(
 id INT primary key AUTO_INCREMENT,
 user_name VARCHAR(50),
 first_name VARCHAR(50),
 last_name VARCHAR(50),
 password VARCHAR(500) NOT NULL );
								
create table if not exists user.groups(
 id INT primary key AUTO_INCREMENT,
 group_name VARCHAR(50));
 
create table if not exists user.user_group_mapping(
 id INT primary key AUTO_INCREMENT,
 user_id INT NOT NULL,
 group_id INT NOT NULL,
 foreign key (USER_ID) references user.users(id),
 foreign key (GROUP_ID) references user.groups(id));
 
create table if not exists user.group_permission_mapping(
 id INT primary key AUTO_INCREMENT,
 parent_group_id INT NOT NULL,
 permission varchar(30),
 permission_group_id INT NOT NULL,
 foreign key (PARENT_GROUP_ID) references user.groups(id));