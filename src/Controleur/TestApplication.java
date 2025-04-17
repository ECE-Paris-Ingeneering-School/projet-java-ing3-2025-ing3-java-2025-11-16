package Controleur;

import Dao.*;
import Modele.*;

import java.sql.Time;
import java.util.*;

public class TestApplication {

    /*private static final Scanner scanner = new Scanner(System.in);
    private static final DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");
    private static final UtilisateurDAO utilisateurDAO = db.getUtilisateurDAO();
    private static final HoraireDAO horaireDAO = db.getHoraireDAO();
    private static final EdtDAO edtDAO = db.getEdtDAO();
    private static final RendezVousDAO rendezVousDAO = db.getRendezVousDAO();

    public static void lancerMenu() {
        int choix;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Ajouter un utilisateur");
            System.out.println("2. Ajouter un horaire");
            System.out.println("3. Ajouter un horaire à l'emploi du temps d’un spécialiste");
            System.out.println("4. Ajouter un rendez-vous");
            System.out.println("5. Afficher les rendez-vous d’un patient");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // vider le buffer

            switch (choix) {
                case 1 -> ajouterUtilisateur();
                case 2 -> ajouterHoraire();
                case 3 -> ajouterEdt();
                case 4 -> ajouterRendezVous();
                case 5 -> afficherRendezVousPatient();
                case 0 -> System.out.println("👋 Fin du programme.");
                default -> System.out.println("❌ Choix invalide.");
            }
        } while (choix != 0);

        db.disconnect();
    }

    private static void ajouterUtilisateur() {
        System.out.print("Type d'utilisateur (1=Patient, 2=Spécialiste) : ");
        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();

        if (type == 1) {
            System.out.print("Type de patient (0 = nouveau, 1 = ancien) : ");
            int typePatient = scanner.nextInt();
            scanner.nextLine();

            Patient patient = new Patient(nom, prenom, email, mdp, typePatient);
            utilisateurDAO.ajouter(patient);
        }
        else if (type == 2) {
            System.out.print("Spécialité : ");
            String specialite = scanner.nextLine();
            System.out.print("Lieu d’exercice : ");
            String lieu = scanner.nextLine();
            Specialiste specialiste = new Specialiste(nom, prenom, email, mdp, specialite, lieu);
            utilisateurDAO.ajouter(specialiste);
        } else {
            System.out.println("❌ Type invalide.");
        }
    }

    private static void ajouterHoraire() {
        System.out.print("Jour (1=Lundi à 7=Dimanche) : ");
        int jour = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Heure début (HH:MM:SS) : ");
        String hDebut = scanner.nextLine();
        System.out.print("Heure fin (HH:MM:SS) : ");
        String hFin = scanner.nextLine();

        Horaire horaire = new Horaire(jour, Time.valueOf(hDebut), Time.valueOf(hFin));
        horaireDAO.ajouterHoraire(horaire);
    }

    private static void ajouterEdt() {
        System.out.print("Email du spécialiste : ");
        String email = scanner.nextLine();
        Utilisateur specialiste = utilisateurDAO.getUtilisateurByEmail(email);
        if (specialiste == null) {
            System.out.println("❌ Spécialiste introuvable.");
            return;
        }

        System.out.print("ID de l'horaire à ajouter : ");
        int idHoraire = scanner.nextInt();
        scanner.nextLine();

        Edt edt = new Edt(specialiste.getId(), idHoraire);
        edtDAO.ajouterEdt(edt);
        System.out.println("✅ Horaire ajouté au planning.");
    }

    private static void ajouterRendezVous() {
        System.out.print("Email du patient : ");
        String emailPatient = scanner.nextLine();
        Utilisateur patient = utilisateurDAO.getUtilisateurByEmail(emailPatient);

        System.out.print("Email du spécialiste : ");
        String emailSpecialiste = scanner.nextLine();
        Utilisateur specialiste = utilisateurDAO.getUtilisateurByEmail(emailSpecialiste);

        if (patient == null || specialiste == null) {
            System.out.println("❌ Patient ou spécialiste non trouvé.");
            return;
        }

        System.out.print("ID de l’horaire : ");
        int idHoraire = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Notes du RDV : ");
        String notes = scanner.nextLine();
        System.out.print("Lieu du RDV : ");
        String lieu = scanner.nextLine();

        Date date = new Date(); // maintenant
        RendezVous rdv = new RendezVous(specialiste.getId(), patient.getId(), idHoraire, date, notes, lieu);
        boolean added = rendezVousDAO.ajouterRendezVous(rdv);
        System.out.println(added ? "✅ RDV ajouté !" : "❌ Échec ajout RDV.");
    }

    private static void afficherRendezVousPatient() {
        System.out.print("Email du patient : ");
        String email = scanner.nextLine();
        Utilisateur patient = utilisateurDAO.getUtilisateurByEmail(email);

        if (patient == null) {
            System.out.println("❌ Patient non trouvé.");
            return;
        }

        List<RendezVous> rdvs = rendezVousDAO.getAllRendezVous();
        System.out.println("\n📅 Rendez-vous du patient " + patient.getPrenom() + " " + patient.getNom() + " :");

        for (RendezVous rdv : rdvs) {
            if (rdv.getIdPatient() == patient.getId()) {
                Utilisateur specialiste = utilisateurDAO.getUtilisateurById(rdv.getIdSpecialiste());
                Horaire horaire = horaireDAO.getHoraireById(rdv.getIdHoraire());

                System.out.println("🆔 RDV ID : " + rdv.getId());
                System.out.println("👨‍⚕️ Spécialiste : " + specialiste.getPrenom() + " " + specialiste.getNom());
                System.out.println("📍 Lieu : " + rdv.getLieu());
                System.out.println("📅 Date : " + rdv.getDate());
                System.out.println("🕘 Heure : " + horaire.getHeureDebut() + " → " + horaire.getHeureFin());
                System.out.println("📝 Notes : " + rdv.getNotes());
                System.out.println("--------------------------------------------------");
            }
        }
    }*/
}
