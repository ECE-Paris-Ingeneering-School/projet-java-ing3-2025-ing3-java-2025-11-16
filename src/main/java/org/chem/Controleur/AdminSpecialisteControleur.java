package org.chem.Controleur;

import org.chem.Modele.*;
import org.chem.Vue.*;
import org.chem.Dao.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminSpecialisteControleur extends JFrame {

    private final AdminSpecialisteVue vue;
    private final UtilisateurDAOImpl specialisteDAO;

    public AdminSpecialisteControleur(AdminSpecialisteVue vue, UtilisateurDAOImpl dao) {
        this.vue = vue;
        this.specialisteDAO = dao;

        // Initialisation des listeners
        vue.getRechercherBtn().addActionListener(e -> rechercher());
        vue.getAjouterBtn().addActionListener(e -> ajouterSpecialiste());

        // Chargement initial
        afficherTousLesSpecialistes();
    }

    private void rechercher() {
        String filtre = vue.getRechercheField().getText().trim();
        List<Specialiste> liste = specialisteDAO.rechercherSpecialistes(filtre,"",null,"Lieu");
        afficherSpecialistes(liste);
    }

    private void afficherTousLesSpecialistes() {
        List<Specialiste> liste = specialisteDAO.getAllSpecialistes();
        afficherSpecialistes(liste);
    }

    private void afficherSpecialistes(List<Specialiste> liste) {
        JPanel panel = vue.getListeSpecialistesPanel();
        panel.removeAll();

        if (liste.isEmpty()) {
            panel.add(new JLabel("Aucun spécialiste trouvé."));
        } else {
            for (Specialiste s : liste) {
                PanelSpecialiste p = new PanelSpecialiste(s);

                // Bouton supprimer
                p.getBtnSupprimer().addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(null, "Supprimer ce spécialiste ?", "Confirmer", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        specialisteDAO.supprimer(s);
                        afficherTousLesSpecialistes();
                    }
                });

                // Bouton ajouter horaire
                p.getBtnAjouterHoraire().addActionListener(e -> {
                    // Ici tu peux faire une pop-up pour saisir un horaire
                    JOptionPane.showMessageDialog(null, "Ajout d'horaire pour : " + s.getNom());
                });

                panel.add(p);
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    private void ajouterSpecialiste() {
        JOptionPane.showMessageDialog(null, "Pop-up d'ajout à implémenter !");
        // Tu peux ici afficher une JFrame ou un JDialog pour saisir les infos
    }
}
