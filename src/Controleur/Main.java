package Controleur;

import Dao.*;
import Modele.Patient;
import Modele.Specialiste;
import Modele.Utilisateur;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");

        // Instanciation du DAO des utilisateurs
        UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl(db);
        // Ajouter un patient
        Patient patient = new Patient("Dupont", "Jean", "jean.dupont@email.com", "mdp123", 1);
        utilisateurDAO.ajouter(patient);
        System.out.println("Patient ajouté avec succès!");

        // Ajouter un spécialiste
        Specialiste specialiste = new Specialiste("Martin", "Sophie", "sophie.martin@email.com", "mdp456", "Cardiologue", "Paris");
        utilisateurDAO.ajouter(specialiste);
        System.out.println("Spécialiste ajouté avec succès!");

        // Récupérer et afficher tous les utilisateurs
        ArrayList<Utilisateur> utilisateurs = utilisateurDAO.getAll();
        for (Utilisateur u : utilisateurs) {
            System.out.println(u);
        }

        // Rechercher un utilisateur par ID
        Utilisateur utilisateurTrouve = utilisateurDAO.chercher(1);
        if (utilisateurTrouve != null) {
            System.out.println("Utilisateur trouvé : " + utilisateurTrouve);
        } else {
            System.out.println("Aucun utilisateur trouvé avec cet ID.");
        }
    }
}

