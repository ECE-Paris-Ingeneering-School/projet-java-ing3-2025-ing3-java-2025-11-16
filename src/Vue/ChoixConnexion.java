package Vue;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ChoixConnexion extends BaseFrame {

    public ChoixConnexion() {
        super();
        // Récupérer le panneau principal de la classe de base pour ne pas le remplacer
        JPanel contenuPanel = getCenterPanel();

        // Ajouter un titre en haut de la page
        JLabel titreLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
        contenuPanel.add(titreLabel, BorderLayout.NORTH);

        // Panel pour les 3 lignes et 2 colonnes (Image à gauche, Bouton à droite)
        JPanel casePanel = new JPanel(new GridBagLayout());
        contenuPanel.add(casePanel, BorderLayout.CENTER); // Ajouter le casePanel dans le CENTER du mainPanel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement autour des composants
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        // Ligne 1 : Image Patient et Bouton Patient
        gbc.gridx = 0; // Première colonne
        gbc.gridy = 0; // Première ligne
        JLabel patientImage = createImageLabel("../images/patient.png");
        casePanel.add(patientImage, gbc);
        //JLabel titreLabel1 = new JLabel("Patient", SwingConstants.CENTER);
        //titreLabel1.setPreferredSize(new Dimension(100, 30));
        // casePanel.add(titreLabel1, gbc);

        gbc.gridx = 1; // Deuxième colonne
        JButton patientButton = createButton("Patient");
        casePanel.add(patientButton, gbc);

        // Ligne 2 : Image Spécialiste et Bouton Spécialiste
        gbc.gridx = 0; // Première colonne
        gbc.gridy = 1; // Deuxième ligne
        JLabel specialisteImage = createImageLabel("../images/specialiste.png");
        casePanel.add(specialisteImage, gbc);
        //JLabel titreLabel2 = new JLabel("Specialiste", SwingConstants.CENTER);
        //titreLabel2.setPreferredSize(new Dimension(100, 30));
        //casePanel.add(titreLabel2, gbc);


        gbc.gridx = 1; // Deuxième colonne
        JButton specialisteButton = createButton("Spécialiste");
        casePanel.add(specialisteButton, gbc);

        // Ligne 3 : Image Admin et Bouton Admin
        gbc.gridx = 0; // Première colonne
        gbc.gridy = 2; // Troisième ligne
        JLabel adminImage = createImageLabel("../images/admin.png");
        casePanel.add(adminImage, gbc);
        //JLabel titreLabel3 = new JLabel("Admin", SwingConstants.CENTER);
        //titreLabel3.setPreferredSize(new Dimension(100, 30));
        // casePanel.add(titreLabel3, gbc);

        gbc.gridx = 1; // Deuxième colonne
        JButton adminButton = createButton("Admin");
        casePanel.add(adminButton, gbc);

        // Rendre la fenêtre visible
        setVisible(true);

        // Actions au clic sur les boutons
        patientButton.addActionListener(e -> {
            new Connexion("patient");
            dispose(); // ferme la page d'accueil
        });

        specialisteButton.addActionListener(e -> {
            new Connexion("specialiste");
            dispose();
        });

        adminButton.addActionListener(e -> {
            new Connexion("admin");
            dispose();
        });
    }

    // Méthode pour créer un JLabel avec une image
    private JLabel createImageLabel(String imagePath) {
        JLabel label = new JLabel();
        try {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image img = icon.getImage();

                // 🔧 Redimensionne ici (ajuste width/height à ta convenance)
                Image resized = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

                label.setIcon(new ImageIcon(resized));
            } else {
                System.err.println("Image non trouvée : " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return label;
    }

    // Méthode pour créer un bouton avec un texte
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 75)); // Taille du bouton
        return button;
    }



}
