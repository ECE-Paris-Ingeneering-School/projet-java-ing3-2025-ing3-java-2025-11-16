package Vue;

import javax.swing.*;
import java.awt.*;
import Modele.*;

public class BaseFrame extends JFrame {

    private JLabel userLabel; // Affiche le compte utilisateur
    protected Utilisateur utilisateurConnecte;

    // Constructeur avec utilisateur connecté
    public BaseFrame(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
        initUI();
    }

    // Constructeur sans utilisateur (cas d'une page sans utilisateur connecté)
    public BaseFrame() {
        this(null);  // On passe null pour indiquer qu'il n'y a pas d'utilisateur connecté
    }

    // Initialisation de l'interface graphique
    public void initUI() {
        // Affiche le type d'utilisateur
        String role = "Non connecté";
        String userText = "Veuillez vous connecter.";

        if (utilisateurConnecte != null) {
            if (utilisateurConnecte instanceof Patient) {
                role = "Patient";
            } else if (utilisateurConnecte instanceof Specialiste) {
                role = "Spécialiste";
            }
            // Affichage de l'utilisateur connecté
            userText = utilisateurConnecte.getPrenom() + " " + utilisateurConnecte.getNom() + " (ID: " + utilisateurConnecte.getId() + ")";
        }

        // Configuration du label utilisateur (s'il est connecté ou non)
        userLabel = new JLabel(role + " : " + userText);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        // Configuration de la fenêtre principale
        setTitle("Application Rendez-vous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrer la fenêtre

        // Panel principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Bandeau supérieur
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(166, 212, 240)); // Bleu doux

        // Titre de l'application (au centre)
        JLabel titreLabel = new JLabel("MediLink", SwingConstants.CENTER);
        titreLabel.setForeground(Color.WHITE);
        titreLabel.setFont(new Font("Brush Script MT", Font.BOLD, 50));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        topPanel.add(titreLabel, BorderLayout.CENTER);

        // Ajouter le label utilisateur à gauche
        topPanel.add(userLabel, BorderLayout.WEST);

        // Ajout du bandeau au panel principal
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Tu peux maintenant ajouter tes panneaux de contenu dans le CENTER, etc.
        // JPanel contentPanel = new JPanel();
        // mainPanel.add(contentPanel, BorderLayout.CENTER);

        setVisible(true); // Afficher la fenêtre
    }

    // Méthode pour accéder au panneau principal
    public JPanel getMainPanel() {
        return (JPanel) getContentPane();
    }

    // Pour mettre à jour dynamiquement le texte de l'utilisateur
    public void setUserText(String texte) {
        if (userLabel != null) {
            userLabel.setText("👤 " + texte);
        }
    }
}
