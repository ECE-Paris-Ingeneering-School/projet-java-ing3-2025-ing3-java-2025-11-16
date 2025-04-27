package org.chem.Vue;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

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


        //  ---------------- CONTENU CENTRAL ----------------
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

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
