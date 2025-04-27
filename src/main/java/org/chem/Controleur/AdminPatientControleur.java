package org.chem.Controleur;

import org.chem.Dao.*;
import org.chem.Modele.*;
import org.chem.Vue.*;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalTime;

/**
 * Contrôleur pour la gestion des patients dans l'interface d'administration.
 * <p>
 * Il permet d'afficher la liste des patients et leurs rendez-vous associés.
 * </p>
 */
public class AdminPatientControleur extends JFrame {

    /** Vue associée à la gestion des patients. */
    private AdminPatientVue vue;

    /** DAO pour la gestion des utilisateurs. */
    private UtilisateurDAOImpl utilisateurDAO;

    /** DAO pour la gestion des rendez-vous. */
    private RendezVousDAOImpl rendezVousDAO;

    /** DAO pour la gestion des horaires. */
    private HoraireDAOImpl horaireDAO;

    /**
     * Constructeur du contrôleur pour la gestion des patients.
     *
     * @param vue la vue de gestion des patients
     * @param utilisateurDAO DAO pour interagir avec les utilisateurs
     * @param RdvDAO DAO pour interagir avec les rendez-vous
     * @param horaireDAO DAO pour interagir avec les horaires
     */
    public AdminPatientControleur(AdminPatientVue vue, UtilisateurDAOImpl utilisateurDAO, RendezVousDAOImpl RdvDAO, HoraireDAOImpl horaireDAO) {
        this.vue = vue;
        this.utilisateurDAO = utilisateurDAO;
        this.rendezVousDAO = RdvDAO;
        this.horaireDAO = horaireDAO;

        afficherTousLesPatients();
    }

    /**
     * Récupère et affiche tous les patients de la base de données.
     */
    public void afficherTousLesPatients() {
        ArrayList<Patient> liste = utilisateurDAO.getAllPatients();
        afficherPatients(liste);
    }

    /**
     * Affiche une liste de patients dans l'interface.
     *
     * <p>Pour chaque patient, ses rendez-vous sont récupérés, formatés
     * et affichés dans un panneau individuel.</p>
     *
     * @param liste la liste des patients à afficher
     */
    public void afficherPatients(ArrayList<Patient> liste) {
        JPanel panel = vue.getListePatientsPanel();

        for (Patient p : liste) {
            ArrayList<RendezVous> listRdv = rendezVousDAO.getRendezVousByPatient(p.getId());
            PanelPatient panelPatient = new PanelPatient(p, listRdv);

            ArrayList<String> rdvFormates = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            for (RendezVous rdv : listRdv) {
                Utilisateur s = utilisateurDAO.getById(rdv.getIdSpecialiste());
                Horaire h = horaireDAO.getHoraireById(rdv.getIdHoraire());

                if (s instanceof Specialiste spe) {
                    String specialisation = spe.getSpecialisation();
                    String nomSpecialiste = s.getPrenom() + " " + s.getNom();
                    String jour = Horaire.convertirJourIntEnString(h.getJourSemaine());
                    String heure = h.getHeureDebut().toLocalTime().format(formatter) + " - " + h.getHeureFin().toLocalTime().format(formatter);

                    String texte = jour + " " + rdv.getDate() + " (" + heure + ") avec " + nomSpecialiste + " [ " + specialisation + " ] : " + rdv.getNotes();
                    rdvFormates.add(texte);
                }
            }

            panelPatient.afficherRendezVous(rdvFormates);

            panel.add(panelPatient); // Ajout du panneau pour le patient
            panel.revalidate(); // Recalcule la mise en page
            panel.repaint(); // Redessine l'interface
        }
    }
}
