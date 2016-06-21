drop table T_VIDEO if exists;

create table T_VIDEO (ID bigint identity primary key, NAME varchar(75),
                        DESCRIPTION varchar(200),LINK varchar(100));

