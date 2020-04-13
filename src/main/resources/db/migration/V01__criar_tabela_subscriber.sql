CREATE TABLE subscribers (
  chat_id BIGINT NOT NULL,
  nome VARCHAR(255),
  sobrenome VARCHAR(255),
  usuario VARCHAR(255),
  user_id BIGINT NOT NULL, 
  msg_id BIGINT NOT NULL, 
  PRIMARY KEY (chat_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
