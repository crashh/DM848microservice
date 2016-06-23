drop table T_USER if exists;

create table T_USER (ID bigint identity primary key, USER_NAME varchar(50),
                        NAME varchar(50), LAST_ACTIVE varchar(10), unique(USER_NAME));

