-- following long hex is sha-256 convert form of admin default password 12345
insert into user.users values (1, 'admin', 'admin', 'admin', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=');

insert into user.groups values (1, 'Admin Group');

insert into user.user_group_mapping values (1, 1, 1);

-- giving all major permissions to the admin user
insert into user.group_permission_mapping (parent_group_id, permission, permission_group_id) values (1, 'VIEW_ALL', -1);
insert into user.group_permission_mapping (parent_group_id, permission, permission_group_id) values (1, 'VIEW_ANY_GROUP', -1);
insert into user.group_permission_mapping (parent_group_id, permission, permission_group_id) values (1, 'CREATE_GROUP', -1);
insert into user.group_permission_mapping (parent_group_id, permission, permission_group_id) values (1, 'MAP_ANY_USER_TO_ANY_GROUP', -1);
insert into user.group_permission_mapping (parent_group_id, permission, permission_group_id) values (1, 'ASSIGN_PERMISSION_TO_ALL', -1);

commit ;