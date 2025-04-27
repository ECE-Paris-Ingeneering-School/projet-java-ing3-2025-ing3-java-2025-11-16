package org.chem.Controleur;

import org.chem.Dao.RendezVousDAO;
import org.chem.Modele.*;
import org.chem.Vue.PriseRDVVue;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Contrôleur pour la gestion de la prise de rendez-vous avec un spécialiste.
 * Permet de naviguer entre les semaines et de réserver des créneaux horaires.
 */
public class PriseRDVControleur {

    /** La vue associée à ce contrôleur. */
    private final PriseRDVVue vue;

    /** Le spécialiste pour lequel le rendez-vous est pris. */
    private final Specialiste specialiste;

    /** L'utilisateur (patient) qui prend le rendez-vous. */
    private final Utilisateur utilisateur;

    /** Le DAO pour gérer les rendez-vous en base de données. */
    private final RendezVousDAO rdvDAO;

    /** La date du début de la semaine actuelle. */
    private LocalDate startOfWeek;

    /**
     * Constructeur du contrôleur de la prise de rendez-vous.
     * Initialise les valeurs et ajoute les listeners pour les actions utilisateur.
     *
     * @param vue La vue de la prise de rendez-vous.
     * @param specialiste Le spécialiste avec lequel le rendez-vous est pris.
     * @param utilisateur L'utilisateur (patient) qui prend le rendez-vous.
     * @param rdvDAO Le DAO pour la gestion des rendez-vous.
     */
    public PriseRDVControleur(PriseRDVVue vue, Specialiste specialiste, Utilisateur utilisateur, RendezVousDAO rdvDAO) {
        this.vue = vue;
        this.specialiste = specialiste;
        this.utilisateur = utilisateur;
        this.rdvDAO = rdvDAO;
        this.startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);  // Initialisation au début de la semaine

        // Ajouter les listeners pour les boutons
        ajouterListeners();

        // Mise à jour initiale de la vue
        updateView();
    }

    /**
     * Ajoute les listeners pour les boutons de navigation (précédent, suivant) sur la vue.
     */
    private void ajouterListeners() {
        vue.getBtnSuiv().addActionListener(e -> {
            startOfWeek = startOfWeek.plusWeeks(1);  // Avancer d'une semaine
            updateView();
        });

        vue.getBtnPrec().addActionListener(e -> {
            startOfWeek = startOfWeek.minusWeeks(1);  // Reculer d'une semaine
            updateView();
        });
    }

    /**
     * Met à jour la vue en fonction de la semaine actuelle.
     * Affiche le mois et l'année, puis met à jour la grille des créneaux.
     */
    private void updateView() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH);
        vue.setMoisLabel(startOfWeek.format(formatter));  // Affichage du mois et de l'année
        vue.setGridPanel(creerGrilleCreneaux());  // Création de la grille des créneaux horaires
    }

    /**
     * Crée la grille des créneaux horaires pour la semaine en cours.
     * Affiche les jours et les heures disponibles ou réservées.
     *
     * @return La grille des créneaux horaires à afficher dans la vue.
     */
    private JPanel creerGrilleCreneaux() {
        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        String[] heures = {"08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00"};

        JPanel grid = new JPanel(new GridLayout(heures.length + 1, jours.length, 10, 10));

        // Affichage des jours avec la date du jour
        for (int i = 0; i < jours.length; i++) {
            LocalDate date = startOfWeek.plusDays(i);  // Calculer la date pour chaque jour de la semaine
            String jourAvecDate = jours[i] + " " + date.getDayOfMonth();  // Ajouter le numéro du jour
            JLabel label = new JLabel(jourAvecDate, SwingConstants.CENTER);
            grid.add(label);
        }

        // Création des boutons pour chaque créneau horaire
        for (String heure : heures) {
            for (int i = 0; i < jours.length; i++) {
                int jourInt = i + 1;
                LocalDate date = startOfWeek.plusDays(i);
                Date dateUtil = java.sql.Date.valueOf(date);
                Horaire match = specialiste.getEmploiDuTemps().stream()
                        .filter(h -> h.getJourSemaine() == jourInt && h.getHeureDebut().toString().startsWith(heure))
                        .findFirst().orElse(null);

                // Création du bouton pour chaque créneau
                JButton bouton = new JButton(heure);
                if (match == null || rdvDAO.estDejaReserve(match.getId(), dateUtil)) {
                    bouton.setEnabled(false);  // Désactive le bouton si le créneau est réservé
                    bouton.setBackground(Color.LIGHT_GRAY);
                } else {
                    bouton.setBackground(new Color(17, 169, 209));  // Bouton actif
                    bouton.addActionListener(e -> reserverCreneau(match.getId(), dateUtil, jourInt, heure));
                }
                grid.add(bouton);
            }
        }
        return grid;
    }

    /**
     * Permet à l'utilisateur de réserver un créneau horaire spécifique avec le spécialiste.
     * Affiche une boîte de dialogue de confirmation et enregistre le rendez-vous si confirmé.
     *
     * @param idHoraire L'ID du créneau horaire.
     * @param date La date du rendez-vous.
     * @param jourInt Le numéro du jour de la semaine.
     * @param heure L'heure du rendez-vous.
     */
    private void reserverCreneau(int idHoraire, Date date, int jourInt, String heure) {
        String message = "Souhaitez-vous réserver ce créneau ?\n" +
                "Spécialiste : " + specialiste.getNom() + "\n" +
                "Jour : " + DayOfWeek.of(jourInt) + "\n" +
                "Heure : " + heure;

        int choix = JOptionPane.showOptionDialog(vue, message, "Confirmation de RDV",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Confirmer", "Annuler"}, "Confirmer");

        if (choix == JOptionPane.YES_OPTION) {
            String note = JOptionPane.showInputDialog(vue, "Veuillez saisir une note pour ce rendez-vous :", "Note du rendez-vous", JOptionPane.PLAIN_MESSAGE);

            if (note == null) {
                note = "aucune note";
            }

            RendezVous rdv = new RendezVous(specialiste.getId(), utilisateur.getId(), idHoraire, date, note, specialiste.getLieu());

            boolean ok = rdvDAO.ajouterRendezVous(rdv);  // Enregistrement du rendez-vous
            JOptionPane.showMessageDialog(vue, ok ? "Rendez-vous confirmé !" : "Erreur !");
            updateView(); // Rafraîchissement de la vue
        }
    }
}
