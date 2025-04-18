package Controleur;
import Modele.*;
import Vue.*;
import Dao.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

public class CalendrierControleur {

    private Calendrier vue;
    private Specialiste specialiste;
    private RendezVousDAO rdvDAO;

    public CalendrierControleur(Calendrier vue, Specialiste specialiste) {
        this.vue = vue;
        this.specialiste = specialiste;

        DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");
        this.rdvDAO = new RendezVousDAOImpl(db);

        attacherListeners();
    }

    private void attacherListeners() {
        JButton[][] boutons = vue.getBoutons();
        String[] heures = vue.getHeures();
        String[] jours = vue.getJours();
        LocalDate startOfWeek = vue.getStartOfWeek();


        for (int i = 0; i < heures.length; i++) {
            for (int j = 0; j < jours.length; j++) {
                JButton bouton = boutons[i][j];
                if (bouton == null) continue;

                final int ligne = i;
                final int colonne = j;

                bouton.addActionListener(e -> {
                    String heure = heures[ligne];
                    String jourTexte = jours[colonne];
                    LocalDate date = startOfWeek.plusDays(colonne);
                    Date dateUtil = java.sql.Date.valueOf(date);

                    // Récupère l'horaire correspondant
                    Horaire horaire = specialiste.getEmploiDuTemps().stream()
                            .filter(h -> h.getJourSemaine() == colonne + 1 && h.getHeureDebut().toString().startsWith(heure))
                            .findFirst()
                            .orElse(null);

                    if (horaire == null) return;

                    boolean dejaPris = rdvDAO.estDejaReserve(horaire.getId(), dateUtil);

                    if (dejaPris) {
                        bouton.setText(heure);
                        bouton.setEnabled(false);
                        bouton.setBackground(Color.GRAY);
                        return;
                    }

                    int confirm = JOptionPane.showConfirmDialog(vue,
                            "Confirmer le rendez-vous à " + heure + " le " + jourTexte + " ?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        RendezVous rdv = new RendezVous(
                                specialiste.getId(),
                                Session.getUtilisateur().getId(),
                                horaire.getId(),
                                dateUtil,
                                "note à définir",
                                specialiste.getLieu());

                        boolean ok = rdvDAO.ajouterRendezVous(rdv);
                        if (ok) {
                            JOptionPane.showMessageDialog(vue, "Rendez-vous confirmé !");
                            bouton.setEnabled(false);
                            bouton.setBackground(Color.GRAY);
                        } else {
                            JOptionPane.showMessageDialog(vue, "Erreur lors de la réservation.");
                        }
                    }
                });
            }
        }
    }
}
