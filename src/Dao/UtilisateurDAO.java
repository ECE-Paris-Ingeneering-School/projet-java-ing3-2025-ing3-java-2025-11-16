package Dao;

import Modele.Utilisateur;
import java.util.List;

public interface UtilisateurDAO {
    List<Utilisateur> getAllUtilisateurs(); // Récupérer tous les utilisateurs

    Utilisateur getUtilisateurById(int id); // Récupérer un utilisateur par son ID

    void ajouterUtilisateur(Utilisateur utilisateur); // Ajouter un utilisateur

    void supprimerUtilisateur(int id); // Supprimer un utilisateur
}
