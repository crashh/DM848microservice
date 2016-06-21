drop table T_COMMENT if exists;

create table T_COMMENT (ID bigint identity primary key, COMMENT varchar(200), VIDEO_ID bigint, USER_ID bigint);

