-- 🌐 Suppression et création de la base
DROP DATABASE IF EXISTS `rdv_specialiste`;
CREATE DATABASE `rdv_specialiste` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `rdv_specialiste`;

-- 👤 Table des utilisateurs
CREATE TABLE `utilisateur` (
                               `ID` INT(11) NOT NULL AUTO_INCREMENT,
                               `nom` VARCHAR(255) NOT NULL,
                               `prenom` VARCHAR(255) NOT NULL,
                               `email` VARCHAR(255) NOT NULL UNIQUE,
                               `mdp` VARCHAR(255) NOT NULL,
                               PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des admins
CREATE TABLE `admin` (
                         `ID` INT(11) NOT NULL,
                         PRIMARY KEY (`ID`),
                         CONSTRAINT `fk_admin_utilisateur` FOREIGN KEY (`ID`) REFERENCES `utilisateur`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 👶 Table des patients
CREATE TABLE `patient` (
                           `ID` INT(11) NOT NULL,
                           `type` INT(11) NOT NULL,
                           PRIMARY KEY (`ID`),
                           CONSTRAINT `fk_patient_utilisateur` FOREIGN KEY (`ID`) REFERENCES `utilisateur`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 🩺 Table des spécialistes
CREATE TABLE `specialiste` (
                               `ID` INT(11) NOT NULL,
                               `Specialisation` VARCHAR(255) NOT NULL,
                               `Lieu` VARCHAR(255) NOT NULL,
                               `Photo` VARCHAR(255) NULL,
                               PRIMARY KEY (`ID`),
                               CONSTRAINT `fk_specialiste_utilisateur` FOREIGN KEY (`ID`) REFERENCES `utilisateur`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 🕒 Table des horaires
CREATE TABLE `horaire` (
                           `ID` INT(11) NOT NULL AUTO_INCREMENT,
                           `jourSemaine` TINYINT NOT NULL CHECK (jourSemaine BETWEEN 1 AND 7),
                           `HeureDebut` TIME NOT NULL,
                           `HeureFin` TIME NOT NULL,
                           PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 📅 Table des rendez-vous
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

-- 📆 Table des disponibilités
CREATE TABLE `edt` (
                       `ID` INT(11) NOT NULL AUTO_INCREMENT,
                       `IDSpecialiste` INT(11) NOT NULL,
                       `IDHoraire` INT(11) NOT NULL,
                       PRIMARY KEY (`ID`),
                       CONSTRAINT `fk_edt_specialiste` FOREIGN KEY (`IDSpecialiste`) REFERENCES `specialiste`(`ID`) ON DELETE CASCADE,
                       CONSTRAINT `fk_edt_horaire` FOREIGN KEY (`IDHoraire`) REFERENCES `horaire`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 👨‍⚕️ Insertion des spécialistes
INSERT INTO utilisateur (nom, prenom, email, mdp) VALUES
                                                      ('Dupont', 'Jean', 'jean.dupont@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Martin', 'Claire', 'claire.martin@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Nguyen', 'Linh', 'linh.nguyen@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK');

INSERT INTO specialiste (ID, Specialisation, Lieu) VALUES
                                                       (1, 'Cardiologue', 'Paris'),
                                                       (2, 'Dermatologue', 'Lyon'),
                                                       (3, 'Psychologue', 'Marseille');

-- 👨‍👩‍👧 Insertion des patients
INSERT INTO utilisateur (nom, prenom, email, mdp) VALUES
                                                      ('Bernard', 'Lucie', 'lucie.bernard@gmail.com', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Petit', 'Marc', 'marc.petit@gmail.com', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Lemoine', 'Sophie', 'sophie.lemoine@gmail.com', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK');

INSERT INTO patient (ID, type) VALUES
                                   (4, 1),
                                   (5, 1),
                                   (6, 1);

-- 👮 Insertion des admins
INSERT INTO utilisateur (nom, prenom, email, mdp) VALUES
                                                      ('Bernard', 'Thomas', 'thomas.bernard@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Fabre', 'Emma', 'emma.fabre@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Morel', 'Luc', 'luc.morel@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK');

INSERT INTO admin (ID) VALUES
                           (7),
                           (8),
                           (9);

-- 👨‍⚕️ Nouveaux spécialistes
INSERT INTO utilisateur (nom, prenom, email, mdp) VALUES
                                                      ('Moreau', 'Julie', 'julie.moreau@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Garcia', 'Paul', 'paul.garcia@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Fernandez', 'Lucia', 'lucia.fernandez@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Thomas', 'Élise', 'elise.thomas@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Perrot', 'Hugo', 'hugo.perrot@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK'),
                                                      ('Carvalho', 'Manon', 'manon.carvalho@medilink.fr', '$2a$10$6Z/h/fmyxrRRFMUKKnNQJe5e5ZXKJxHqB8mhzXITq5TZoDJDRDkoK');

INSERT INTO specialiste (ID, Specialisation, Lieu) VALUES
                                                       (10, 'Cardiologue', 'Paris'),
                                                       (11, 'Gynécologue', 'Lyon'),
                                                       (12, 'Cardiologue', 'Bordeaux'),
                                                       (13, 'Gynécologue', 'Toulouse'),
                                                       (14, 'Dermatologue', 'Nice'),
                                                       (15, 'Cardiologue', 'Marseille');
