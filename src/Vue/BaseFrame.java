package Vue;

import javax.swing.*;
import java.awt.*;

public class BaseFrame extends JFrame {

    private JLabel userLabel; // Affiche le compte utilisateur

    public BaseFrame(String utilisateurConnecte) {
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

        /*// Affichage de l'utilisateur connecté (à gauche)
        userLabel = new JLabel("👤 Connecté : " + utilisateurConnecte);
        userLabel.setForeground(Color.WHITE);
        userLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(userLabel, BorderLayout.WEST);*/

        // Titre de l'application (au centre)
        JLabel titreLabel = new JLabel("MediLink", SwingConstants.CENTER);
        titreLabel.setForeground(Color.WHITE);
        titreLabel.setFont(new Font("Brush Script MT", Font.BOLD, 50));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        topPanel.add(titreLabel, BorderLayout.CENTER);

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
        userLabel.setText("👤 " + texte);
    }
}
