package org.chem.Controleur;
import org.chem.Dao.RendezVousDAO;
import org.chem.Modele.*;
import org.chem.Vue.PriseRDVVue;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PriseRDVControleur {
    private final PriseRDVVue vue;
    private final Specialiste specialiste;
    private final Utilisateur utilisateur;
    private final RendezVousDAO rdvDAO;
    private LocalDate startOfWeek;

    public PriseRDVControleur(PriseRDVVue vue, Specialiste specialiste, Utilisateur utilisateur, RendezVousDAO rdvDAO) {
        this.vue = vue;
        this.specialiste = specialiste;
        this.utilisateur = utilisateur;
        this.rdvDAO = rdvDAO;
        this.startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);

        ajouterListeners();
        updateView();
    }

    private void ajouterListeners() {
        vue.getBtnSuiv().addActionListener(e -> {
            startOfWeek = startOfWeek.plusWeeks(1);
            updateView();
        });

        vue.getBtnPrec().addActionListener(e -> {
            startOfWeek = startOfWeek.minusWeeks(1);
            updateView();
        });
    }

    private void updateView() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH);
        vue.setMoisLabel(startOfWeek.format(formatter));
        vue.setGridPanel(creerGrilleCreneaux());
    }

    private JPanel creerGrilleCreneaux() {
        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        String[] heures = {"08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00"};

        JPanel grid = new JPanel(new GridLayout(heures.length + 1, jours.length, 10, 10));

        // Afficher les jours avec le numéro du jour
        for (int i = 0; i < jours.length; i++) {
            LocalDate date = startOfWeek.plusDays(i);  // Calculer la date pour chaque jour de la semaine
            String jourAvecDate = jours[i] + " " + date.getDayOfMonth(); // Ajouter le numéro du jour
            JLabel label = new JLabel(jourAvecDate, SwingConstants.CENTER);
            grid.add(label);
        }

        for (String heure : heures) {
            for (int i = 0; i < jours.length; i++) {
                int jourInt = i + 1;
                LocalDate date = startOfWeek.plusDays(i);
                Date dateUtil = java.sql.Date.valueOf(date);
                Horaire match = specialiste.getEmploiDuTemps().stream()
                        .filter(h -> h.getJourSemaine() == jourInt && h.getHeureDebut().toString().startsWith(heure))
                        .findFirst().orElse(null);

                JButton bouton = new JButton(heure);
                if (match == null || rdvDAO.estDejaReserve(match.getId(), dateUtil)) {
                    bouton.setEnabled(false);
                    bouton.setBackground(Color.LIGHT_GRAY);
                } else {
                    bouton.setBackground(new Color(17, 169, 209));
                    bouton.addActionListener(e -> reserverCreneau(match.getId(), dateUtil, jourInt, heure));
                }

                grid.add(bouton);
            }
        }

        return grid;
    }

    private void reserverCreneau(int idHoraire, Date date, int jourInt, String heure) {
        String message = "Souhaitez-vous réserver ce créneau ?\n" +
                "Spécialiste : " + specialiste.getNom() + "\n" +
                "Jour : " + DayOfWeek.of(jourInt) + "\n" +
                "Heure : " + heure;

        int choix = JOptionPane.showOptionDialog(vue, message, "Confirmation de RDV",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Confirmer", "Annuler"}, "Confirmer");

        if (choix == JOptionPane.YES_OPTION) {
            RendezVous rdv = new RendezVous(specialiste.getId(), utilisateur.getId(), idHoraire, date, "note à voir", specialiste.getLieu());
            boolean ok = rdvDAO.ajouterRendezVous(rdv);
            JOptionPane.showMessageDialog(vue, ok ? "Rendez-vous confirmé !" : "Erreur !");
            updateView(); // refresh
        }
    }
}
