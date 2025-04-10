-- Création de la base de données
DROP DATABASE IF EXISTS `rdv_specialiste`;
CREATE DATABASE `rdv_specialiste` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `rdv_specialiste`;

-- Table des utilisateurs
CREATE TABLE `utilisateur` (
   `ID` INT(11) NOT NULL AUTO_INCREMENT,
   `nom` VARCHAR(255) NOT NULL,
   `prenom` VARCHAR(255) NOT NULL,
   `email` VARCHAR(255) NOT NULL UNIQUE,
   `mdp` VARCHAR(255) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des patients (hérite de utilisateur)
CREATE TABLE `patient` (
   `ID` INT(11) NOT NULL,
   `type` INT(11) NOT NULL,
   PRIMARY KEY (`ID`),
   CONSTRAINT `fk_patient_utilisateur` FOREIGN KEY (`ID`) REFERENCES `utilisateur`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des spécialistes (hérite de utilisateur)
CREATE TABLE `specialiste` (
   `ID` INT(11) NOT NULL,
   `Specialisation` VARCHAR(255) NOT NULL,
   `Lieu` VARCHAR(255) NOT NULL,
   PRIMARY KEY (`ID`),
   CONSTRAINT `fk_specialiste_utilisateur` FOREIGN KEY (`ID`) REFERENCES `utilisateur`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des horaires (stocke les dates et heures de rendez-vous)
-- Détail des résultats possibles :
-- (1 : Dimanche, 2 : Lundi, 3 : Mardi, 4 : Mercredi, 5 : Jeudi, 6 : Vendredi, 7 : Samedi)
CREATE TABLE `horaire` (
   `ID` INT(11) NOT NULL AUTO_INCREMENT,
   `jourSemaine` TINYINT NOT NULL CHECK (jourSemaine BETWEEN 1 AND 7),
   `HeureDebut` TIME NOT NULL,
   `HeureFin` TIME NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des rendez-vous
CREATE TABLE `rdv` (
   `ID` INT(11) NOT NULL AUTO_INCREMENT,
   `IDSpecialiste` INT(11) NOT NULL,
   `IDPatient` INT(11) NOT NULL,
   `IDHoraire` INT(11) NOT NULL,
   `Date` DATE NULL,
   `Notes` TEXT NULL,
   `Lieu` VARCHAR(255) NOT NULL,
   PRIMARY KEY (`ID`),
   CONSTRAINT `fk_rdv_specialiste` FOREIGN KEY (`IDSpecialiste`) REFERENCES `specialiste`(`ID`) ON DELETE CASCADE,
   CONSTRAINT `fk_rdv_patient` FOREIGN KEY (`IDPatient`) REFERENCES `patient`(`ID`) ON DELETE CASCADE,
   CONSTRAINT `fk_rdv_horaire` FOREIGN KEY (`IDHoraire`) REFERENCES `horaire`(`ID`) ON DELETE CASCADE,
   UNIQUE (`IDSpecialiste`, `Date`, `IDHoraire`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des disponibilités des spécialistes
CREATE TABLE `edt` (
   `ID` INT(11) NOT NULL AUTO_INCREMENT,
   `IDSpecialiste` INT(11) NOT NULL,
   `IDHoraire` INT(11) NOT NULL,
   PRIMARY KEY (`ID`),
   CONSTRAINT `fk_edt_specialiste` FOREIGN KEY (`IDSpecialiste`) REFERENCES `specialiste`(`ID`) ON DELETE CASCADE,
   CONSTRAINT `fk_edt_horaire` FOREIGN KEY (`IDHoraire`) REFERENCES `horaire`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
