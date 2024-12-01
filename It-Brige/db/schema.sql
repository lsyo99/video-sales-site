-- -----------------------------------------------------
-- Schema ItBridge
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ItBridge` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ItBridge`;

-- Table: authority
CREATE TABLE IF NOT EXISTS `ItBridge`.`authority` (
  `auth_id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `auth_name` VARCHAR(20) NULL,
  `auth_level` VARCHAR(10) NULL,
  PRIMARY KEY (`auth_id`)
) ENGINE = InnoDB;

-- Table: User
CREATE TABLE IF NOT EXISTS `ItBridge`.`User` (
  `user_id` BIGINT(32) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(20) NOT NULL,
  `birthday` DATE NOT NULL,
  `sign_date` DATE NOT NULL,
  `auth_id` BIGINT(10) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_auth_id`
    FOREIGN KEY (`auth_id`)
    REFERENCES `ItBridge`.`authority` (`auth_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- Table: board
CREATE TABLE IF NOT EXISTS `ItBridge`.`board` (
  `board_id` BIGINT(32) NOT NULL AUTO_INCREMENT,
  `board_name` VARCHAR(50) NULL,
  `auth_id` BIGINT(10) NULL,
  PRIMARY KEY (`board_id`),
  CONSTRAINT `fk_board_auth_id`
    FOREIGN KEY (`auth_id`)
    REFERENCES `ItBridge`.`authority` (`auth_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- Table: post
CREATE TABLE IF NOT EXISTS `ItBridge`.`post` (
  `post_id` BIGINT(32) NOT NULL AUTO_INCREMENT,
  `post_name` VARCHAR(200) NULL,
  `img_id` BIGINT(32) NULL,
  `post_type` DATE NULL,
  `status` VARCHAR(10) NULL,
  `board_id` BIGINT(32) NULL,
  PRIMARY KEY (`post_id`),
  CONSTRAINT `fk_post_board_id`
    FOREIGN KEY (`board_id`)
    REFERENCES `ItBridge`.`board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- Table: lecture
CREATE TABLE IF NOT EXISTS `ItBridge`.`lecture` (
  `lecture_id` BIGINT(32) NOT NULL,
  `lecture_name` VARCHAR(100) NULL,
  `upload_at` DATE NULL,
  `lecture_price` DECIMAL(10, 2) NULL,
  `lecture_subject` VARCHAR(10) NULL,
  `thumbnailUrl` VARCHAR(200) NULL,
  PRIMARY KEY (`lecture_id`)
) ENGINE = InnoDB;

-- Table: payment
CREATE TABLE IF NOT EXISTS `ItBridge`.`payment` (
  `pay_number` BIGINT(32) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(32) NULL,
  `lecture_id` BIGINT(32) NULL,
  `payment_date` DATE NULL,
  `payment_type` VARCHAR(20) NULL,
  PRIMARY KEY (`pay_number`),
  CONSTRAINT `fk_payment_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `ItBridge`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_lecture_id`
    FOREIGN KEY (`lecture_id`)
    REFERENCES `ItBridge`.`lecture` (`lecture_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

-- Table: image
CREATE TABLE IF NOT EXISTS `ItBridge`.`image` (
  `img_id` BIGINT(32) NOT NULL AUTO_INCREMENT,
  `img_url` VARCHAR(200) NULL,
  PRIMARY KEY (`img_id`)
) ENGINE = InnoDB;
