DROP DATABASE IF EXISTS `rdv_specialiste`;
CREATE DATABASE `rdv_specialiste` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `rdv_specialiste`;

CREATE TABLE `utilisateur` (
   `ID` INT(11) NOT NULL AUTO_INCREMENT,
   `nom` VARCHAR(255) NOT NULL,
   `prenom` VARCHAR(255) NOT NULL,
   `email` VARCHAR(255) NOT NULL UNIQUE,
   `mdp` VARCHAR(255) NOT NULL,
   type VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `admin` (
   `ID` INT(11) NOT NULL,
   PRIMARY KEY (`ID`),
    CONSTRAINT `fk_admin_utilisateur` FOREIGN KEY (`ID`) REFERENCES `utilisateur`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `patient` (
   `ID` INT(11) NOT NULL,
   `type` INT(11) NOT NULL,
   PRIMARY KEY (`ID`),
   CONSTRAINT `fk_patient_utilisateur` FOREIGN KEY (`ID`) REFERENCES `utilisateur`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `specialiste` (
   `ID` INT(11) NOT NULL,
   `Specialisation` VARCHAR(255) NOT NULL,
   `Lieu` VARCHAR(255) NOT NULL,
   `Photo` MEDIUMBLOB,
   PRIMARY KEY (`ID`),
   CONSTRAINT `fk_specialiste_utilisateur` FOREIGN KEY (`ID`) REFERENCES `utilisateur`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `horaire` (
   `ID` INT(11) NOT NULL AUTO_INCREMENT,
   `jourSemaine` TINYINT NOT NULL CHECK (jourSemaine BETWEEN 1 AND 7),
   `HeureDebut` TIME NOT NULL,
   `HeureFin` TIME NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `edt` (
   `ID` INT(11) NOT NULL AUTO_INCREMENT,
   `IDSpecialiste` INT(11) NOT NULL,
   `IDHoraire` INT(11) NOT NULL,
   PRIMARY KEY (`ID`),
   CONSTRAINT `fk_edt_specialiste` FOREIGN KEY (`IDSpecialiste`) REFERENCES `specialiste`(`ID`) ON DELETE CASCADE,
   CONSTRAINT `fk_edt_horaire` FOREIGN KEY (`IDHoraire`) REFERENCES `horaire`(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO utilisateur (nom, prenom, email, mdp, type) VALUES
      ('Dupont', 'Jean', 'jean.dupont@medilink.fr', '$2a$10$Iw5vO5xBPnqWpyF76rogyO5BFRSp4YAYTLXwjVEYhYgfvP/QxnzBS', 'S'),
      ( 'Martin', 'Claire', 'claire.martin@medilink.fr', '$2a$10$ynmbGL0Fwvan2yEXVcWGHOtOOgkVabg4ebjpSoUGiVRTH41IAko7u','S'),
      ( 'Nguyen', 'Linh', 'linh.nguyen@medilink.fr', '$2a$10$HE2cVqgyEftF4yk0mjx49.pxXaBtorCAb3KfUYKtv8rz.pZaIrQLm','S');


INSERT INTO specialiste (ID, Specialisation, Lieu) VALUES
    ( (SELECT ID FROM utilisateur WHERE email = 'jean.dupont@medilink.fr'),     'Cardiologue','Paris'),
    ( (SELECT ID FROM utilisateur WHERE email = 'claire.martin@medilink.fr'),   'Dermatologue','Lyon'),
    ( (SELECT ID FROM utilisateur WHERE email = 'linh.nguyen@medilink.fr'),     'Psychologue','Marseille');

INSERT INTO utilisateur (nom, prenom, email, mdp, type) VALUES
        ('Bernard', 'Lucie', 'lucie.bernard@gmail.com', '$2a$10$/Wvuc1RD1NWvPgXE5uP8uOsNfeCjOc2Ijg869ttj3HWpv0PFi3XHe', 'P'),
        ('Petit', 'Marc', 'marc.petit@gmail.com', '$2a$10$0tsNILZc6wHnBtAd.e8.2OYfhUb2RrUTlmb8/q6nN8kBhTLB33kb6','P'),
        ('Lemoine', 'Sophie', 'sophie.lemoine@gmail.com', '$2a$10$IvBAeUXo.6xa30fuRcA98eztBNSL6P4rK/vLyw7uDui4IrieeVFRm','P');

INSERT INTO patient (ID, type) VALUES
       ((SELECT ID FROM utilisateur WHERE email = 'lucie.bernard@gmail.com'), 1),
       ((SELECT ID FROM utilisateur WHERE email = 'marc.petit@gmail.com'), 1),
       ((SELECT ID FROM utilisateur WHERE email = 'sophie.lemoine@gmail.com'), 1);

INSERT INTO utilisateur (nom, prenom, email, mdp, type) VALUES
      ('Bernard', 'Thomas', 'thomas.bernard@medilink.fr', '$2a$10$RBEimI067SzkUCZ8Eej7uOAv1Nde0tjjfSHa.iwGQ3bqu7S.T8yP.', 'A'),
      ('Fabre', 'Emma', 'emma.fabre@medilink.fr', '$2a$10$ZEJrqxWCNU30MV.tysDX9.29MdpaNAXCNvrUSabmTcM4sWfoHfg0m', 'A'),
      ('Morel', 'Luc', 'luc.morel@medilink.fr', '$2a$10$3gGkI8t.J27ezCt1H0Fh9O/BOjPxjaitssTSOdOpgt9WNZJ6uNCLi', 'A');

INSERT INTO admin (ID) VALUES
       ((SELECT ID FROM utilisateur WHERE email = 'thomas.bernard@medilink.fr')),
       ((SELECT ID FROM utilisateur WHERE email = 'emma.fabre@medilink.fr')),
       ((SELECT ID FROM utilisateur WHERE email = 'luc.morel@medilink.fr'));

INSERT INTO utilisateur (nom, prenom, email, mdp, type) VALUES
      ('Moreau', 'Julie', 'julie.moreau@medilink.fr', '$2a$10$3hg.WyXlLK978mqKTnOjSuVODIQOAhSo/9NUyNV9Y.rvMUchwRlN2', 'S'),     -- Cardiologue
      ('Garcia', 'Paul', 'paul.garcia@medilink.fr', '$2a$10$cUNA7Yvr2GEV9TDknGNpK.lKwhOBjfovZpjycOAbWK9XODAWoopSm', 'S'),       -- Gynécologue
      ('Fernandez', 'Lucia', 'lucia.fernandez@medilink.fr', '$2a$10$btDQEGWKpDCWXMTSWhDcRej24mY7l6bhQkX39aRwOAjS4iynGgBAm', 'S'), -- Cardiologue
      ('Thomas', 'Élise', 'elise.thomas@medilink.fr', '$2a$10$RxB70KGcc2YitjEtAq8F1.Vphdf22WAzBhwOf7Es2aLiIXd8wz2j2', 'S'),     -- Gynécologue
      ('Perrot', 'Hugo', 'hugo.perrot@medilink.fr', '$2a$10$XTjfw1hf5cd6.h15Paw6ceM6B5j/ZxcGnngmo6BzPtq53gytLayR2', 'S'),       -- Dermatologue
      ('Carvalho', 'Manon', 'manon.carvalho@medilink.fr', '$2a$10$sdK1lsXEzkLzEV3nCnfDAuO62u60EHkM/dQkPdDxMnhFiHOqGrf0u', 'S'); -- Cardiologue


INSERT INTO specialiste (ID, Specialisation, Lieu) VALUES
       ((SELECT ID FROM utilisateur WHERE email = 'julie.moreau@medilink.fr'), 'Cardiologue', 'Paris'),
       ((SELECT ID FROM utilisateur WHERE email = 'paul.garcia@medilink.fr'), 'Gynécologue', 'Lyon'),
       ((SELECT ID FROM utilisateur WHERE email = 'lucia.fernandez@medilink.fr'), 'Cardiologue', 'Bordeaux'),
       ((SELECT ID FROM utilisateur WHERE email = 'elise.thomas@medilink.fr'), 'Gynécologue', 'Toulouse'),
       ((SELECT ID FROM utilisateur WHERE email = 'hugo.perrot@medilink.fr'), 'Dermatologue', 'Nice'),
       ((SELECT ID FROM utilisateur WHERE email = 'manon.carvalho@medilink.fr'), 'Cardiologue', 'Marseille');


INSERT INTO horaire (jourSemaine, HeureDebut, HeureFin) VALUES
        (2, '09:00:00', '10:00:00'), -- ID 1
        (2, '10:00:00', '11:00:00'), -- ID 2
        (2, '14:00:00', '15:00:00'), -- ID 3
        (3, '09:00:00', '10:00:00'), -- ID 4
        (3, '10:00:00', '11:00:00'), -- ID 5
        (4, '14:00:00', '15:00:00'), -- ID 6
        (5, '09:00:00', '10:00:00'), -- ID 7
        (6, '14:00:00', '15:00:00'); -- ID 8

INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       ((SELECT ID FROM utilisateur WHERE email = 'jean.dupont@medilink.fr'),  1),
       ((SELECT ID FROM utilisateur WHERE email = 'jean.dupont@medilink.fr'), 2),
       ((SELECT ID FROM utilisateur WHERE email = 'jean.dupont@medilink.fr'), 4),
       ((SELECT ID FROM utilisateur WHERE email = 'jean.dupont@medilink.fr'),  5);

-- Dr Martin (Dermato)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       ((SELECT ID FROM utilisateur WHERE email = 'claire.martin@medilink.fr'), 4),
       ((SELECT ID FROM utilisateur WHERE email = 'claire.martin@medilink.fr'), 5),
       ((SELECT ID FROM utilisateur WHERE email = 'claire.martin@medilink.fr'), 7);

-- Dr Nguyen (Psy)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       ((SELECT ID FROM utilisateur WHERE email = 'linh.nguyen@medilink.fr'), 6),
       ((SELECT ID FROM utilisateur WHERE email = 'linh.nguyen@medilink.fr'), 8);

-- Julie Moreau (ID 10 - Cardiologue à Paris)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
       ((SELECT ID FROM utilisateur WHERE email = 'julie.moreau@medilink.fr'), 1), -- Lundi 9h
       ((SELECT ID FROM utilisateur WHERE email = 'julie.moreau@medilink.fr'), 2), -- Lundi 10h
       ((SELECT ID FROM utilisateur WHERE email = 'julie.moreau@medilink.fr'), 3); -- Lundi 14h

-- Paul Garcia (ID 11 - Gynécologue à Lyon)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
        ((SELECT ID FROM utilisateur WHERE email = 'paul.garcia@medilink.fr'), 4), -- Mardi 9h
        ((SELECT ID FROM utilisateur WHERE email = 'paul.garcia@medilink.fr'), 5); -- Mardi 10h

-- Lucia Fernandez (ID 12 - Cardiologue à Bordeaux)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
        ((SELECT ID FROM utilisateur WHERE email = 'lucia.fernandez@medilink.fr'), 6), -- Jeudi 14h
        ((SELECT ID FROM utilisateur WHERE email = 'lucia.fernandez@medilink.fr'), 7); -- Vendredi 9h

-- Élise Thomas (ID 13 - Gynécologue à Toulouse)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
        ((SELECT ID FROM utilisateur WHERE email = 'elise.thomas@medilink.fr'), 5), -- Mardi 10h
        ((SELECT ID FROM utilisateur WHERE email = 'elise.thomas@medilink.fr'), 8); -- Samedi 14h

-- Hugo Perrot (ID 14 - Dermatologue à Nice)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
        ((SELECT ID FROM utilisateur WHERE email = 'hugo.perrot@medilink.fr'), 3), -- Lundi 14h
        ((SELECT ID FROM utilisateur WHERE email = 'hugo.perrot@medilink.fr'), 6); -- Jeudi 14h

-- Manon Carvalho (ID 15 - Cardiologue à Marseille)
INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES
        ((SELECT ID FROM utilisateur WHERE email = 'manon.carvalho@medilink.fr'), 1), -- Lundi 9h
        ((SELECT ID FROM utilisateur WHERE email = 'manon.carvalho@medilink.fr'), 4), -- Mardi 9h
        ((SELECT ID FROM utilisateur WHERE email = 'manon.carvalho@medilink.fr'), 7); -- Vendredi 9h


INSERT INTO rdv (IDSpecialiste, IDPatient, IDHoraire, Date, Notes, Lieu) VALUES
     ((SELECT ID FROM utilisateur WHERE email = 'jean.dupont@medilink.fr'), (SELECT ID FROM utilisateur WHERE email = 'lucie.bernard@gmail.com'), 1, '2025-04-14', 'Suivi tension artérielle', 'Paris'),       -- Lundi matin
     ((SELECT ID FROM utilisateur WHERE email = 'claire.martin@medilink.fr'), (SELECT ID FROM utilisateur WHERE email = 'marc.petit@gmail.com'), 5, '2025-04-15', 'Consultation pour eczéma', 'Lyon'),        -- Mardi matin
     ((SELECT ID FROM utilisateur WHERE email = 'linh.nguyen@medilink.fr'), (SELECT ID FROM utilisateur WHERE email = 'sophie.lemoine@gmail.com'), 8, '2025-04-18', 'Consultation stress', 'Marseille');        -- Vendredi après-midi

