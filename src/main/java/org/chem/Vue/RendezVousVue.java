package org.chem.Vue;

import org.chem.Modele.Patient;
import org.chem.Modele.RendezVous;
import org.chem.Modele.Utilisateur;
import org.chem.Dao.RendezVousDAO;
import org.chem.Dao.DatabaseConnection;
import org.chem.Modele.Horaire;

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
            JOptionPane.showMessageDialog(this, "Patients peuvent consulter les rdv.");
        }
    }


    public void afficherRendezVous(int idPatient) {
        DatabaseConnection db = DatabaseConnection.getDefaultInstance();
        RendezVousDAO rdvDao = db.getRendezVousDAO();
        List<RendezVous> rdvs = rdvDao.getRendezVousByPatient(idPatient);

        String[] colonnes = {"ID", "Date", "Heure", "Spécialiste", "Lieu", "Notes"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        for (RendezVous rdv : rdvs) {
            Utilisateur specialiste = db.getUtilisateurDAO().getById(rdv.getIdSpecialiste());


            String heure = "";
            if (specialiste != null && specialiste instanceof org.chem.Modele.Specialiste specialisteCast) {
                for (var h : specialisteCast.getEmploiDuTemps()) {
                    if (h.getId() == rdv.getIdHoraire()) {
                        String heureDebut;
                        if (h.getHeureDebut() != null) {
                            heureDebut = h.getHeureDebut().toString();
                        } else {
                            heureDebut = "Inconnue";
                        }
                        String heureFin;
                        if (h.getHeureFin() != null) {
                            heureFin = h.getHeureFin().toString();
                        } else {
                            heureFin = "Inconnue";
                        }
                    heure = heureDebut + " - " + heureFin;
                    break;
                    }
                }
            }

            Object[] ligne = {
                    rdv.getId(),
                    rdv.getDate().toString(),
                    heure,
                    (specialiste != null) ? specialiste.getNom() + " " + specialiste.getPrenom() : "Inconnu",
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


