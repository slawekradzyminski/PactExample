-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS information;

CREATE TABLE information (
  id IDENTITY PRIMARY KEY,
  name VARCHAR(250) NOT NULL UNIQUE,
  nationality VARCHAR(250) NOT NULL,
  salary INTEGER DEFAULT NULL
);
