drop table T_USER if exists;

create table T_USER (ID bigint identity primary key, NUMBER varchar(9),
                        NAME varchar(50) not null, BALANCE decimal(8,2), unique(NUMBER));
                        
ALTER TABLE T_USER ALTER COLUMN BALANCE SET DEFAULT 0.0;
