package Controleur;

import Dao.DatabaseConnection;
import Dao.UtilisateurDAO;
import Dao.UtilisateurDAOImpl;
import Modele.Utilisateur;
import Modele.Patient;
import Modele.Specialiste;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Connexion à la base de données
        DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");

        // Instanciation du DAO des utilisateurs
        UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl(db);

        // Récupération et affichage des utilisateurs existants
        List<Utilisateur> utilisateurs = utilisateurDAO.getAllUtilisateurs();
        System.out.println("Liste des utilisateurs existants :");
        for (Utilisateur u : utilisateurs) {
            System.out.println(u);
        }

        // Ajout d'un nouveau Patient
        Patient patient = new Patient(10, "Dupont", "Jean", "jean.dupont@example.com", "mdp123", 1);
        utilisateurDAO.ajouterUtilisateur(patient);
        System.out.println("Patient ajouté : " + patient);

        // Ajout d'un nouveau Spécialiste
        Specialiste specialiste = new Specialiste(11, "Martin", "Alice", "alice.martin@example.com", "mdp456", "Dentiste", "Marseille");
        utilisateurDAO.ajouterUtilisateur(specialiste);
        System.out.println("Spécialiste ajouté : " + specialiste);

        // Récupération et affichage des utilisateurs après ajout
        utilisateurs = utilisateurDAO.getAllUtilisateurs();
        System.out.println("\nListe mise à jour des utilisateurs :");
        for (Utilisateur u : utilisateurs) {
            System.out.println(u);
        }

        // Fermeture de la connexion à la base de données
        db.disconnect();
    }
}
