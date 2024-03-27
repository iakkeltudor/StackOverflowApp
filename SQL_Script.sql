-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema stack_overflow
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema stack_overflow
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `stack_overflow` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `stack_overflow` ;

-- -----------------------------------------------------
-- Table `stack_overflow`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stack_overflow`.`user` (
  `U_ID` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` ENUM('USER', 'ADMIN') NULL DEFAULT NULL,
  PRIMARY KEY (`U_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 103
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `stack_overflow`.`question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stack_overflow`.`question` (
  `Q_ID` INT NOT NULL AUTO_INCREMENT,
  `author_id` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `text` VARCHAR(300) NOT NULL,
  `creation_date` VARCHAR(45) NOT NULL,
  `creation_time` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Q_ID`),
  INDEX `F_A_ID_idx` (`author_id` ASC) VISIBLE,
  CONSTRAINT `F_A_ID`
    FOREIGN KEY (`author_id`)
    REFERENCES `stack_overflow`.`user` (`U_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `stack_overflow`.`answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stack_overflow`.`answer` (
  `A_ID` INT NOT NULL AUTO_INCREMENT,
  `author` VARCHAR(45) NOT NULL,
  `text` VARCHAR(300) NOT NULL,
  `creation_date` VARCHAR(45) NOT NULL,
  `creation_time` VARCHAR(45) NOT NULL,
  `question_id` INT NOT NULL,
  PRIMARY KEY (`A_ID`),
  INDEX `FF_Q_ID_idx` (`question_id` ASC) VISIBLE,
  CONSTRAINT `FF_Q_ID`
    FOREIGN KEY (`question_id`)
    REFERENCES `stack_overflow`.`question` (`Q_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `stack_overflow`.`au`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stack_overflow`.`au` (
  `FFF_U_ID` INT NOT NULL,
  `FFF_A_ID` INT NOT NULL,
  PRIMARY KEY (`FFF_U_ID`, `FFF_A_ID`),
  INDEX `FFF_A_ID_idx` (`FFF_A_ID` ASC) VISIBLE,
  CONSTRAINT `FFF_A_ID`
    FOREIGN KEY (`FFF_A_ID`)
    REFERENCES `stack_overflow`.`answer` (`A_ID`),
  CONSTRAINT `FFF_U_ID`
    FOREIGN KEY (`FFF_U_ID`)
    REFERENCES `stack_overflow`.`user` (`U_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `stack_overflow`.`uq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stack_overflow`.`uq` (
  `F_U_ID` INT NOT NULL,
  `F_Q_ID` INT NOT NULL,
  PRIMARY KEY (`F_U_ID`, `F_Q_ID`),
  INDEX `F_Q_ID_idx` (`F_Q_ID` ASC) VISIBLE,
  CONSTRAINT `F_Q_ID`
    FOREIGN KEY (`F_Q_ID`)
    REFERENCES `stack_overflow`.`question` (`Q_ID`),
  CONSTRAINT `F_U_ID`
    FOREIGN KEY (`F_U_ID`)
    REFERENCES `stack_overflow`.`user` (`U_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `stack_overflow`.`user_seq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `stack_overflow`.`user_seq` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
