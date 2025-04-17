package Vue;
import Modele.*;

import java.awt.*;
import java.time.*;
import java.util.*;
import javax.swing.*;

public class Calendrier extends JPanel{

    private LocalDate startOfWeek;
    private final Specialiste specialiste;

    public Calendrier(Specialiste specialiste) {
        this.specialiste = specialiste;
        this.startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        removeAll(); // Clear if refreshing

        // --- TOP: Header avec boutons navigation
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton previousWeek = new JButton("← Semaine précédente");
        JButton nextWeek = new JButton("→ Semaine suivante");
        JLabel semaineLabel = new JLabel("Semaine du " + startOfWeek + " au " + startOfWeek.plusDays(5));



        previousWeek.addActionListener(e -> {
            startOfWeek = startOfWeek.minusWeeks(1);
            buildUI();
        });

        nextWeek.addActionListener(e -> {
            startOfWeek = startOfWeek.plusWeeks(1);
            buildUI();
        });

        header.add(previousWeek);
        header.add(semaineLabel);
        header.add(nextWeek);
        add(header, BorderLayout.NORTH);

        // --- GRID: tableau des créneaux
        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        String[] heures = {"08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};

        JPanel grid = new JPanel(new GridLayout(heures.length + 1, jours.length,10,10));
        grid.setBorder(BorderFactory.createEmptyBorder()); // pas de contour


        for (String jour : jours) grid.add(new JLabel(jour, SwingConstants.CENTER));

        for (String heure : heures) {
            //grid.add(new JLabel(heure, SwingConstants.CENTER));
            for (int i = 0; i < jours.length; i++) {
                JButton creneauBtn = new JButton();
                int jourInt = i + 1; // Lundi = 1

                // Vérifie si ce créneau existe dans l'emploi du temps
                Optional<Horaire> match = specialiste.getEmploiDuTemps().stream()
                        .filter(h -> h.getJourSemaine() == jourInt &&
                                h.getHeureDebut().toString().startsWith(heure))
                        .findFirst();

                if (match.isPresent()) {
                    creneauBtn.setFocusPainted(false);
                    creneauBtn.setBorderPainted(false); // plus clean
                    creneauBtn.setContentAreaFilled(true); // permet de colorer, mais sans bordure moche

                    creneauBtn.setText(heure);
                    creneauBtn.setBackground(new Color(17, 169, 209));
                    creneauBtn.addActionListener(e -> {
                        JOptionPane.showMessageDialog(this,
                                "RDV avec " + specialiste.getNom() +
                                        " le " + jours[jourInt - 1] + " à " + heure);
                    });
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
