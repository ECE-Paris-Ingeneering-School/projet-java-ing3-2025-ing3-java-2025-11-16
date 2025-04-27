package org.chem.Vue;

import org.chem.Controleur.PriseRDVControleur;
import org.chem.Dao.DatabaseConnection;
import org.chem.Dao.RendezVousDAOImpl;
import org.chem.Dao.UtilisateurDAOImpl;
import org.chem.Modele.Session;
import org.chem.Modele.Specialiste;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre de recherche permettant de trouver des spécialistes en fonction de différents critères : nom, spécialité, lieu, jour et horaire.
 * Cette classe est utilisée pour afficher l'interface de recherche et les résultats.
 */
public class Recherche extends BaseFrame {

    /** Champ de texte pour entrer un mot-clé (nom, spécialité). */
    private JTextField motCleField;

    /** ComboBox pour sélectionner un jour de la semaine. */
    private JComboBox<String> jourCombo;

    /** ComboBox pour sélectionner un horaire. */
    private JComboBox<String> heureCombo;

    /** Bouton pour lancer la recherche. */
    private JButton rechercherBtn;

    /** Champ de texte pour entrer un lieu. */
    private JTextField lieuField;

    /** Panneau qui affiche les résultats de la recherche. */
    private JPanel resultatsPanel;

    /**
     * Constructeur de la fenêtre de recherche.
     * Initialise les éléments graphiques pour permettre à l'utilisateur de rechercher un spécialiste.
     */
    public Recherche() {
        super(Session.getUtilisateur());

        // Création des panneaux de contenu et bandeau
        JPanel contenu = getCenterPanel();
        JPanel bandeau = getNorthPanel();

        // Panneau de recherche avec un champ de texte et un bouton
        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        motCleField = new JTextField(20);
        rechercherBtn = new JButton("Rechercher");
        motCleField.setPreferredSize(new Dimension(300, 30));

        // Initialisation du panneau de résultats de la recherche
        resultatsPanel = new JPanel();
        resultatsPanel.setLayout(new BoxLayout(resultatsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultatsPanel);
        contenu.add(scrollPane, BorderLayout.CENTER);

        // Configuration du champ de texte pour le mot-clé
        motCleField.setBorder(BorderFactory.createLineBorder(new Color(15, 75, 135)));
        motCleField.setText("Nom, spécialité");
        motCleField.setForeground(Color.GRAY);

        // Gestion du focus pour le champ de texte
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

        // Panneau de filtres (lieu, jour, horaire)
        JPanel filtrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filtrePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        filtrePanel.setBackground(Color.WHITE);

        // Champ de texte pour le lieu
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

        // Ajout des éléments dans le panneau de filtres
        filtrePanel.add(new JLabel("Lieu:"));
        filtrePanel.add(lieuField);
        filtrePanel.add(new JLabel("Jour:"));
        filtrePanel.add(jourCombo);
        filtrePanel.add(new JLabel("Horaire:"));
        filtrePanel.add(heureCombo);

        contenu.add(filtrePanel, BorderLayout.NORTH);

        // Configuration du panneau de recherche
        recherchePanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        recherchePanel.add(motCleField);
        recherchePanel.add(rechercherBtn);
        recherchePanel.setBackground(Color.decode("#649FCB"));

        bandeau.add(recherchePanel);

        setVisible(true);
    }

    /**
     * Crée un panneau pour afficher les informations d'un spécialiste.
     *
     * @param s Le spécialiste à afficher.
     * @return Un panneau contenant les informations et emploi du temps du spécialiste.
     */
    public JPanel creerPanelSpecialiste(Specialiste s) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(new Color(253, 249, 249));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));

        // Panneau de gauche pour les informations personnelles
        JPanel infosPanel = new JPanel();
        infosPanel.setLayout(new BoxLayout(infosPanel, BoxLayout.Y_AXIS));
        infosPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infosPanel.setBackground(new Color(253, 249, 249));

        // Affichage du nom, spécialité et lieu
        JLabel nomPrenom = new JLabel(s.getPrenom() + " " + s.getNom());
        nomPrenom.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel specialisation = new JLabel("Spécialité : " + s.getSpecialisation());
        JLabel lieu = new JLabel("Lieu : " + s.getLieu());

        infosPanel.add(nomPrenom);
        infosPanel.add(specialisation);
        infosPanel.add(lieu);

        // Panneau de droite pour l'emploi du temps
        DatabaseConnection db = DatabaseConnection.getDefaultInstance();
        RendezVousDAOImpl rendezVousDAO = new RendezVousDAOImpl(db);

        PriseRDVVue rdvv = new PriseRDVVue();
        PriseRDVControleur rdvControleur = new PriseRDVControleur(rdvv, s, utilisateurConnecte, rendezVousDAO);

        panel.add(infosPanel, BorderLayout.WEST);
        panel.add(rdvv, BorderLayout.EAST);

        return panel;
    }

    /**
     * Récupère le champ de texte pour le mot-clé de la recherche.
     *
     * @return Le champ de texte pour le mot-clé.
     */
    public JTextField getMotCleField() {
        return motCleField;
    }

    /**
     * Récupère le champ de texte pour le lieu de recherche.
     *
     * @return Le champ de texte pour le lieu.
     */
    public JTextField getLieuField() {
        return lieuField;
    }

    /**
     * Récupère le ComboBox pour le jour de la semaine.
     *
     * @return Le ComboBox pour le jour.
     */
    public JComboBox<String> getJourCombo() {
        return jourCombo;
    }

    /**
     * Récupère le ComboBox pour l'horaire de la recherche.
     *
     * @return Le ComboBox pour l'horaire.
     */
    public JComboBox<String> getHeureCombo() {
        return heureCombo;
    }

    /**
     * Récupère le bouton pour lancer la recherche.
     *
     * @return Le bouton de recherche.
     */
    public JButton getRechercherBtn() {
        return rechercherBtn;
    }

    /**
     * Récupère le panneau qui affiche les résultats de la recherche.
     *
     * @return Le panneau des résultats de la recherche.
     */
    public JPanel getResultatsPanel() {
        return resultatsPanel;
    }
}
