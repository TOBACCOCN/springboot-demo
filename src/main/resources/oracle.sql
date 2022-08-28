CREATE TABLESPACE test_tablespace datafile 'C:\APP\TOBACCO\ORADATA\ORCL\TEST01.DBF' size 10m;
CREATE USER TEST identified by test default tablespace test_tablespace temporary tablespace temp quota unlimited on test_tablespace;
GRANT create session to test;

CREATE TABLE TEST.STUDENT_INFO (
    ID          NUMBER(10) PRIMARY KEY,
    NAME        VARCHAR2(10),
    SEX         VARCHAR2(2),
    CREATE_DATE DATE
);
