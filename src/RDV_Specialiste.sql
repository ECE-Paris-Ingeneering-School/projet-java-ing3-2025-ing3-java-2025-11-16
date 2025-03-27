-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : jeu. 27 mars 2025 à 08:35
-- Version du serveur : 5.7.39
-- Version de PHP : 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `RDV_Specialiste`
--

-- --------------------------------------------------------

--
-- Structure de la table `EDT`
--

CREATE TABLE `EDT` (
  `IDSpecialiste` int(11) NOT NULL,
  `Horaire_debut` int(11) NOT NULL,
  `Horaire_fin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Horaire`
--

CREATE TABLE `Horaire` (
  `Jour` int(11) NOT NULL,
  `Mois` int(11) NOT NULL,
  `Année` int(11) NOT NULL,
  `Heure` int(11) NOT NULL,
  `Minute` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Patient`
--

CREATE TABLE `Patient` (
  `IDpatient` int(11) NOT NULL,
  `TypePatient` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Patient`
--

INSERT INTO `Patient` (`IDpatient`, `TypePatient`) VALUES
(2, 0),
(3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `RDV`
--

CREATE TABLE `RDV` (
  `IDSpecialliste` int(11) NOT NULL,
  `IDPatient` int(11) NOT NULL,
  `Notes` varchar(255) NOT NULL,
  `Horaire` int(11) NOT NULL,
  `Lieu` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Specialiste`
--

CREATE TABLE `Specialiste` (
  `IDSpecialiste` int(11) NOT NULL,
  `Specialisation` varchar(255) NOT NULL,
  `Lieu` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Specialiste`
--

INSERT INTO `Specialiste` (`IDSpecialiste`, `Specialisation`, `Lieu`) VALUES
(1, 'Cardiologue', 'Paris'),
(1, 'Cardiologue', 'Paris'),
(2, 'Generaliste', 'Lyon'),
(2, 'Generaliste', 'Lyon');

-- --------------------------------------------------------

--
-- Structure de la table `Utilisateur`
--

CREATE TABLE `Utilisateur` (
  `IDUtilisateur` int(11) NOT NULL,
  `mdp` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prénom` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Utilisateur`
--

INSERT INTO `Utilisateur` (`IDUtilisateur`, `mdp`, `nom`, `prénom`, `Email`) VALUES
(1, 'password', 'Dupont', 'Martin', ''),
(2, 'passsword2', 'Lefevre', 'Sophie', ''),
(2, 'passsword2', 'Lefevre', 'Sophie', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
