package org.chem.Vue;

import org.chem.Modele.RendezVous;
import org.chem.Modele.Utilisateur;
import org.chem.Controleur.RendezVousController;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RendezVousVue extends BaseFrame {
    private final RendezVousController controller;
    private final JTextArea affichage;

    public RendezVousVue(Utilisateur utilisateur) {
        super(utilisateur);
        this.controller = new RendezVousController();

        setTitle("Gestion des Rendez-Vous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // --- Zone centrale ---
        affichage = new JTextArea();
        affichage.setEditable(false);
        affichage.setFont(new Font("Monospaced", Font.PLAIN, 15));
        affichage.setBackground(new Color(248, 248, 248));
        affichage.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(affichage);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(1000, 600));

        add(scrollPane, BorderLayout.CENTER);

        // --- Footer : boutons ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        JButton btnLister = new JButton("📄 Lister les rendez-vous");
        JButton btnAjouter = new JButton("➕ Ajouter un rendez-vous");

        btnLister.setPreferredSize(new Dimension(240, 40));
        btnAjouter.setPreferredSize(new Dimension(240, 40));

        footer.add(btnLister);
        footer.add(btnAjouter);
        add(footer, BorderLayout.SOUTH);

        // --- Actions ---
        btnLister.addActionListener(e -> listerRendezVous());
        btnAjouter.addActionListener(e -> ajouterRendezVous());

        setVisible(true);
    }

    private void listerRendezVous() {
        affichage.setText("");
        List<RendezVous> rdvs = controller.getRendezVous();
        if (rdvs.isEmpty()) {
            affichage.append("⚠ Aucun rendez-vous trouvé.\n");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            for (RendezVous rdv : rdvs) {
                affichage.append(
                        "🗓 RDV ID " + rdv.getId() +
                                " | Patient #" + rdv.getIdPatient() +
                                " | Spécialiste #" + rdv.getIdSpecialiste() +
                                " | Horaire #" + rdv.getIdHoraire() +
                                " | 📍 " + rdv.getLieu() +
                                "\n    Date : " + sdf.format(rdv.getDate()) +
                                "\n    Notes : " + rdv.getNotes() + "\n\n"
                );
            }
        }
    }

    private void ajouterRendezVous() {
        try {
            String idPatientStr = JOptionPane.showInputDialog(this, "ID Patient :");
            String idSpecialisteStr = JOptionPane.showInputDialog(this, "ID Spécialiste :");
            String idHoraireStr = JOptionPane.showInputDialog(this, "ID Horaire :");
            String dateStr = JOptionPane.showInputDialog(this, "Date (yyyy-MM-dd HH:mm) :");
            String notes = JOptionPane.showInputDialog(this, "Notes :");
            String lieu = JOptionPane.showInputDialog(this, "Lieu :");

            if (idPatientStr == null || idSpecialisteStr == null || idHoraireStr == null || dateStr == null || lieu == null)
                return;

            int idPatient = Integer.parseInt(idPatientStr);
            int idSpecialiste = Integer.parseInt(idSpecialisteStr);
            int idHoraire = Integer.parseInt(idHoraireStr);
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr);

            RendezVous rdv = new RendezVous(idSpecialiste, idPatient, idHoraire, date, notes, lieu);
            boolean success = controller.ajouterRendezVous(rdv);

            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Rendez-vous ajouté !");
                listerRendezVous();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Échec de l'ajout.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }
}
