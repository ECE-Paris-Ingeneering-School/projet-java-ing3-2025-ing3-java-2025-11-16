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
        setSize(2000, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // ---------------- Bandeau top ----------------
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(54, 153, 213));

        JLabel titreLabel = new JLabel("MediLink");
        titreLabel.setForeground(Color.WHITE);
        titreLabel.setFont(new Font("Brush Script MT", Font.BOLD, 50));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        topPanel.add(titreLabel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        if(utilisateurConnecte != null){
            JButton logoutBtn = new JButton("Déconnexion");
            logoutBtn.addActionListener(e -> {
                dispose();
                new PageAccueil();
            });
            rightPanel.add(logoutBtn);
        }

        rightPanel.add(userLabel);
        topPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);


        //  ---------------- Panel pour le contenu central ----------------
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public JPanel getMainPanel() {
        return (JPanel) getContentPane();
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JPanel getNorthPanel() { return topPanel; }

    public void setUserText(String texte) {
        if (userLabel != null) {
            userLabel.setText("👤 " + texte);
        }
    }
}
