package Vue;

import Dao.*;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class Recherche extends JFrame {

    private JTextField motCleField;
    private JComboBox<String> jourCombo;
    private JTextField heureField;
    private JButton rechercherBtn;
    private JTextArea resultArea;

    private UtilisateurDAOImpl specialisteDAO;

    public Recherche(Utilisateur utilisateur) {
        //super(utilisateur);

        //JPanel mainPanel = getMainPanel();

        setTitle("Application Rendez-vous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");
        this.specialisteDAO = new UtilisateurDAOImpl(db); // DAO à implémenter

        JPanel recherchePanel = new JPanel(new GridLayout(4, 2, 10, 10));

        motCleField = new JTextField();
        heureField = new JTextField();

        String[] jours = {"", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        jourCombo = new JComboBox<>(jours);

        rechercherBtn = new JButton("Rechercher");
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);

        recherchePanel.add(new JLabel("Mot-clé (nom, spécialité, lieu) :"));
        recherchePanel.add(motCleField);
        recherchePanel.add(new JLabel("Jour :"));
        recherchePanel.add(jourCombo);
        recherchePanel.add(new JLabel("Heure (HH:MM) :"));
        recherchePanel.add(heureField);
        recherchePanel.add(new JLabel());
        recherchePanel.add(rechercherBtn);

        add(recherchePanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        rechercherBtn.addActionListener(e -> rechercher());

        setLocationRelativeTo(null); // Centrer la fenêtre
        setVisible(true);
    }

    private void rechercher() {
        String motCle = motCleField.getText().trim();
        String jour = null;
        String heureStr = heureField.getText().trim();

        Time heure = null;

        if (jourCombo.getSelectedItem() != null && !jourCombo.getSelectedItem().toString().isEmpty()) {
            jour = jourCombo.getSelectedItem().toString().toLowerCase();
        }

        if (!heureStr.isEmpty()) {
            try {
                heure = Time.valueOf(heureStr + ":00");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Format d'heure incorrect (ex: 09:30)");
                return;
            }
        }

        ArrayList<Specialiste> resultats = specialisteDAO.rechercherSpecialistes(motCle, jour, heure);
        afficherResultats(resultats);
    }


    private void afficherResultats(ArrayList<Specialiste> ListeSpecialistes) {
        resultArea.setText("");
        if (ListeSpecialistes.isEmpty()) {
            resultArea.append("Aucun spécialiste trouvé.\n");
        } else {
            for (int i = 0; i < ListeSpecialistes.size(); i++) {
                Specialiste s = ListeSpecialistes.get(i);
                resultArea.append(s.getPrenom() + " " + s.getNom() +
                        " - " + s.getSpecialisation() + " - " + s.getLieu() + "\n");
            }

        }
    }
}