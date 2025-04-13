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
      ('Dupont', 'Jean', 'jean.dupont@medilink.fr', 'pass123'),
      ('Martin', 'Claire', 'claire.martin@medilink.fr', 'pass123'),
      ('Nguyen', 'Linh', 'linh.nguyen@medilink.fr', 'pass123');

INSERT INTO specialiste (ID, Specialisation, Lieu) VALUES
       (1, 'Cardiologue', 'Paris'),
       (2, 'Dermatologue', 'Lyon'),
       (3, 'Psychologue', 'Marseille');

-- 👨‍👩‍👧 Insertion des patients
INSERT INTO utilisateur (nom, prenom, email, mdp) VALUES
        ('Bernard', 'Lucie', 'lucie.bernard@gmail.com', 'pass123'),
        ('Petit', 'Marc', 'marc.petit@gmail.com', 'pass123'),
        ('Lemoine', 'Sophie', 'sophie.lemoine@gmail.com', 'pass123');

INSERT INTO patient (ID, type) VALUES
       (4, 1),
       (5, 1),
       (6, 1);

-- 🕐 Insertion des horaires (1: lundi à 7 : dimanche, matin et après-midi)
INSERT INTO horaire (jourSemaine, HeureDebut, HeureFin) VALUES
        (2, '09:00:00', '10:00:00'), -- ID 1
        (2, '10:00:00', '11:00:00'), -- ID 2
        (2, '14:00:00', '15:00:00'), -- ID 3
        (3, '09:00:00', '10:00:00'), -- ID 4
        (3, '10:00:00', '11:00:00'), -- ID 5
        (4, '14:00:00', '15:00:00'), -- ID 6
        (5, '09:00:00', '10:00:00'), -- ID 7
        (6, '14:00:00', '15:00:00'); -- ID 8

-- 📘 Emplois du temps des spécialistes
-- Dr Dupont (Cardiologue)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (1, 1),
       (1, 2),
       (1, 4),
       (1, 5);

-- Dr Martin (Dermato)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (2, 4),
       (2, 5),
       (2, 7);
-- Dr Nguyen (Psy)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       (3, 6),
       (3, 8);

-- 📆 Rendez-vous logiques
INSERT INTO rdv (IDSpecialiste, IDPatient, IDHoraire, Date, Notes, Lieu) VALUES
     (1, 4, 1, '2025-04-14', 'Suivi tension artérielle', 'Paris'),       -- Lundi matin
     (2, 5, 5, '2025-04-15', 'Consultation pour eczéma', 'Lyon'),        -- Mardi matin
     (3, 6, 8, '2025-04-18', 'Consultation stress', 'Marseille');        -- Vendredi après-midi
