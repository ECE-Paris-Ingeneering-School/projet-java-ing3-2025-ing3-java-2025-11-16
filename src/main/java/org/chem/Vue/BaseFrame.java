package org.chem.Vue;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import org.chem.Controleur.*;
import org.chem.Modele.Admin;
import org.chem.Modele.Patient;
import org.chem.Modele.Specialiste;
import org.chem.Modele.Utilisateur;

public class BaseFrame extends JFrame {

    private JLabel userLabel;
    protected Utilisateur utilisateurConnecte;
    protected JPanel centerPanel;
    protected JPanel topPanel;
    protected JPanel bottomPanel;

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
        setSize(1280, 720);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // ---------------- HEADER ----------------
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#649FCB"));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);

        try {
            InputStream is = getClass().getResourceAsStream("/images/logo.png");
            if (is != null) {
                BufferedImage logo = ImageIO.read(is);
                JLabel logoLabel = new JLabel(new ImageIcon(logo.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
                leftPanel.add(logoLabel);
            }
        } catch (Exception e) {
            System.out.println("Erreur chargement du logo");
        }

        JLabel titreLabel = new JLabel("MediLink");
        titreLabel.setForeground(Color.WHITE);
        titreLabel.setFont(new Font("SansSerif", Font.BOLD, 45));
        leftPanel.add(titreLabel);
        topPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Utilisez FlowLayout
        rightPanel.setOpaque(false);

        if (utilisateurConnecte != null) {
            JButton compteButton = new JButton("<html><b>Mon compte</b><br><i>" + utilisateurConnecte.getPrenom() + " " + utilisateurConnecte.getNom() + "</i></html>");

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
                JMenuItem RechercheItem = new JMenuItem("Rechercher");
                JMenuItem RDVItem = new JMenuItem("Mes Rendez-vous");

                menuDeroulant.add(profilItem);
                menuDeroulant.add(RechercheItem);
                menuDeroulant.add(RDVItem);

                profilItem.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, utilisateurConnecte.getPrenom()+" "+utilisateurConnecte.getNom()+"\n email : "+utilisateurConnecte.getEmail());
                });

                RechercheItem.addActionListener(e -> {
                    dispose();
                    new RechercheControleur(new Recherche());
                });

                RDVItem.addActionListener(e -> {
                    dispose();
                    new RendezVousController(new RendezVousVue(utilisateurConnecte));
                });



            }
            else if (utilisateurConnecte instanceof Specialiste) {

                JMenuItem profilItem = new JMenuItem("Profil");
                JMenuItem RDVItem = new JMenuItem("Mes Rendez-vous");
                JMenuItem EdtItem = new JMenuItem("Emploi du temps");

                menuDeroulant.add(profilItem);
                menuDeroulant.add(RDVItem);
                menuDeroulant.add(EdtItem);

                profilItem.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, utilisateurConnecte.getPrenom()+" "+utilisateurConnecte.getNom()+"\n email : "+utilisateurConnecte.getEmail());
                });

                RDVItem.addActionListener(e -> {
                    dispose();
                    new RendezVousVue(utilisateurConnecte);
                });

            }
            else if(utilisateurConnecte instanceof Admin) {

                JMenuItem profilItem = new JMenuItem("Profil");
                JMenuItem actionItem = new JMenuItem("Gérer patients et specialiste");

                menuDeroulant.add(profilItem);
                menuDeroulant.add(actionItem);

                // Ajoutez des ActionListeners aux éléments du menu
                profilItem.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, utilisateurConnecte.getPrenom()+" "+utilisateurConnecte.getNom()+"\n email : "+utilisateurConnecte.getEmail());
                });

                actionItem.addActionListener(e -> {
                    dispose();
                    new AdminControleur(new AdminVue(utilisateurConnecte));
                });
            }

            JMenuItem deconnexionItem = new JMenuItem("Déconnexion");
            menuDeroulant.add(deconnexionItem);
            deconnexionItem.addActionListener(e -> {
                dispose();
                new ChoixConnexionControleur(new ChoixConnexion());
            });

            rightPanel.add(compteButton, gbc);

        }
        topPanel.add(rightPanel, BorderLayout.EAST);

        //  ---------------- CONTENU CENTRAL ----------------
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JPanel getMainPanel() {return (JPanel) getContentPane();}

    public JPanel getCenterPanel() { return centerPanel;}

    public JPanel getNorthPanel() { return topPanel; }

    public JPanel getSouthPanel() {return bottomPanel;}

    public void setUserText(String texte) {
        if (userLabel != null) {
            userLabel.setText(" " + texte);
        }
    }
}
