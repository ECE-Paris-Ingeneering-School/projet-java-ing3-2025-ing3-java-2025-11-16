package org.chem.Vue;

import org.chem.Controleur.PriseRDVControleur;
import org.chem.Dao.DatabaseConnection;
import org.chem.Dao.RendezVousDAOImpl;
import org.chem.Dao.UtilisateurDAOImpl;
import org.chem.Modele.Session;
import org.chem.Modele.Specialiste;

import javax.swing.*;
import java.awt.*;

public class Recherche extends BaseFrame {

    private JTextField motCleField;
    private JComboBox<String> jourCombo;
    private JComboBox<String> heureCombo;
    private JButton rechercherBtn;
    private JTextField lieuField;

    private JPanel resultatsPanel;


    public Recherche() {
        super(Session.getUtilisateur());

        JPanel contenu = getCenterPanel(); // affichage des specialistes + filtres jours/horaires/Lieu
        JPanel bandeau = getNorthPanel(); // Barre de recherche dans le bandeau (Nom specialiste ou Specialité)

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

        setVisible(true);
    }


    public JPanel creerPanelSpecialiste(Specialiste s) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(new Color(253, 249, 249));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350)); // Hauteur fixe

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
        DatabaseConnection db = DatabaseConnection.getDefaultInstance();
        RendezVousDAOImpl rendezVousDAO = new RendezVousDAOImpl(db);

        PriseRDVVue rdvv = new PriseRDVVue();
        PriseRDVControleur rdvControleur = new PriseRDVControleur(rdvv,s,utilisateurConnecte,rendezVousDAO);

        //PriseRDV calendrierVue = new PriseRDV(s,utilisateurConnecte);

        panel.add(infosPanel, BorderLayout.WEST);
        panel.add(rdvv, BorderLayout.EAST);

        return panel;
    }


    public JTextField getMotCleField() {
        return motCleField;
    }

    public JTextField getLieuField() {
        return lieuField;
    }

    public JComboBox<String> getJourCombo() {
        return jourCombo;
    }

    public JComboBox<String> getHeureCombo() {
        return heureCombo;
    }

    public JButton getRechercherBtn() {
        return rechercherBtn;
    }

    public JPanel getResultatsPanel() {
        return resultatsPanel;

    }
}