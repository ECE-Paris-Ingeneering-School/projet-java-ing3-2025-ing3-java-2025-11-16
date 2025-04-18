package Vue;
import Dao.*;
import Modele.*;
import Controleur.*;

import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

public class Calendrier extends JPanel{

    private LocalDate startOfWeek;
    private final Specialiste specialiste;
    private JButton[][] boutons;
    private String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
    private String[] heures = {"08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};
    private JButton previousWeek;
    private JButton nextWeek;
    private JLabel semaineLabel;

    public Calendrier(Specialiste specialiste) {
        this.specialiste = specialiste;
        this.startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        setLayout(new BorderLayout());

        buildUI();

        new CalendrierControleur(this, specialiste);
    }

    private void buildUI() {
        removeAll(); // Clear if refreshing

        // --- TOP: Header avec boutons navigation
        JPanel header = new JPanel(new BorderLayout());
        previousWeek = new JButton("← pred");
        nextWeek = new JButton("→ suiv");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH);
        String moisAnnee = startOfWeek.format(formatter);
        semaineLabel = new JLabel(moisAnnee, SwingConstants.CENTER);


        /*previousWeek.addActionListener(e -> {
            startOfWeek = startOfWeek.minusWeeks(1);
            buildUI();
        });

        nextWeek.addActionListener(e -> {
            startOfWeek = startOfWeek.plusWeeks(1);
            buildUI();
        });*/

        header.add(previousWeek,BorderLayout.WEST);
        header.add(semaineLabel);
        header.add(nextWeek, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // --- GRID: tableau des créneaux

        JPanel grid = new JPanel(new GridLayout(heures.length + 1, jours.length,10,10));
        boutons = new JButton[heures.length][jours.length]; // Stocker les boutons pour le contrôleur
        grid.setBorder(BorderFactory.createEmptyBorder()); // pas de contour

        // Ligne des jours
        for (int j = 0; j < jours.length; j++) {
            LocalDate currentDay = startOfWeek.plusDays(j);
            String jourComplet = jours[j] + " " + currentDay.getDayOfMonth();
            JLabel jourLabel = new JLabel(jourComplet, SwingConstants.CENTER);
            grid.add(jourLabel);
        }

        // Grille horaire

        for(int h=0; h<heures.length; h++) {
            for (int j=0; j<jours.length; j++) {
                JButton btn = new JButton(); // Texte/état sera géré par le contrôleur
                boutons[h][j] = btn;
                grid.add(btn);
            }
        }
        add(grid, BorderLayout.CENTER);

        setBackground(new Color(229, 247, 255));
        revalidate();
        repaint();

    }

    public JButton[][] getBoutons() {
        return boutons;
    }

    public String[] getJours() {
        return jours;
    }

    public String[] getHeures() {
        return heures;
    }

    public LocalDate getStartOfWeek() {
        return startOfWeek;
    }

    public JButton getPreviousWeekButton() {
        return previousWeek;
    }

    public JButton getNextWeekButton() {
        return nextWeek;
    }

    public JLabel getSemaineLabel() {
        return semaineLabel;
    }



    public void setStartOfWeek(LocalDate startOfWeek) {
        this.startOfWeek = startOfWeek;
        removeAll();
        buildUI(); // rebuild UI après changement de semaine
    }
}
