package Controleur;

import Modele.Utilisateur;
import Modele.Patient;
import Modele.Specialiste;
import Dao.UtilisateurDAO;
import Dao.UtilisateurDAOImpl;
import Dao.DatabaseConnection;

import java.util.List;

public class UtilisateursController {
    private UtilisateurDAO utilisateurDAO;

    public UtilisateursController() {
        DatabaseConnection db = DatabaseConnection.getInstance("RDV_Specialiste", "root", "root");
        this.utilisateurDAO = new UtilisateurDAOImpl(db);

    }

    // Ajouter un utilisateur (patient ou spécialiste)
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.ajouterUtilisateur(utilisateur);
    }

    // Récupérer un utilisateur par son ID
    public Utilisateur getUtilisateur(int id) {
        return utilisateurDAO.getUtilisateurById(id);
    }

    // Récupérer tous les utilisateurs
    public List<Utilisateur> getTousUtilisateurs() {
        return utilisateurDAO.getAllUtilisateurs();
    }

    // Supprimer un utilisateur
    public void supprimerUtilisateur(int id) {
        utilisateurDAO.supprimerUtilisateur(id);
    }
}
