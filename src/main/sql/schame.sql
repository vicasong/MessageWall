#@ mysql dialect
#---------------------------------------------------------------------------------------------------
# <description>The Definition Of MessageWall DataBase</description>
# <auth>Vica</auth>
# <date>2016-08-21</date>
# <version>1.0</version>
#---------------------------------------------------------------------------------------------------

-- Create Database Of MessageWall
CREATE DATABASE `db_messagewall` DEFAULT CHAR SET = utf8;

-- Use The Database
USE db_messagewall;

-- Create Table Of User
CREATE TABLE `t_user`(
  `user_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The Specified Identify Number Of User',
  `user_name` VARCHAR(100) NOT NULL COMMENT 'The Specified Name Of User For Login',
  `nick_name` VARCHAR(200) NOT NULL COMMENT 'The Custom Name Of User To Show',
  `password` VARCHAR(65) NOT NULL COMMENT 'The Security Strings For User To Login',
  `level` BIGINT NOT NULL DEFAULT 0 COMMENT 'The Exp Level Of User By Post Message',
  PRIMARY KEY (user_id),
  UNIQUE uq_login_name (user_name)
)ENGINE = InnoDB  DEFAULT CHAR SET = utf8 AUTO_INCREMENT = 100000 COMMENT 'User Table';

-- Create Table Of Message
CREATE TABLE `t_message`(
  `msg_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The Specified Identify Number Of Message',
  `title` VARCHAR(240) NOT NULL COMMENT 'The Title Of This Message',
  `content` VARCHAR(400) NOT NULL COMMENT 'The Content Strings Of This Message',
  `user_id` BIGINT NOT NULL COMMENT 'The User Id Who Posted This Message',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT 'The Time Of This Message Created',
  `topped` VARCHAR(8) DEFAULT 'No' COMMENT 'Whether This Message Is Topped',
  PRIMARY KEY (msg_id),
  FULLTEXT (title),
  FOREIGN KEY fk_message_user (user_id) REFERENCES t_user(user_id) ON UPDATE NO ACTION ON DELETE CASCADE ,
  INDEX idx_time (create_time),
  INDEX idx_top (topped)
)ENGINE = InnoDB  DEFAULT CHAR SET = utf8 AUTO_INCREMENT = 1000 COMMENT 'Message Table';

-- Create Table Of Reply
CREATE TABLE `t_reply`(
  `r_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The Specified Identify Number Of Reply',
  `content` VARCHAR(140) NOT NULL COMMENT 'The Content Strings Of This Reply',
  `user_id` BIGINT NOT NULL COMMENT 'The User Id Who Posted This Reply',
  `msg_id` BIGINT NOT NULL COMMENT 'The Message To Reply',
  `reply_id` BIGINT COMMENT 'The Replied Target Id',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT 'The Time Of This Reply Created',
  PRIMARY KEY (r_id),
  FOREIGN KEY fk_reply_user (user_id) REFERENCES t_user(user_id) ON UPDATE NO ACTION ON DELETE CASCADE ,
  FOREIGN KEY fk_reply_msg (msg_id) REFERENCES t_message(msg_id) ON DELETE CASCADE ON UPDATE NO ACTION ,
  FOREIGN KEY fk_reply_reply (reply_id) REFERENCES t_reply(r_id) ON UPDATE NO ACTION ON DELETE CASCADE ,
  INDEX idx_time (create_time)
)ENGINE = InnoDB  DEFAULT CHAR SET = utf8 AUTO_INCREMENT = 100 COMMENT 'Reply Table';

SELECT * FROM t_message  order by create_time desc limit 1,12;

DROP TABLE `db_messagewall`.`t_reply`;