package Dao;

import Modele.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public interface UtilisateurDAO {
    ArrayList<Utilisateur> getAll(); // Récupérer tous les utilisateurs

    Utilisateur seConnecter(String email, String mdp, String type);

    void ajouter(Utilisateur utilisateur);

    void supprimer(Utilisateur utilisateur);

    void modifier(Utilisateur utilisateur);

    Utilisateur chercher(int id);

    Utilisateur getUtilisateurById(int id);

    Utilisateur getUtilisateurByEmail(String email);

}
