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

    public void initUI() {
        // Définir rôle & userText
        String role = "Non connecté";
        String userText = "Veuillez vous connecter.";

        if (utilisateurConnecte != null) {
            if (utilisateurConnecte instanceof Patient) {
                role = "Patient";
            } else if (utilisateurConnecte instanceof Specialiste) {
                role = "Spécialiste";
            }
            userText = utilisateurConnecte.getPrenom() + " " + utilisateurConnecte.getNom() + " (ID: " + utilisateurConnecte.getId() + ")";
        }

        userLabel = new JLabel(role + " : " + userText);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Georgia", Font.PLAIN, 14));

        setTitle("Application Rendez-vous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(166, 212, 240));

        // ---------- Titre MediLink à gauche ----------
        JLabel titreLabel = new JLabel("MediLink");
        titreLabel.setForeground(Color.WHITE);
        titreLabel.setFont(new Font("Brush Script MT", Font.BOLD, 50));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.add(titreLabel, BorderLayout.WEST);

        // ---------- Panel à droite avec infos utilisateur + bouton ----------
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false); // Pour laisser voir la couleur du bandeau

        if(utilisateurConnecte!=null){
            JButton logoutBtn = new JButton("Déconnexion");
            logoutBtn.addActionListener(e -> {
                dispose();
                new PageAccueil(); // ou "patient"/"specialiste" si tu veux pré-remplir
            });
            rightPanel.add(logoutBtn);
        }


        rightPanel.add(userLabel);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // ---------- Ajout du bandeau au main panel ----------
        mainPanel.add(topPanel, BorderLayout.NORTH);

        setVisible(true);
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
