CREATE TABLE ROLE (
  id BIGINT NOT NULL AUTO_INCREMENT,
  role VARCHAR(255) DEFAULT NULL UNIQUE,
  PRIMARY KEY(id),
);