package Dao;

import Modele.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public interface UtilisateurDAO {
    ArrayList<Utilisateur> getAll(); // Récupérer tous les utilisateurs

    void ajouter(Utilisateur utilisateur);

    //void supprimer(Utilisateur utilisateur);

    //Utilisateur modifier(Utilisateur utilisateur);

    Utilisateur chercher(int id);
 }
