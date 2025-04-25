package org.chem.Controleur;

import org.chem.Dao.*;
import org.chem.Modele.*;
import org.chem.Vue.*;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalTime;

public class AdminPatientControleur extends JFrame {

    private AdminPatientVue vue;
    private UtilisateurDAOImpl utilisateurDAO;
    private RendezVousDAOImpl rendezVousDAO;
    private HoraireDAOImpl horaireDAO;

    public AdminPatientControleur(AdminPatientVue vue, UtilisateurDAOImpl utilisateurDAO, RendezVousDAOImpl RdvDAO, HoraireDAOImpl horaireDAO) {
        this.vue = vue;
        this.utilisateurDAO = utilisateurDAO;
        this.rendezVousDAO = RdvDAO;
        this.horaireDAO = horaireDAO;

        afficherTousLesPatients();
    }

    public void afficherTousLesPatients(){
        ArrayList<Patient> liste = utilisateurDAO.getAllPatients();
        afficherPatients(liste);
    }

    public void afficherPatients(ArrayList<Patient> liste){
        JPanel panel = vue.getListePatientsPanel();

        for(Patient p : liste){
            ArrayList<RendezVous> listRdv = rendezVousDAO.getRendezVousByPatient(p.getId());
            PanelPatient panelPatient = new PanelPatient(p,listRdv);

            ArrayList<String> rdvFormates = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


            for (RendezVous rdv : listRdv) {
                Utilisateur s = utilisateurDAO.getById(rdv.getIdSpecialiste());
                Horaire h = horaireDAO.getHoraireById(rdv.getIdHoraire());

                if(s instanceof Specialiste spe){
                    String Specialisation = spe.getSpecialisation();

                    String nomSpecialiste = s.getPrenom() + " " + s.getNom();
                    String jour = Horaire.convertirJourIntEnString(h.getJourSemaine());

                    String heure = h.getHeureDebut().toLocalTime().format(formatter) + " - " + h.getHeureFin().toLocalTime().format(formatter);
                    String texte = jour + " " + rdv.getDate() + " (" + heure + ") avec " + nomSpecialiste + " [ "+ Specialisation + "] : "+rdv.getNotes();
                    rdvFormates.add(texte);
                }

            }

            panelPatient.afficherRendezVous(rdvFormates);


            panel.add(panelPatient); // Ajout du panel du spécialiste
            panel.revalidate(); // Recalcule la mise en page
            panel.repaint(); // Redessine l'interface

        }
    }
}
