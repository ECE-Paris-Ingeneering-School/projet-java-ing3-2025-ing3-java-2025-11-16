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
              ('Dupont', 'Jean', 'jean.dupont@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
              ('Martin', 'Claire', 'claire.martin@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
              ('Nguyen', 'Linh', 'linh.nguyen@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC');

INSERT INTO specialiste (ID, Specialisation, Lieu) VALUES
               (1, 'Cardiologue', 'Paris'),
               (2, 'Dermatologue', 'Lyon'),
               (3, 'Psychologue', 'Marseille');

-- 👨‍👩‍👧 Insertion des patients
INSERT INTO utilisateur (nom, prenom, email, mdp) VALUES
              ('Bernard', 'Lucie', 'lucie.bernard@gmail.com', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
              ('Petit', 'Marc', 'marc.petit@gmail.com', '$$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
              ('Lemoine', 'Sophie', 'sophie.lemoine@gmail.com', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC');

INSERT INTO patient (ID, type) VALUES
               (4, 1),
               (5, 1),
               (6, 1);

-- 👮 Insertion des admins
INSERT INTO utilisateur (nom, prenom, email, mdp) VALUES
              ('Bernard', 'Thomas', 'thomas.bernard@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
              ('Fabre', 'Emma', 'emma.fabre@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
              ('Morel', 'Luc', 'luc.morel@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC');

INSERT INTO admin (ID) VALUES
                   (7),
                   (8),
                   (9);

-- 👨‍⚕️ Nouveaux spécialistes
INSERT INTO utilisateur (nom, prenom, email, mdp) VALUES
                  ('Moreau', 'Julie', 'julie.moreau@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
                  ('Garcia', 'Paul', 'paul.garcia@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
                  ('Fernandez', 'Lucia', 'lucia.fernandez@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
                  ('Thomas', 'Élise', 'elise.thomas@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
                  ('Perrot', 'Hugo', 'hugo.perrot@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC'),
                  ('Carvalho', 'Manon', 'manon.carvalho@medilink.fr', '$2a$12$WXhZ2SgV4qAKrq1om6BLVOiAt1hbYBev8CpPq90e3iU3j.BcOl0OC');

INSERT INTO specialiste (ID, Specialisation, Lieu) VALUES
                   (10, 'Cardiologue', 'Paris'),
                   (11, 'Gynécologue', 'Lyon'),
                   (12, 'Cardiologue', 'Bordeaux'),
                   (13, 'Gynécologue', 'Toulouse'),
                   (14, 'Dermatologue', 'Nice'),
                   (15, 'Cardiologue', 'Marseille');

INSERT INTO horaire(ID, jourSemaine, HeureDebut, HeureFin) VALUES
        -- Lundi
        (1, 1, '08:00:00', '09:00:00'),
        (2, 1, '09:00:00', '10:00:00'),
        (3, 1, '10:00:00', '11:00:00'),
        (4, 1, '11:00:00', '12:00:00'),
        (5, 1, '13:00:00', '14:00:00'),
        (6, 1, '14:00:00', '15:00:00'),
        (7, 1, '15:00:00', '16:00:00'),
        (8, 1, '16:00:00', '17:00:00'),

        -- Mardi
        (9, 2, '08:00:00', '09:00:00'),
        (10, 2, '09:00:00', '10:00:00'),
        (11, 2, '10:00:00', '11:00:00'),
        (12, 2, '11:00:00', '12:00:00'),
        (13, 2, '13:00:00', '14:00:00'),
        (14, 2, '14:00:00', '15:00:00'),
        (15, 2, '15:00:00', '16:00:00'),
        (16, 2, '16:00:00', '17:00:00'),

        -- Mercredi
        (17, 3, '08:00:00', '09:00:00'),
        (18, 3, '09:00:00', '10:00:00'),
        (19, 3, '10:00:00', '11:00:00'),
        (20, 3, '11:00:00', '12:00:00'),
        (21, 3, '13:00:00', '14:00:00'),
        (22, 3, '14:00:00', '15:00:00'),
        (23, 3, '15:00:00', '16:00:00'),
        (24, 3, '16:00:00', '17:00:00'),

        -- Jeudi
        (25, 4, '08:00:00', '09:00:00'),
        (26, 4, '09:00:00', '10:00:00'),
        (27, 4, '10:00:00', '11:00:00'),
        (28, 4, '11:00:00', '12:00:00'),
        (29, 4, '13:00:00', '14:00:00'),
        (30, 4, '14:00:00', '15:00:00'),
        (31, 4, '15:00:00', '16:00:00'),
        (32, 4, '16:00:00', '17:00:00'),

        -- Vendredi
        (33, 5, '08:00:00', '09:00:00'),
        (34, 5, '09:00:00', '10:00:00'),
        (35, 5, '10:00:00', '11:00:00'),
        (36, 5, '11:00:00', '12:00:00'),
        (37, 5, '13:00:00', '14:00:00'),
        (38, 5, '14:00:00', '15:00:00'),
        (39, 5, '15:00:00', '16:00:00'),
        (40, 5, '16:00:00', '17:00:00'),

        -- Samedi
        (41, 6, '08:00:00', '09:00:00'),
        (42, 6, '09:00:00', '10:00:00'),
        (43, 6, '10:00:00', '11:00:00'),
        (44, 6, '11:00:00', '12:00:00'),
        (45, 6, '13:00:00', '14:00:00'),
        (46, 6, '14:00:00', '15:00:00'),
        (47, 6, '15:00:00', '16:00:00'),
        (48, 6, '16:00:00', '17:00:00');


-- 🗓️ Disponibilités réduites pour tous les spécialistes (4 créneaux chacun)

-- Jean Dupont (Cardiologue, Paris)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (1, 1), (1, 3), (1, 17), (1, 18);

-- Claire Martin (Dermatologue, Lyon)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (2, 13), (2, 14), (2, 33), (2, 34);

-- Linh Nguyen (Psychologue, Marseille)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (3, 25), (3, 27), (3, 30), (3, 31);

-- Julie Moreau (Cardiologue, Paris)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (10, 2), (10, 4), (10, 22), (10, 23);

-- Paul Garcia (Gynécologue, Lyon)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (11, 9), (11, 11), (11, 13), (11, 15);

-- Lucia Fernandez (Cardiologue, Bordeaux)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (12, 5), (12, 6), (12, 38), (12, 39);

-- Élise Thomas (Gynécologue, Toulouse)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (13, 26), (13, 28), (13, 46), (13, 47);

-- Hugo Perrot (Dermatologue, Nice)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (14, 10), (14, 12), (14, 36), (14, 37);

-- Manon Carvalho (Cardiologue, Marseille)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (15, 19), (15, 20), (15, 41), (15, 42);
