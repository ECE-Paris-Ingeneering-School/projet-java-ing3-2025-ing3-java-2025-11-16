package org.chem.Vue;

import org.chem.Modele.Patient;
import org.chem.Modele.RendezVous;
import org.chem.Modele.Utilisateur;
import org.chem.Dao.RendezVousDAO;
import org.chem.Dao.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RendezVousVue extends BaseFrame {

    private JTable rdvTable;

    public RendezVousVue(Utilisateur utilisateurConnecte) {
        super(utilisateurConnecte);

        if (utilisateurConnecte instanceof Patient patient) {
            afficherRendezVous(patient.getId());
        } else {
            JOptionPane.showMessageDialog(this, "Seuls les patients peuvent consulter leurs rendez-vous.");
        }
    }


    public void afficherRendezVous(int idPatient) {
        DatabaseConnection db = DatabaseConnection.getInstance("medilink", "root", "");
        RendezVousDAO rdvDao = db.getRendezVousDAO();
        List<RendezVous> rdvs = rdvDao.getRendezVousByPatient(idPatient);

        String[] colonnes = {"ID", "Date", "Heure", "Spécialiste", "Lieu", "Notes"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        for (RendezVous rdv : rdvs) {
            Object[] ligne = {
                    rdv.getId(),
                    rdv.getDate().toString(),
                    "IDHoraire #" + rdv.getIdHoraire(),
                    "IDSpécialiste #" + rdv.getIdSpecialiste(),
                    rdv.getLieu(),
                    rdv.getNotes()
            };
            model.addRow(ligne);
        }

        rdvTable = new JTable(model);
        rdvTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(rdvTable);
        getCenterPanel().removeAll();
        getCenterPanel().add(scrollPane, BorderLayout.CENTER);
        getCenterPanel().revalidate();
        getCenterPanel().repaint();
    }

}


