package org.chem.Dao;

import org.chem.Modele.Horaire;
import org.chem.Modele.Specialiste;
import org.chem.Modele.Utilisateur;

import java.sql.Time;
import java.util.*;

public interface UtilisateurDAO {
    //ArrayList<Utilisateur> getAll(); // Récupérer tous les utilisateurs

    ArrayList<Specialiste> rechercherSpecialistes(String motCle, String jour, Time heure, String lieu);

    ArrayList<Horaire> chargerHorairesPourSpecialiste(int idSpecialiste);

    Utilisateur seConnecter(String email, String mdp);

    void ajouter(Utilisateur utilisateur);

    //void supprimer(Utilisateur utilisateur);

    //void modifier(Utilisateur utilisateur);

    //Utilisateur chercher(int id);

    //Utilisateur getUtilisateurById(int id);

    //Utilisateur getUtilisateurByEmail(String email);

}
