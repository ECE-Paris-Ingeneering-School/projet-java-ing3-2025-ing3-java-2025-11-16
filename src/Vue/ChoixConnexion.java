package Vue;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ChoixConnexion extends BaseFrame {

    private JButton patientButton;
    private JButton specialisteButton;
    private JButton adminButton;

    public ChoixConnexion() {
        super();

        JPanel contenuPanel = getCenterPanel();

        JLabel titreLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        contenuPanel.add(titreLabel, BorderLayout.NORTH);

        JPanel casePanel = new JPanel(new GridBagLayout());
        contenuPanel.add(casePanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        casePanel.add(createImageLabel("../images/patient.png"), gbc);
        gbc.gridx = 1;
        patientButton = createButton("Patient");
        casePanel.add(patientButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        casePanel.add(createImageLabel("../images/specialiste.png"), gbc);
        gbc.gridx = 1;
        specialisteButton = createButton("Spécialiste");
        casePanel.add(specialisteButton, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        casePanel.add(createImageLabel("../images/admin.png"), gbc);
        gbc.gridx = 1;
        adminButton = createButton("Admin");
        casePanel.add(adminButton, gbc);

        setVisible(true);
    }

    private JLabel createImageLabel(String imagePath) {
        JLabel label = new JLabel();
        try {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image img = icon.getImage();
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

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 75));
        return button;
    }

    // 👉 Getters pour accès aux boutons
    public JButton getPatientButton() { return patientButton; }
    public JButton getSpecialisteButton() { return specialisteButton; }
    public JButton getAdminButton() { return adminButton; }
}
