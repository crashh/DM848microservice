drop table T_COMMENT if exists;

create table T_COMMENT (ID bigint identity primary key, COMMENT varchar(250), VIDEO_ID bigint, USER_ID varchar(200));

