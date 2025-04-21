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
import java.util.List;

public class AdminSpecialisteControleur extends JFrame {

    private final AdminSpecialisteVue vue;
    private final UtilisateurDAOImpl specialisteDAO;
    private final HoraireDAOImpl horaireDAO;

    public AdminSpecialisteControleur(AdminSpecialisteVue vue, UtilisateurDAOImpl Specialistedao, HoraireDAOImpl horaireDAO) {
        this.vue = vue;
        this.specialisteDAO = Specialistedao;
        this.horaireDAO = horaireDAO;

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
                    // Liste des jours
                    String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi","Samedi"};
                    JComboBox<String> jourCombo = new JComboBox<>(jours);

                    // Liste des horaires prédéfinis
                    String[] horaires = {"08:00", "09:00", "10:30", "13:00", "14:30", "16:00"};
                    JComboBox<String> heureCombo = new JComboBox<>(horaires);

                    JPanel panelchoix = new JPanel(new GridLayout(2, 2));
                    panelchoix.add(new JLabel("Jour de la semaine :"));
                    panelchoix.add(jourCombo);
                    panelchoix.add(new JLabel("Horaire :"));
                    panelchoix.add(heureCombo);

                    int result = JOptionPane.showConfirmDialog(
                            null,
                            panelchoix,
                            "Ajouter un horaire pour " + s.getNom(),
                            JOptionPane.OK_CANCEL_OPTION
                    );

                    if (result == JOptionPane.OK_OPTION) {
                        String jour = (String) jourCombo.getSelectedItem();
                        String heure = (String) heureCombo.getSelectedItem();

                        try{

                        int jourInt = Horaire.convertirJourEnInt(jour);

                        // Conversion de l'heure de début
                        LocalTime heureDebutLT = LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm"));
                        LocalTime heureFinLT = heureDebutLT.plusHours(1); // ajoute 1h

                        // Conversion en java.sql.Time
                        Time heureDebut = Time.valueOf(heureDebutLT);
                        Time heureFin = Time.valueOf(heureFinLT);

                        // Création de l'objet Horaire
                        Horaire h = new Horaire(jourInt, heureDebut, heureFin);
                        System.out.println("Horaire ajouté : Jour " + jour + " de " + heureDebut + " à " + heureFin);

                        horaireDAO.ajouterHoraire(h);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erreur lors de la création de l'horaire.");
                        }
                    } else {
                        System.out.println("Ajout annulé.");
                    }
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
