--创建用户表
create table users
(
  id          NUMBER(8)   NOT NULL,
  username    VARCHAR(20) NOT NULL,
  password    VARCHAR(20) NOT NULL,
  email       VARCHAR(30),
  dateOfBirth DATE,
  realname    VARCHAR(20),
  phone       VARCHAR(11),
  CONSTRAINT pk_users PRIMARY KEY (id)
);

COMMIT ;
--创建序列，用于用户主键
create SEQUENCE seq_user
  START WITH 1000
  INCREMENT BY 1
  NOMAXVALUE
  NOCYCLE
  CACHE 10;

CREATE table recordLog
(
  id number(8) not null,
  userId number(8),
  type varchar2(20) not null,
  methodName varchar2(100) not null,
  recordDate DATE not null,
  IPADDR VARCHAR2(20),
  errorcode varchar2(30),
  errorMessage varchar2(500),
  CONSTRAINT pk_loggs PRIMARY KEY (id)
);

CREATE SEQUENCE seq_loggs
  START WITH 1000
  INCREMENT BY 1
  NOMAXVALUE
  NOCYCLE
  CACHE 10;

