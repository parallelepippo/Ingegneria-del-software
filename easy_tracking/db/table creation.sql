-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Corriere
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Corriere
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Corriere` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema corriere
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema corriere
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `corriere` DEFAULT CHARACTER SET utf8mb3 ;
USE `Corriere` ;

-- -----------------------------------------------------
-- Table `Corriere`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Corriere`.`Cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `Comune` VARCHAR(45) NOT NULL,
  `CAP` INT NOT NULL,
  `Indirizzo` VARCHAR(255) NOT NULL,
  `Numero` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `codiceFiascale` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `CODFIS_UNIQUE` (`Comune` ASC) VISIBLE,
  UNIQUE INDEX `NumeroTelefono_UNIQUE` (`Numero` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Corriere`.`Corriere`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Corriere`.`Corriere` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `CAP` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Corriere`.`Magazzino`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Corriere`.`Magazzino` (
  `idMagazzino` INT NOT NULL,
  `Comune` VARCHAR(45) NOT NULL,
  `CAP` INT NOT NULL,
  `Indirizzo` VARCHAR(45) NULL,
  `PacchiInGiacenza` INT NULL,
  `CapienzaPacchi` INT NULL,
  PRIMARY KEY (`idMagazzino`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Corriere`.`Spedizione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Corriere`.`Spedizione` (
  `trackingCode` INT NOT NULL,
  `idMittente` INT NOT NULL,
  `idDestinatario` INT NOT NULL,
  `idDeposito` INT NOT NULL,
  `idCorriere` INT NOT NULL,
  `dataSpedizione` DATE NOT NULL,
  `dataConsegna` DATE NULL,
  `Stato` VARCHAR(45) NOT NULL,
  `codiceQR` VARCHAR(45) NULL,
  `pesoPacco` DOUBLE NULL,
  `prezzo` DOUBLE NULL,
  PRIMARY KEY (`trackingCode`, `idMittente`, `idDestinatario`, `idDeposito`, `idCorriere`),
  INDEX `fk_Anagrafica_has_Spedizione_Anagrafica_idx` (`idMittente` ASC) VISIBLE,
  INDEX `fk_Anagrafica_has_Spedizione_Anagrafica1_idx` (`idDestinatario` ASC) VISIBLE,
  INDEX `fk_Spedizione_Corriere1_idx` (`idCorriere` ASC) VISIBLE,
  INDEX `fk_Spedizione_Magazzino1_idx` (`idDeposito` ASC) VISIBLE,
  CONSTRAINT `fk_Anagrafica_has_Spedizione_Anagrafica`
    FOREIGN KEY (`idMittente`)
    REFERENCES `Corriere`.`Cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Anagrafica_has_Spedizione_Anagrafica1`
    FOREIGN KEY (`idDestinatario`)
    REFERENCES `Corriere`.`Cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Spedizione_Corriere1`
    FOREIGN KEY (`idCorriere`)
    REFERENCES `Corriere`.`Corriere` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Spedizione_Magazzino1`
    FOREIGN KEY (`idDeposito`)
    REFERENCES `Corriere`.`Magazzino` (`idMagazzino`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `corriere` ;

-- -----------------------------------------------------
-- Table `corriere`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `corriere`.`cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `Comune` VARCHAR(45) NOT NULL,
  `CAP` VARCHAR(12) NOT NULL,
  `numero` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `Indirizzo` VARCHAR(255) NOT NULL,
  `codicefiscale` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `NumeroTelefono_UNIQUE` (`numero` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `CODFIS_UNIQUE` (`codicefiscale` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `corriere`.`corriere`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `corriere`.`corriere` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `CAP` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `corriere`.`magazzino`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `corriere`.`magazzino` (
  `idMagazzino` INT NOT NULL,
  `Comune` VARCHAR(45) NOT NULL,
  `CAP` INT NOT NULL,
  `Indirizzo` VARCHAR(45) NULL DEFAULT NULL,
  `PacchiInGiacenza` INT NULL DEFAULT NULL,
  `CapienzaPacchi` INT NULL DEFAULT NULL,
  `Magazzinocol` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idMagazzino`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `corriere`.`spedizione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `corriere`.`spedizione` (
  `trackingCode` INT NOT NULL,
  `idMittente` INT NOT NULL,
  `idDestinatario` INT NOT NULL,
  `idDeposito` INT NOT NULL,
  `idCorriere` INT NOT NULL,
  `dataSpedizione` DATE NOT NULL,
  `dataConsegna` DATE NULL DEFAULT NULL,
  `Stato` VARCHAR(45) NOT NULL,
  `codiceQR` VARCHAR(45) NULL DEFAULT NULL,
  `pesoPacco` DOUBLE NULL DEFAULT NULL,
  `prezzo` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`trackingCode`, `idMittente`, `idDestinatario`, `idDeposito`, `idCorriere`),
  INDEX `fk_Anagrafica_has_Spedizione_Anagrafica_idx` (`idMittente` ASC) VISIBLE,
  INDEX `fk_Anagrafica_has_Spedizione_Anagrafica1_idx` (`idDestinatario` ASC) VISIBLE,
  INDEX `fk_Spedizione_Corriere1_idx` (`idCorriere` ASC) VISIBLE,
  INDEX `fk_Spedizione_Magazzino1_idx` (`idDeposito` ASC) VISIBLE,
  CONSTRAINT `fk_Anagrafica_has_Spedizione_Anagrafica`
    FOREIGN KEY (`idMittente`)
    REFERENCES `corriere`.`cliente` (`id`),
  CONSTRAINT `fk_Anagrafica_has_Spedizione_Anagrafica1`
    FOREIGN KEY (`idDestinatario`)
    REFERENCES `corriere`.`cliente` (`id`),
  CONSTRAINT `fk_Spedizione_Corriere1`
    FOREIGN KEY (`idCorriere`)
    REFERENCES `corriere`.`corriere` (`id`),
  CONSTRAINT `fk_Spedizione_Magazzino1`
    FOREIGN KEY (`idDeposito`)
    REFERENCES `corriere`.`magazzino` (`idMagazzino`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
