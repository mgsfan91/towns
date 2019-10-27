DROP SCHEMA IF EXISTS towns CASCADE;

CREATE SCHEMA towns;

CREATE TABLE towns.users (
  username varchar(50) NOT NULL PRIMARY KEY,
  password varchar(68) NOT NULL,
  enabled smallint NOT NULL
) ;
-- test test
INSERT INTO towns.users VALUES
('test','test',1);


CREATE TABLE towns.towns (
  id SERIAL NOT NULL PRIMARY KEY,
  name varchar(64) DEFAULT NULL,
  description varchar(256) NOT NULL,
  population integer NOT NULL,
  popularity_cnt integer DEFAULT NULL
) ;
INSERT INTO towns.towns VALUES
(1,'zagreb','glavni grad RH', 700000, 0),
(2,'split','dalmatinski glavni', 150000, 0),
(3,'slavonski brod','posavski glavni', 60000, 0);


CREATE TABLE towns.town_user (
  town_id integer references towns.towns(id),
  user_id varchar(50) references towns.users(username),
  constraint id PRIMARY KEY (town_id, user_id)
) ;
