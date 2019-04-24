DROP DATABASE IF EXISTS TattooSalon;
CREATE DATABASE TattooSalon DEFAULT CHARACTER SET utf8;
USE TattooSalon;

CREATE TABLE user_discount(
discount_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
discount INT NOT NULL);
INSERT INTO user_discount(discount) VALUES (15), (20), (50), (60), (75), (80);

CREATE TABLE user_role(
role_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
role VARCHAR(30) NOT NULL);
INSERT INTO user_role(role) VALUES ('admin'), ('client');

CREATE TABLE status(
status_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
status_name VARCHAR(30) NOT NULL);
INSERT INTO status(status_name) VALUES ('submitted'), ('accepted'), ('cancelled'), ('awaitingFeedBack'), ('closed');

CREATE TABLE image_type(
type_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
type VARCHAR(30) NOT NULL);
INSERT INTO image_type(type) VALUES ('title'), ('ordinary'), ('sketch');

CREATE TABLE user(
user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
email VARCHAR(30) NOT NULL,
login VARCHAR(30) NOT NULL,
password VARCHAR(100) NOT NULL,
username VARCHAR(30) NOT NULL,
user_status BIT NOT NULL,
rating INT NOT NULL,
role_id INT NOT NULL);
ALTER TABLE user ADD FOREIGN KEY (role_id) REFERENCES user_role (role_id) ON DELETE CASCADE;

CREATE TABLE discount_card(
card_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
user_id INT NOT NULL,
card_number INT NOT NULL,
active_status BIT NOT NULL,
discount_id INT NOT NULL);
ALTER TABLE discount_card ADD FOREIGN KEY (discount_id) REFERENCES user_discount (discount_id) ON DELETE CASCADE;
ALTER TABLE discount_card ADD FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE;

CREATE TABLE tattoo(
tattoo_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL);
CREATE TABLE image(
image_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
image VARCHAR(200) NOT NULL);

CREATE TABLE user_proposal(
proposal_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
image_id INT NOT NULL,
user_id INT NOT NULL,
date DATETIME NOT NULL,
status_id INT NOT NULL,
rating INT NOT NULL);
ALTER TABLE user_proposal ADD FOREIGN KEY (image_id) REFERENCES image(image_id) ON DELETE CASCADE;
ALTER TABLE user_proposal ADD FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE;
ALTER TABLE user_proposal ADD FOREIGN KEY (status_id) REFERENCES status(status_id) ON DELETE CASCADE;

CREATE TABLE tattoo_image(
tattoo_image_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
image_id INT NOT NULL,
tattoo_id INT NOT NULL,
type_id INT NOT NULL);
ALTER TABLE tattoo_image ADD FOREIGN KEY (image_id) REFERENCES image(image_id) ON DELETE CASCADE;
ALTER TABLE tattoo_image ADD FOREIGN KEY (tattoo_id) REFERENCES tattoo(tattoo_id) ON DELETE CASCADE;
ALTER TABLE tattoo_image ADD FOREIGN KEY (type_id) REFERENCES image_type(type_id) ON DELETE CASCADE;

CREATE TABLE user_order(
order_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
user_id INT NOT NULL,
tattoo_id INT NOT NULL,
rating INT NOT NULL,
date DATETIME NOT NULL,
status_id INT NOT NULL);
ALTER TABLE user_order ADD FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE;
ALTER TABLE user_order ADD FOREIGN KEY (tattoo_id) REFERENCES tattoo(tattoo_id) ON DELETE CASCADE;
ALTER TABLE user_order ADD FOREIGN KEY (status_id) REFERENCES status(status_id) ON DELETE CASCADE;

CREATE TABLE tattoo_rating(
rating_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
user_id INT NOT NULL,
tattoo_id INT NOT NULL,
rating INT NOT NULL);
ALTER TABLE tattoo_rating ADD FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE;
ALTER TABLE tattoo_rating ADD FOREIGN KEY (tattoo_id) REFERENCES tattoo(tattoo_id) ON DELETE CASCADE;