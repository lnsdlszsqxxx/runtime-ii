DROP TABLE IF EXISTS department CASCADE;
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS account CASCADE;

DROP SEQUENCE IF EXISTS department_id_seq;
DROP SEQUENCE IF EXISTS student_id_seq;
DROP SEQUENCE IF EXISTS account_id_seq;

CREATE SEQUENCE department_id_seq START WITH 1;
CREATE SEQUENCE student_id_seq START WITH 1;
CREATE SEQUENCE account_id_seq START WITH 1;

CREATE TABLE department (
--    id                INTEGER NOT NULL default nextval('department_id_seq'),
   id SERIAL NOT NULL,
   name              VARCHAR(30) not null unique,
   description       VARCHAR(150),
   location          VARCHAR(100)
);
ALTER TABLE department ADD CONSTRAINT department_pk PRIMARY KEY ( id );


CREATE TABLE student (
--    id              INTEGER NOT NULL default nextval('student_id_seq'),
   id SERIAL NOT NULL,
   name            VARCHAR(30) not null unique,
   first_name      VARCHAR(30),
   last_name       VARCHAR(30),
   email           VARCHAR(50),
   address         VARCHAR(150),
   department_id   INTEGER NOT NULL
);
ALTER TABLE student ADD CONSTRAINT student_pk PRIMARY KEY ( id );


CREATE TABLE account (
--    id             INTEGER NOT NULL default nextval('account_id_seq'),
   id SERIAL NOT NULL,
   account_type   VARCHAR(30),
   balance        NUMERIC(10, 2),
   student_id    INTEGER NOT NULL
);
ALTER TABLE account ADD CONSTRAINT account_pk PRIMARY KEY ( id );


ALTER TABLE account
   ADD CONSTRAINT account_student_fk FOREIGN KEY ( student_id )
       REFERENCES student ( id );

ALTER TABLE student
   ADD CONSTRAINT student_department_fk FOREIGN KEY ( department_id )
       REFERENCES department ( id );
