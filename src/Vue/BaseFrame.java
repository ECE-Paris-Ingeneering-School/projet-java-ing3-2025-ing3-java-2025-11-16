package Vue;

import javax.swing.*;
import java.awt.*;

import Modele.*;

public class BaseFrame extends JFrame {

    private JLabel userLabel;
    protected Utilisateur utilisateurConnecte;
    protected JPanel centerPanel; // ✅ Le panel pour le contenu "hors bandeau"
    protected JPanel topPanel;

    public BaseFrame(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
        initUI();
    }

    public BaseFrame() {
        this(null);
    }

    public void initUI() {
        setTitle("Application Rendez-vous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(2000, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // ---------------- Bandeau top ----------------
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(54, 153, 213));

        JLabel titreLabel = new JLabel("MediLink");
        titreLabel.setForeground(Color.WHITE);
        titreLabel.setFont(new Font("Brush Script MT", Font.BOLD, 40));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        topPanel.add(titreLabel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Utilisez FlowLayout
        rightPanel.setOpaque(false);

        if (utilisateurConnecte != null) {
            JButton compteButton = new JButton("Mon compte");

            Font bigFont = new Font("Arial", Font.BOLD, 17);
            Dimension bigSize = new Dimension(200, 60);

            compteButton.setFont(bigFont);
            compteButton.setPreferredSize(bigSize);

            compteButton.setBackground(new Color(10, 98, 156));
            compteButton.setForeground(Color.WHITE);

            rightPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 0, 0, 5); // 50 pixels d'espace en haut

            JPopupMenu menuDeroulant = new JPopupMenu();
            // Ajoutez un ActionListener au bouton pour afficher le menu déroulant
            compteButton.addActionListener(e -> {
                menuDeroulant.show(compteButton, 0, compteButton.getHeight());
            });

            if(utilisateurConnecte instanceof Patient){
                JMenuItem profilItem = new JMenuItem("Profil");
                JMenuItem RDVItem = new JMenuItem("Mes Rendez-vous");

                menuDeroulant.add(profilItem);
                menuDeroulant.add(RDVItem);

                // Ajoutez des ActionListeners aux éléments du menu
                profilItem.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "Affichage du profil");
                });

                RDVItem.addActionListener(e -> {
                    dispose();
                    new RendezVousVue(utilisateurConnecte);
                });


            }
            else if (utilisateurConnecte instanceof Specialiste) {

                JMenuItem profilItem = new JMenuItem("Profil");
                JMenuItem RDVItem = new JMenuItem("Mes Rendez-vous");
                JMenuItem EdtItem = new JMenuItem("Emploi du temps");  // Modifier son edt

                menuDeroulant.add(profilItem);
                menuDeroulant.add(RDVItem);
                menuDeroulant.add(EdtItem);

                // Ajoutez des ActionListeners aux éléments du menu
                profilItem.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "Affichage du profil");
                });

                RDVItem.addActionListener(e -> {
                    dispose();
                    new RendezVousVue(utilisateurConnecte);
                });

            }
            else if(utilisateurConnecte instanceof Admin){
                JMenuItem profilItem = new JMenuItem("Profil");

                menuDeroulant.add(profilItem);

                // Ajoutez des ActionListeners aux éléments du menu
                profilItem.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "Affichage du profil");
                });


            }

            JMenuItem deconnexionItem = new JMenuItem("Déconnexion");
            menuDeroulant.add(deconnexionItem);
            deconnexionItem.addActionListener(e -> {
                dispose();
                new ChoixConnexion();
            });

            rightPanel.add(compteButton, gbc);

        }

        if (utilisateurConnecte == null) {
            userLabel = new JLabel("Non connecté");
            rightPanel.add(userLabel, BorderLayout.CENTER);
        }

        topPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        //  ---------------- Panel pour le contenu central ----------------
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }


    public JPanel getMainPanel() { return (JPanel) getContentPane();}

    public JPanel getCenterPanel() { return centerPanel;}

    public JPanel getNorthPanel() { return topPanel; }

    public void setUserText(String texte) {
        if (userLabel != null) {
            userLabel.setText("👤 " + texte);
        }
    }
}
