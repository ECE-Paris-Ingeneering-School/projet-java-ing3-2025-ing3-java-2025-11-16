package org.chem.Vue;
import org.chem.Dao.DatabaseConnection;
import org.chem.Dao.RendezVousDAO;
import org.chem.Dao.RendezVousDAOImpl;
import org.chem.Modele.Horaire;
import org.chem.Modele.RendezVous;
import org.chem.Modele.Specialiste;
import org.chem.Modele.Utilisateur;

import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

public class PriseRDV extends JPanel{

    private LocalDate startOfWeek;
    private final Specialiste specialiste;
    private Utilisateur utilisateur;

    public PriseRDV(Specialiste specialiste, Utilisateur utilisateur) {
        this.specialiste = specialiste;
        this.utilisateur = utilisateur;
        this.startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        removeAll(); // Clear if refreshing

        // --- TOP: Header avec boutons navigation
        JPanel header = new JPanel(new BorderLayout());
        JButton previousWeek = new JButton("← pred");
        JButton nextWeek = new JButton("→ suiv");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH);
        String moisAnnee = startOfWeek.format(formatter);
        JLabel semaineLabel = new JLabel(moisAnnee, SwingConstants.CENTER);



        previousWeek.addActionListener(e -> {
            startOfWeek = startOfWeek.minusWeeks(1);
            buildUI();
        });

        nextWeek.addActionListener(e -> {
            startOfWeek = startOfWeek.plusWeeks(1);
            buildUI();
        });

        header.add(previousWeek,BorderLayout.WEST);
        header.add(semaineLabel);
        header.add(nextWeek, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // --- GRID: tableau des créneaux
        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        String[] heures = {"08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};

        JPanel grid = new JPanel(new GridLayout(heures.length + 1, jours.length,10,10));
        grid.setBorder(BorderFactory.createEmptyBorder()); // pas de contour


        for (int i = 0; i < jours.length; i++) {
            LocalDate currentDay = startOfWeek.plusDays(i);
            String jourComplet = jours[i] + " " + currentDay.getDayOfMonth();
            JLabel jourLabel = new JLabel(jourComplet, SwingConstants.CENTER);
            grid.add(jourLabel);
        }


        for (String heure : heures) {

            for (int i = 0; i < jours.length; i++) {
                JButton creneauBtn = new JButton();
                int jourInt = i + 1; // Lundi = 1

                Horaire match = null;
                for (Horaire h : specialiste.getEmploiDuTemps()) {
                    if (h.getJourSemaine() == jourInt && h.getHeureDebut().toString().startsWith(heure)) {
                        match = h;
                        break;
                    }
                }

                // Date exacte
                LocalDate date = startOfWeek.plusDays(jourInt - 1);
                Date dateUtil = java.sql.Date.valueOf(date);

                if (match!=null) {

                    int idHoraire = match.getId();

                    DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");
                    RendezVousDAO rdvDAO = new RendezVousDAOImpl(db);
                    System.out.println(specialiste.getId());

                    boolean dejaReserve = rdvDAO.estDejaReserve(idHoraire, dateUtil);


                    if (dejaReserve) {
                        // Grise le bouton + désactive
                        creneauBtn.setText(heure);
                        creneauBtn.setEnabled(false);
                        creneauBtn.setBackground(Color.LIGHT_GRAY);
                    }

                    else{
                        creneauBtn.setFocusPainted(false);
                        creneauBtn.setBorderPainted(false); // plus clean
                        creneauBtn.setContentAreaFilled(true); // permet de colorer, mais sans bordure moche

                        creneauBtn.setText(heure);
                        creneauBtn.setBackground(new Color(17, 169, 209));
                        creneauBtn.addActionListener(e -> {
                            String message = "Souhaitez-vous réserver ce créneau ?\n\n" +
                                    "Spécialiste : " + specialiste.getNom() + "\n" +
                                    "Jour : " + jours[jourInt - 1] + "\n" +
                                    "Heure : " + heure;

                            int choix = JOptionPane.showOptionDialog(this, message, "Confirmation de RDV",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                    new String[]{"Confirmer", "Annuler"}, "Confirmer");

                            if (choix == JOptionPane.YES_OPTION) {

                                RendezVous rdv = new RendezVous(specialiste.getId(),utilisateur.getId(),idHoraire,dateUtil,"note a voir",specialiste.getLieu());
                                boolean ok = rdvDAO.ajouterRendezVous(rdv);

                                if (ok) {
                                    JOptionPane.showMessageDialog(this, "Rendez-vous confirmé !");
                                    creneauBtn.setEnabled(false);
                                    creneauBtn.setBackground(Color.GRAY);
                                }
                                else{
                                    JOptionPane.showMessageDialog(this, "Erreur dans la confirmation !");
                                }

                            }
                        });
                    }


                } else {
                    creneauBtn.setEnabled(false); // Pas dispo
                    creneauBtn.setOpaque(false);
                    creneauBtn.setBorderPainted(false);
                }

                grid.add(creneauBtn);
            }
        }

        setBackground(new Color(229, 247, 255));
        add(grid, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}