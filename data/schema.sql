-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema adboarddb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema adboarddb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `adboarddb`;
USE `adboarddb`;

-- -----------------------------------------------------
-- Table `adboarddb`.`user_statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adboarddb`.`user_statuses`
(
    `id`    INT         NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adboarddb`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adboarddb`.`user_roles`
(
    `id`    INT         NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adboarddb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adboarddb`.`users`
(
    `id`                INT          NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(45)  NOT NULL,
    `email`             VARCHAR(45)  NOT NULL,
    `password`          VARCHAR(255) NOT NULL,
    `registration_date` DATETIME     NOT NULL,
    `hash`              VARCHAR(255) NOT NULL,
    `avatar`            VARCHAR(128) NOT NULL,
    `user_statuses_id`  INT          NOT NULL,
    `user_roles_id`     INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_users_user_statuses1_idx` (`user_statuses_id` ASC) VISIBLE,
    INDEX `fk_users_user_roles1_idx` (`user_roles_id` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_users_user_statuses1`
        FOREIGN KEY (`user_statuses_id`)
            REFERENCES `adboarddb`.`user_statuses` (`id`)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT,
    CONSTRAINT `fk_users_user_roles1`
        FOREIGN KEY (`user_roles_id`)
            REFERENCES `adboarddb`.`user_roles` (`id`)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adboarddb`.`item_categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adboarddb`.`item_categories`
(
    `id`    INT         NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adboarddb`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adboarddb`.`cities`
(
    `id`    INT         NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adboarddb`.`items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adboarddb`.`items`
(
    `id`                 INT          NOT NULL AUTO_INCREMENT,
    `title`              VARCHAR(100) NOT NULL,
    `price`              DECIMAL      NOT NULL,
    `description`        TEXT         NOT NULL,
    `contact`            VARCHAR(45)  NOT NULL,
    `create_time`        DATETIME     NOT NULL,
    `update_time`        DATETIME     NOT NULL,
    `picture`            VARCHAR(128) NOT NULL,
    `item_categories_id` INT          NOT NULL,
    `users_id`           INT          NOT NULL,
    `cities_id`          INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_items_item_categories1_idx` (`item_categories_id` ASC) VISIBLE,
    INDEX `fk_items_users1_idx` (`users_id` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_items_cities1_idx` (`cities_id` ASC) VISIBLE,
    CONSTRAINT `fk_items_item_categories1`
        FOREIGN KEY (`item_categories_id`)
            REFERENCES `adboarddb`.`item_categories` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_items_users1`
        FOREIGN KEY (`users_id`)
            REFERENCES `adboarddb`.`users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_items_cities1`
        FOREIGN KEY (`cities_id`)
            REFERENCES `adboarddb`.`cities` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adboarddb`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adboarddb`.`comments`
(
    `id`          INT      NOT NULL AUTO_INCREMENT,
    `text`        TEXT     NOT NULL,
    `create_time` DATETIME NOT NULL,
    `items_id`    INT      NOT NULL,
    `users_id`    INT      NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_comments_items1_idx` (`items_id` ASC) VISIBLE,
    INDEX `fk_comments_users1_idx` (`users_id` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_comments_items1`
        FOREIGN KEY (`items_id`)
            REFERENCES `adboarddb`.`items` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_comments_users1`
        FOREIGN KEY (`users_id`)
            REFERENCES `adboarddb`.`users` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adboarddb`.`bookmarks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `adboarddb`.`bookmarks`
(
    `users_id` INT NOT NULL,
    `items_id` INT NOT NULL,
    PRIMARY KEY (`users_id`, `items_id`),
    INDEX `fk_users_has_items_items1_idx` (`items_id` ASC) VISIBLE,
    INDEX `fk_users_has_items_users1_idx` (`users_id` ASC) VISIBLE,
    CONSTRAINT `fk_users_has_items_users1`
        FOREIGN KEY (`users_id`)
            REFERENCES `adboarddb`.`users` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_users_has_items_items1`
        FOREIGN KEY (`items_id`)
            REFERENCES `adboarddb`.`items` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
