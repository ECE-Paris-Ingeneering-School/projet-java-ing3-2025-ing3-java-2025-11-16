package org.chem.Controleur;

import org.chem.Modele.*;
import org.chem.Vue.*;
import org.chem.Dao.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdminSpecialisteControleur extends JFrame {

    private final AdminSpecialisteVue vue;
    private final UtilisateurDAOImpl specialisteDAO;
    private final HoraireDAOImpl horaireDAO;
    private final EdtDAOImpl edtDAO;

    public AdminSpecialisteControleur(AdminSpecialisteVue vue, UtilisateurDAOImpl Specialistedao, HoraireDAOImpl horaireDAO,EdtDAOImpl edtDAO) {
        this.vue = vue;
        this.specialisteDAO = Specialistedao;
        this.horaireDAO = horaireDAO;
        this.edtDAO = edtDAO;

        // Initialisation des listeners
        vue.getRechercherBtn().addActionListener(e -> rechercher());
        vue.getAjouterBtn().addActionListener(e -> ajouterSpecialiste());

        // Chargement initial
        afficherTousLesSpecialistes();
    }

    private void rechercher() {
        String filtre = vue.getRechercheField().getText().trim();
        ArrayList<Specialiste> liste = specialisteDAO.rechercherSpecialistes(filtre,"",null,"Lieu");
        afficherSpecialistes(liste);
    }

    private void afficherTousLesSpecialistes() {
        ArrayList<Specialiste> liste = specialisteDAO.getAllSpecialistes();
        afficherSpecialistes(liste);
    }

    private void afficherSpecialistes(ArrayList<Specialiste> liste) {
        JPanel panel = vue.getListeSpecialistesPanel();
        panel.removeAll();

        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        JComboBox<String> jourCombo = new JComboBox<>(jours);

        String[] horaires = {"08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00"};
        JComboBox<String> heureCombo = new JComboBox<>(horaires);

        JPanel panelChoix = new JPanel(new GridLayout(2, 2));
        panelChoix.add(new JLabel("Jour de la semaine :"));
        panelChoix.add(jourCombo);
        panelChoix.add(new JLabel("Heure de début :"));
        panelChoix.add(heureCombo);

        if (liste.isEmpty()) {
            panel.add(new JLabel("Aucun spécialiste trouvé."));
        } else {
            for (Specialiste s : liste) {

                PanelSpecialiste p = new PanelSpecialiste(s);

                for (Component c : p.getEmploiDuTempsPanel().getComponents()) {

                    if (c instanceof JPanel ligne) {
                        for (Component btn : ligne.getComponents()) {

                            if (btn instanceof JButton b) {
                                String cmd = b.getActionCommand();

                                if (cmd.startsWith("supprimer_")) {
                                    b.addActionListener(e -> {
                                        int confirm = JOptionPane.showConfirmDialog(null, "Supprimer cet horaire ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                        if (confirm == JOptionPane.YES_OPTION) {
                                            int idHoraire = Integer.parseInt(cmd.replace("supprimer_", ""));
                                            // Supprime dans EDT
                                            boolean success = edtDAO.supprimerLienSpecialisteHoraire(s.getId(), idHoraire);
                                            if (success) {
                                                JOptionPane.showMessageDialog(null, "Horaire supprimé !");
                                                afficherTousLesSpecialistes(); // refresh
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression.");
                                            }
                                        }
                                    });
                                }

                                if (cmd.startsWith("modifier_")) {

                                    b.addActionListener(e -> {

                                        int result = JOptionPane.showConfirmDialog(null, panelChoix, "Choisir un créneau", JOptionPane.OK_CANCEL_OPTION);

                                        if (result == JOptionPane.OK_OPTION) {
                                            String jour = (String) jourCombo.getSelectedItem();
                                            String heure = (String) heureCombo.getSelectedItem();
                                            int ancienIdHoraire = Integer.parseInt(cmd.replace("modifier_", ""));

                                            try {
                                                int jourInt = Horaire.convertirJourEnInt(jour);
                                                Time heureDebut = Time.valueOf(LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm")));

                                                // Étape 1 : chercher l’ID de l’horaire existant
                                                int newIdHoraire = horaireDAO.getIdHoraireExistant(jourInt, heureDebut);

                                                if (newIdHoraire == -1) {
                                                    JOptionPane.showMessageDialog(null, "Aucun horaire trouvé avec ce jour et cette heure.");
                                                    return;
                                                }

                                                // Étape 2 : modifier l'horaire dans l'edt
                                                boolean success = edtDAO.modifierLienHoraireSpecialiste(s.getId(), ancienIdHoraire, newIdHoraire);

                                                if (success) {
                                                    JOptionPane.showMessageDialog(null, "Horaire modifier dans l'emploi du temps !");
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Ce créneau est déjà dans l'emploi du temps.");
                                                }

                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                                JOptionPane.showMessageDialog(null, "Erreur lors de la modification.");
                                            }
                                        }

                                    });
                                }
                            }
                        }
                    }


                    // Bouton supprimer
                    p.getBtnSupprimerSpecialiste().addActionListener(e -> {
                        int confirm = JOptionPane.showConfirmDialog(null, "Supprimer ce spécialiste ?", "Confirmer", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            specialisteDAO.supprimer(s);
                            afficherTousLesSpecialistes();
                        }
                    });

                    // Bouton ajouter horaire
                    p.getBtnAjouterHoraire().addActionListener(e -> {

                        int result = JOptionPane.showConfirmDialog(null, panelChoix, "Choisir un créneau", JOptionPane.OK_CANCEL_OPTION);

                        if (result == JOptionPane.OK_OPTION) {
                            String jour = (String) jourCombo.getSelectedItem();
                            String heure = (String) heureCombo.getSelectedItem();

                            try {
                                int jourInt = Horaire.convertirJourEnInt(jour);
                                Time heureDebut = Time.valueOf(LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm")));

                                // Étape 1 : chercher l’ID de l’horaire existant
                                int idHoraire = horaireDAO.getIdHoraireExistant(jourInt, heureDebut);

                                if (idHoraire == -1) {
                                    JOptionPane.showMessageDialog(null, "Aucun horaire trouvé avec ce jour et cette heure.");
                                    return;
                                }

                                // Étape 2 : ajouter dans edt
                                boolean success = edtDAO.ajouterLienSpecialisteHoraire(s.getId(), idHoraire);

                                if (success) {
                                    JOptionPane.showMessageDialog(null, "Horaire ajouté à l'emploi du temps !");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Ce créneau est déjà dans l'emploi du temps.");
                                }

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout.");
                            }
                        }

                    });
                    panel.add(p);
                }
            }

            panel.revalidate();
            panel.repaint();
        }
    }


    private void ajouterSpecialiste() {
        JOptionPane.showMessageDialog(null, "Pop-up d'ajout à implémenter !");
        // Tu peux ici afficher une JFrame ou un JDialog pour saisir les infos
    }

}
