CREATE TABLE IF NOT EXISTS user (
  user_id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250),
  email VARCHAR(250),
  password VARCHAR(250),
  deleted BOOLEAN
);

INSERT INTO user SELECT 1, 'ricardo', 'ricardo@gmail.com', 'pass word', false FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE user_id = 1);
INSERT INTO user SELECT 2, 'charles', 'charles@gmail.com', 'kotlin', true FROM DUAL WHERE NOT EXISTS (SELECT * FROM user WHERE user_id = 2);






