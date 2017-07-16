CREATE TABLE USER (
  id BIGINT NOT NULL AUTO_INCREMENT,
  active BOOLEAN DEFAULT NULL,
  name VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);