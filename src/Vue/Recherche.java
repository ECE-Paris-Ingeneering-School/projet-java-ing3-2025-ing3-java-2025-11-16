package Vue;

import Dao.*;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class Recherche extends BaseFrame {

    private JTextField motCleField;
    private JComboBox<String> jourCombo;
    private JComboBox<String> heureCombo;
    private JButton rechercherBtn;
    private JTextField lieuField;

    private UtilisateurDAOImpl specialisteDAO;
    private JPanel resultatsPanel;


    public Recherche(Utilisateur utilisateur) {
        super(utilisateur);

        JPanel contenu = getCenterPanel(); // affichage des specialistes + filtres jours/horaires/Lieu
        JPanel bandeau = getNorthPanel(); // Barre de recherche dans le bandeau (Nom specialiste ou Specialité)

        DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");
        this.specialisteDAO = new UtilisateurDAOImpl(db);

        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        motCleField = new JTextField(20);
        rechercherBtn = new JButton("Rechercher");
        motCleField.setPreferredSize(new Dimension(300, 30));

        resultatsPanel = new JPanel();
        resultatsPanel.setLayout(new BoxLayout(resultatsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultatsPanel);
        contenu.add(scrollPane, BorderLayout.CENTER);


        motCleField.setBorder(BorderFactory.createLineBorder(new Color(15, 75, 135)));
        motCleField.setText("Nom, spécialité");
        motCleField.setForeground(Color.GRAY);

        motCleField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (motCleField.getText().equals("Nom, spécialité")) {
                    motCleField.setText("");
                    motCleField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (motCleField.getText().isEmpty()) {
                    motCleField.setForeground(Color.GRAY);
                    motCleField.setText("Nom, spécialité");
                }
            }
        });

        JPanel filtrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filtrePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Un peu d'espace
        filtrePanel.setBackground(Color.WHITE); // ou une autre couleur douce si tu veux

    // Barre de recherche pour le lieu
        lieuField = new JTextField("Lieu", 15);
        lieuField.setForeground(Color.GRAY);
        lieuField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (lieuField.getText().equals("Lieu")) {
                    lieuField.setText("");
                    lieuField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (lieuField.getText().isEmpty()) {
                    lieuField.setForeground(Color.GRAY);
                    lieuField.setText("Lieu");
                }
            }
        });

    // ComboBox pour le jour
        String[] jours = {"", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"};
        jourCombo = new JComboBox<>(jours);

    // ComboBox pour l'horaire
        String[] horaires = {"", "08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00"};
        heureCombo = new JComboBox<>(horaires);

    // Ajout au panel
        filtrePanel.add(new JLabel("Lieu:"));
        filtrePanel.add(lieuField);
        filtrePanel.add(new JLabel("Jour:"));
        filtrePanel.add(jourCombo);
        filtrePanel.add(new JLabel("Horaire:"));
        filtrePanel.add(heureCombo);

    // Ensuite, ajoute ce filtrePanel en haut du contenu principal :
        contenu.add(filtrePanel, BorderLayout.NORTH);

        recherchePanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10)); // Un peu d'espace

        recherchePanel.add(motCleField);
        recherchePanel.add(rechercherBtn);
        recherchePanel.setBackground(new Color(54, 153, 213));

        bandeau.add(recherchePanel);

        rechercherBtn.addActionListener(e -> rechercher());

        setVisible(true);
    }

    public void rechercher() {
        String motCle = motCleField.getText().trim();
        String lieu = lieuField.getText().trim();
        String jour = null;
        Time heure = null;

        System.out.println(motCle);
        System.out.println(lieu);

        // Récupérer le jour s’il est sélectionné
        if (jourCombo.getSelectedItem() != null && !jourCombo.getSelectedItem().toString().isEmpty()) {
            jour = jourCombo.getSelectedItem().toString().toLowerCase();
            System.out.println("Jour: " + jour);
        }

        // Récupérer l'heure si sélectionnée
        if (heureCombo.getSelectedItem() != null && !heureCombo.getSelectedItem().toString().isEmpty()) {
            try {
                heure = Time.valueOf(heureCombo.getSelectedItem().toString() + ":00");
                System.out.println("Heure: " + heure);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Heure invalide (ex : 09:00)");
                return;
            }
        }

        if(motCle.trim().isEmpty() || motCle.equalsIgnoreCase("Nom, spécialité")) {
            System.out.println("MotCle: " + motCle);
            JOptionPane.showMessageDialog(this, "Saisir un nom ou une spécialité","Champ requis", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Appel au DAO
        ArrayList<Specialiste> resultats = specialisteDAO.rechercherSpecialistes(motCle, jour, heure, lieu);

        // Affichage
        afficherResultats(resultats);
    }


    public JPanel creerPanelSpecialiste(Specialiste s) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(new Color(253, 249, 249));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250)); // Hauteur fixe

        // Partie gauche - Infos perso
        JPanel infosPanel = new JPanel();
        infosPanel.setLayout(new BoxLayout(infosPanel, BoxLayout.Y_AXIS));
        infosPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infosPanel.setBackground(new Color(253, 249, 249));

        JLabel nomPrenom = new JLabel(s.getPrenom() + " " + s.getNom());
        nomPrenom.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel specialisation = new JLabel("Spécialité : " + s.getSpecialisation());
        JLabel lieu = new JLabel("Lieu : " + s.getLieu());

        infosPanel.add(nomPrenom);
        infosPanel.add(specialisation);
        infosPanel.add(lieu);

        // Partie droite - Emploi du temps
        Calendrier calendrierPanel = new Calendrier(s);

        panel.add(infosPanel, BorderLayout.WEST);
        panel.add(calendrierPanel, BorderLayout.EAST);

        return panel;
    }


    public void afficherResultats(ArrayList<Specialiste> listeSpecialistes) {
        resultatsPanel.removeAll(); // Vider les anciens résultats

        if (listeSpecialistes.isEmpty()) {
            JLabel label = new JLabel("Aucun spécialiste trouvé.");
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            resultatsPanel.add(label);
        } else {
            for (Specialiste s : listeSpecialistes) {
                resultatsPanel.add(creerPanelSpecialiste(s));
            }
        }

        resultatsPanel.revalidate();
        resultatsPanel.repaint();
    }

}