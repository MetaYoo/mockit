-- DDL
-- MOCK_TEST
CREATE TABLE MOCK_TEST (
  `ID` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `USER_NAME` VARCHAR (50),
  `USER_PWD` VARCHAR (50),
  KEY idx_username (`USER_NAME`)
) ;