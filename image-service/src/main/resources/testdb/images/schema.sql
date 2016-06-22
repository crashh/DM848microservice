drop table T_IMAGE if exists;

create table T_IMAGE (
  ID bigint identity primary key,
  NAME varchar(75),
  DESCRIPTION varchar(500),
  LINK varchar(100),
  USER_NAME varchar(100),
  CDATE varchar(10)
);

