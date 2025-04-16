package Vue;

import Modele.*;
import com.mysql.cj.exceptions.UnableToConnectException;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PageAccueil extends BaseFrame {

    public PageAccueil() {
        super();
        // Récupérer le panneau principal de la classe de base pour ne pas le remplacer
        JPanel mainPanel = getMainPanel();

        // Panel pour les 3 cases (Patient, Spécialiste, Admin)
        JPanel casePanel = new JPanel(new GridBagLayout());
        mainPanel.add(casePanel, BorderLayout.CENTER); // Ajouter le casePanel dans le CENTER du mainPanel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Première colonne
        gbc.gridy = 0; // Première ligne

        // Case Patient
        JButton patientButton = createButton("Patient", "../images/patient.jpg");
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement autour du bouton
        casePanel.add(patientButton, gbc);

        // Case Spécialiste
        gbc.gridx = 1; // Deuxième colonne
        JButton specialisteButton = createButton("Spécialiste", "../images/docteur.jpg");
        casePanel.add(specialisteButton, gbc);

        // Case Admin
        gbc.gridx = 2; // Troisième colonne
        JButton adminButton = createButton("Admin", "images/admin.jpg");
        casePanel.add(adminButton, gbc);

        // Rendre la fenêtre visible
        setVisible(true);

        // Actions au clic sur les boutons
        patientButton.addActionListener(e -> {
            new ChoixConnexion("patient");
            dispose(); // ferme la page d'accueil
        });

        specialisteButton.addActionListener(e -> {
            new ChoixConnexion("specialiste");
            dispose();
        });

        adminButton.addActionListener(e -> {
            new ChoixConnexion("admin");
            dispose();
        });

    }

    // Méthode pour créer un bouton avec une image et un texte
    private JButton createButton(String text, String imagePath) {
        // Créer un bouton avec le texte
        JButton button = new JButton(text);

        // Ajouter une image au bouton
        try {
            URL imageUrl = new URL("file:" + imagePath);  // Utilisation de l'URL locale du fichier
            ImageIcon icon = new ImageIcon(imageUrl);
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setPreferredSize(new Dimension(200, 200)); // Taille du bouton
        } catch (Exception e) {
            e.printStackTrace();
        }

        return button;
    }

}
