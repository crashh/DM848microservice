drop table T_USER if exists;

create table T_USER (ID bigint identity primary key, USER_NAME varchar(50),
                        NAME varchar(50), unique(USER_NAME));

