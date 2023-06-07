-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema teachsync
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `teachsync` ;

-- -----------------------------------------------------
-- Schema teachsync
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `teachsync` DEFAULT CHARACTER SET utf8mb3 ;
USE `teachsync` ;

-- -----------------------------------------------------
-- Table `teachsync`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`course` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`course` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `courseName` VARCHAR(45) NOT NULL,
  `courseDesc` LONGTEXT NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`classroom`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`classroom` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`classroom` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `courseId` BIGINT NOT NULL,
  `className` VARCHAR(45) NOT NULL,
  `classDesc` LONGTEXT NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_class_course1_idx` (`courseId` ASC) VISIBLE,
  CONSTRAINT `fk_class_course1`
    FOREIGN KEY (`courseId`)
    REFERENCES `teachsync`.`course` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`homework`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`homework` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`homework` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `classId` BIGINT NOT NULL,
  `homeworkName` VARCHAR(45) NOT NULL,
  `homeworkDesc` LONGTEXT NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_homework_class1_idx` (`classId` ASC) VISIBLE,
  CONSTRAINT `fk_homework_class1`
    FOREIGN KEY (`classId`)
    REFERENCES `teachsync`.`classroom` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`role` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `roleName` VARCHAR(45) NOT NULL,
  `roleDesc` LONGTEXT NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`user` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `roleId` BIGINT NOT NULL,
  `parentId` BIGINT NULL DEFAULT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `fullName` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `phone` VARCHAR(10) NULL DEFAULT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role_idx` (`roleId` ASC) VISIBLE,
  INDEX `fk_user_user1_idx` (`parentId` ASC) VISIBLE,
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`roleId`)
    REFERENCES `teachsync`.`role` (`id`),
  CONSTRAINT `fk_user_user_parent_child`
    FOREIGN KEY (`parentId`)
    REFERENCES `teachsync`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`homework_user_pair`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`homework_user_pair` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`homework_user_pair` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `homeworkId` BIGINT NOT NULL,
  `userId` BIGINT NOT NULL,
  `score` FLOAT NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_homework_user_pair_user1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_homework_user_pair_homework1_idx` (`homeworkId` ASC) VISIBLE,
  CONSTRAINT `fk_homework_user_pair_homework1`
    FOREIGN KEY (`homeworkId`)
    REFERENCES `teachsync`.`homework` (`id`),
  CONSTRAINT `fk_homework_user_pair_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `teachsync`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`material`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`material` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`material` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `courseId` BIGINT NULL DEFAULT NULL,
  `materialLink` LONGTEXT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Material_course1_idx` (`courseId` ASC) VISIBLE,
  CONSTRAINT `fk_Material_course1`
    FOREIGN KEY (`courseId`)
    REFERENCES `teachsync`.`course` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`news`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`news` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`news` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `newsTitle` VARCHAR(45) NOT NULL,
  `newsDesc` LONGTEXT NULL DEFAULT NULL,
  `newsLink` LONGTEXT NOT NULL,
  `authorId` BIGINT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_news_user1_idx` (`authorId` ASC) VISIBLE,
  CONSTRAINT `fk_news_user1`
    FOREIGN KEY (`authorId`)
    REFERENCES `teachsync`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`price_log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`price_log` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`price_log` (
  `id` BIGINT NOT NULL,
  `courseId` BIGINT NOT NULL,
  `price` DOUBLE NOT NULL,
  `isCurrent` BIT(1) NULL DEFAULT NULL,
  `isPromotion` BIT(1) NULL DEFAULT NULL,
  `promotionType` VARCHAR(45) NULL DEFAULT NULL,
  `promotionAmount` DOUBLE NULL DEFAULT NULL,
  `promotionDesc` LONGTEXT NULL DEFAULT NULL,
  `validFrom` DATETIME NULL DEFAULT NULL,
  `validTo` DATETIME NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_promotion_course1_idx` (`courseId` ASC) VISIBLE,
  CONSTRAINT `fk_promotion_course1`
    FOREIGN KEY (`courseId`)
    REFERENCES `teachsync`.`course` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`request` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`request` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `requesterId` BIGINT NOT NULL,
  `requestName` VARCHAR(45) NOT NULL,
  `requestType` VARCHAR(45) NOT NULL,
  `requestDesc` LONGTEXT NULL DEFAULT NULL,
  `resolverId` BIGINT NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_request_user1_idx` (`requesterId` ASC) VISIBLE,
  INDEX `fk_request_user2_idx` (`resolverId` ASC) VISIBLE,
  CONSTRAINT `fk_request_user1_requester`
    FOREIGN KEY (`requesterId`)
    REFERENCES `teachsync`.`user` (`id`),
  CONSTRAINT `fk_request_user2_resolver`
    FOREIGN KEY (`resolverId`)
    REFERENCES `teachsync`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`schedule` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`schedule` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `classId` BIGINT NOT NULL,
  `date` DATE NOT NULL,
  `slot` INT NOT NULL,
  `scheduleDesc` LONGTEXT NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_schedule_class1_idx` (`classId` ASC) VISIBLE,
  CONSTRAINT `fk_schedule_class1`
    FOREIGN KEY (`classId`)
    REFERENCES `teachsync`.`classroom` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`schedule_user_pair`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`schedule_user_pair` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`schedule_user_pair` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `scheduleId` BIGINT NOT NULL,
  `userId` BIGINT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_schedule_user_pair_user1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_schedule_user_pair_schedule1_idx` (`scheduleId` ASC) VISIBLE,
  CONSTRAINT `fk_schedule_user_pair_schedule1`
    FOREIGN KEY (`scheduleId`)
    REFERENCES `teachsync`.`schedule` (`id`),
  CONSTRAINT `fk_schedule_user_pair_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `teachsync`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`test`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`test` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`test` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `classId` BIGINT NOT NULL,
  `testName` VARCHAR(45) NOT NULL,
  `testDesc` LONGTEXT NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_test_class1_idx` (`classId` ASC) VISIBLE,
  CONSTRAINT `fk_test_class1`
    FOREIGN KEY (`classId`)
    REFERENCES `teachsync`.`classroom` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`test_user_pair`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`test_user_pair` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`test_user_pair` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `testId` BIGINT NOT NULL,
  `userId` BIGINT NOT NULL,
  `score` FLOAT NULL DEFAULT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_test_user_pair_user1_idx` (`userId` ASC) VISIBLE,
  INDEX `fk_test_user_pair_test1_idx` (`testId` ASC) VISIBLE,
  CONSTRAINT `fk_test_user_pair_test1`
    FOREIGN KEY (`testId`)
    REFERENCES `teachsync`.`test` (`id`),
  CONSTRAINT `fk_test_user_pair_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `teachsync`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`user_class_pair`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`user_class_pair` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`user_class_pair` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `userId` BIGINT NOT NULL,
  `classId` BIGINT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_has_class_class1_idx` (`classId` ASC) VISIBLE,
  INDEX `fk_user_has_class_user1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_user_class_pair_class1`
    FOREIGN KEY (`classId`)
    REFERENCES `teachsync`.`classroom` (`id`),
  CONSTRAINT `fk_user_class_pair_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `teachsync`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `teachsync`.`user_course_pair`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teachsync`.`user_course_pair` ;

CREATE TABLE IF NOT EXISTS `teachsync`.`user_course_pair` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `userId` BIGINT NOT NULL,
  `courseId` BIGINT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_has_course_course1_idx` (`courseId` ASC) VISIBLE,
  INDEX `fk_user_has_course_user1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_user_course_pair_course1`
    FOREIGN KEY (`courseId`)
    REFERENCES `teachsync`.`course` (`id`),
  CONSTRAINT `fk_user_course_pair_user1`
    FOREIGN KEY (`userId`)
    REFERENCES `teachsync`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
