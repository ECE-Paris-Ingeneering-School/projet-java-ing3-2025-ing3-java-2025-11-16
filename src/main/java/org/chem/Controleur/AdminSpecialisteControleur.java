package org.chem.Controleur;

import org.chem.Modele.*;
import org.chem.Vue.*;
import org.chem.Dao.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Contrôleur pour l'administration des spécialistes.
 * Permet de gérer l'affichage, l'ajout, la modification et la suppression des spécialistes
 * ainsi que la gestion de leurs horaires et rendez-vous.
 */
public class AdminSpecialisteControleur extends JFrame {

    private final AdminSpecialisteVue vue;
    private final UtilisateurDAOImpl utilisateurDAO;
    private final HoraireDAOImpl horaireDAO;
    private final EdtDAOImpl edtDAO;
    private RendezVousDAOImpl rendezVousDAO;

    /**
     * Constructeur du contrôleur AdminSpecialisteControleur.
     *
     * @param vue Vue associée.
     * @param Specialistedao DAO pour la gestion des utilisateurs (spécialistes).
     * @param horaireDAO DAO pour la gestion des horaires.
     * @param edtDAO DAO pour la gestion des emplois du temps.
     * @param rendezVousDAO DAO pour la gestion des rendez-vous.
     */
    public AdminSpecialisteControleur(AdminSpecialisteVue vue, UtilisateurDAOImpl Specialistedao, HoraireDAOImpl horaireDAO, EdtDAOImpl edtDAO, RendezVousDAOImpl rendezVousDAO) {
        this.vue = vue;
        this.utilisateurDAO = Specialistedao;
        this.horaireDAO = horaireDAO;
        this.edtDAO = edtDAO;
        this.rendezVousDAO = rendezVousDAO;

        vue.getRechercherBtn().addActionListener(e -> rechercher());
        vue.getAjouterBtn().addActionListener(e -> ajouterSpecialiste());

        afficherTousLesSpecialistes();
    }

    /**
     * Recherche des spécialistes selon un filtre texte.
     */
    private void rechercher() {
        String filtre = vue.getRechercheField().getText().trim();
        ArrayList<Specialiste> liste = utilisateurDAO.rechercherSpecialistes(filtre, "", null, "Lieu");
        afficherSpecialistes(liste);
    }

    /**
     * Affiche tous les spécialistes existants.
     */
    private void afficherTousLesSpecialistes() {
        ArrayList<Specialiste> liste = utilisateurDAO.getAllSpecialistes();
        afficherSpecialistes(liste);
    }

    /**
     * Affiche la liste des spécialistes et leurs rendez-vous.
     *
     * @param liste Liste des spécialistes à afficher.
     */
    public void afficherSpecialistes(ArrayList<Specialiste> liste) {
        JPanel panel = vue.getListeSpecialistesPanel();
        panel.removeAll();

        JComboBox<String> jourCombo = new JComboBox<>();
        JComboBox<String> heureCombo = new JComboBox<>();

        for (Specialiste s : liste) {
            ArrayList<RendezVous> listRdv = rendezVousDAO.getRendezVousBySpecialiste(s.getId());
            PanelSpecialiste p = new PanelSpecialiste(s, listRdv);

            ArrayList<String> rdvFormates = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            if (listRdv.isEmpty()) {
                rdvFormates.add("Aucun rendez-vous.");
            } else {
                for (RendezVous rdv : listRdv) {
                    Utilisateur patient = utilisateurDAO.getById(rdv.getIdPatient());
                    Horaire h = horaireDAO.getHoraireById(rdv.getIdHoraire());

                    String nomPatient = patient.getPrenom() + " " + patient.getNom();
                    String jour = Horaire.convertirJourIntEnString(h.getJourSemaine());
                    String heure = h.getHeureDebut().toLocalTime().format(formatter) + " - " + h.getHeureFin().toLocalTime().format(formatter);

                    String texte = jour + " " + rdv.getDate() + " (" + heure + ") avec " + nomPatient + " : " + rdv.getNotes();
                    rdvFormates.add(texte);
                }
            }

            for (Component c : p.getEmploiDuTempsPanel().getComponents()) {
                if (c instanceof JPanel ligne) {
                    for (Component btn : ligne.getComponents()) {
                        if (btn instanceof JButton b) {
                            String cmd = b.getActionCommand();
                            if (cmd.startsWith("supprimer_")) ajouterActionSupprimerHoraire(b, s, cmd);
                            if (cmd.startsWith("modifier_")) ajouterActionModifierHoraire(b, s, cmd, jourCombo, heureCombo);
                        }
                    }
                }
            }

            ajouterActionModifierSpecialiste(p.getBtnModifierSpecialiste(), s);
            ajouterActionSupprimerSpecialiste(p.getBtnSupprimerSpecialiste(), s);
            ajouterActionAjouterHoraire(p.getBtnAjouterHoraire(), s, jourCombo, heureCombo);

            p.afficherRendezVous(rdvFormates);

            panel.add(p);
            panel.revalidate();
            panel.repaint();
        }
    }

    /**
     * Permet d'ajouter un nouveau spécialiste via une boîte de dialogue.
     */
    private void ajouterSpecialiste() {
        JTextField nomField = new JTextField(20);
        JTextField prenomField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField mdpField = new JPasswordField(20);
        JTextField specialiteField = new JTextField(20);
        JTextField lieuField = new JTextField(20);

        JPanel panelAjoutSpecialiste = vue.creerPanelInfoSpecialiste(nomField, prenomField, emailField, mdpField, specialiteField, lieuField);

        int result = JOptionPane.showConfirmDialog(null, panelAjoutSpecialiste, "Ajouter un spécialiste", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            String email = emailField.getText().trim();
            String mdp = new String(mdpField.getPassword()).trim();
            String specialite = specialiteField.getText().trim();
            String lieu = lieuField.getText().trim();

            if (!nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty() && !mdp.isEmpty() && !specialite.isEmpty() && !lieu.isEmpty()) {
                Specialiste s = new Specialiste(nom, prenom, email, mdp, specialite, lieu);
                utilisateurDAO.ajouter(s);
                afficherTousLesSpecialistes();
            } else {
                JOptionPane.showMessageDialog(null, "Tous les champs ne sont pas remplis !");
            }
        }
    }

    /**
     * Rafraîchit l'affichage selon le filtre ou affiche tout.
     */
    private void refresh() {
        if (!vue.getRechercheField().getText().trim().isEmpty()) {
            rechercher();
        } else {
            afficherTousLesSpecialistes();
        }
    }

    /**
     * Ajoute l'action de suppression d'un horaire au bouton.
     *
     * @param bouton Bouton de suppression.
     * @param s      Spécialiste concerné.
     * @param cmd    Commande associée contenant l'ID de l'horaire.
     */
    private void ajouterActionSupprimerHoraire(JButton bouton, Specialiste s, String cmd) {
        bouton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Supprimer cet horaire ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int idHoraire = Integer.parseInt(cmd.replace("supprimer_", ""));
                boolean success = edtDAO.supprimerLienSpecialisteHoraire(s.getId(), idHoraire);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Horaire supprimé !");
                    refresh();
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression.");
                }
            }
        });
    }

    /**
     * Ajoute l'action de modification d'un horaire au bouton.
     *
     * @param bouton     Bouton de modification.
     * @param s          Spécialiste concerné.
     * @param cmd        Commande associée contenant l'ID de l'ancien horaire.
     * @param jourCombo  ComboBox des jours.
     * @param heureCombo ComboBox des heures.
     */
    private void ajouterActionModifierHoraire(JButton bouton, Specialiste s, String cmd, JComboBox<String> jourCombo, JComboBox<String> heureCombo) {
        bouton.addActionListener(e -> {
            JPanel panelChoixModifier = vue.creerPanelChoixHoraire(jourCombo, heureCombo);
            int result = JOptionPane.showConfirmDialog(null, panelChoixModifier, "Choisir un créneau", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String jour = (String) jourCombo.getSelectedItem();
                String heure = (String) heureCombo.getSelectedItem();
                int ancienIdHoraire = Integer.parseInt(cmd.replace("modifier_", ""));

                try {
                    int jourInt = Horaire.convertirJourEnInt(jour);
                    Time heureDebut = Time.valueOf(LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm")));
                    int newIdHoraire = horaireDAO.getIdHoraireExistant(jourInt, heureDebut);

                    if (newIdHoraire == -1) {
                        JOptionPane.showMessageDialog(null, "Aucun horaire trouvé avec ce jour et cette heure.");
                        return;
                    }

                    boolean success = edtDAO.modifierLienHoraireSpecialiste(s.getId(), ancienIdHoraire, newIdHoraire);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Horaire modifié !");
                        refresh();
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

    /**
     * Ajoute l'action de modification d'un spécialiste.
     *
     * @param bouton Bouton de modification.
     * @param s      Spécialiste concerné.
     */
    private void ajouterActionModifierSpecialiste(JButton bouton, Specialiste s) {
        bouton.addActionListener(e -> {
            JTextField nomField = new JTextField(s.getNom(), 20);
            JTextField prenomField = new JTextField(s.getPrenom(), 20);
            JTextField emailField = new JTextField(s.getEmail(), 20);
            JPasswordField mdpField = new JPasswordField(20);
            JTextField specialiteField = new JTextField(s.getSpecialisation(), 20);
            JTextField lieuField = new JTextField(s.getLieu(), 20);

            JPanel panelModif = vue.creerPanelInfoSpecialiste(nomField, prenomField, emailField, mdpField, specialiteField, lieuField);

            int result = JOptionPane.showConfirmDialog(null, panelModif, "Modifier le spécialiste", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                String email = emailField.getText().trim();
                String mdp = new String(mdpField.getPassword()).trim();
                String specialite = specialiteField.getText().trim();
                String lieu = lieuField.getText().trim();

                if (!nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty() && !specialite.isEmpty() && !lieu.isEmpty()) {
                    String motDePasseFinal = mdp.isEmpty() ? s.getMdp() : mdp;

                    Specialiste sModifie = new Specialiste(nom, prenom, email, motDePasseFinal, specialite, lieu);
                    sModifie.setId(s.getId());
                    utilisateurDAO.modifier(sModifie);
                    refresh();
                } else {
                    JOptionPane.showMessageDialog(null, "Tous les champs ne sont pas remplis !");
                }
            }
        });
    }

    /**
     * Ajoute l'action de suppression d'un spécialiste.
     *
     * @param bouton Bouton de suppression.
     * @param s      Spécialiste à supprimer.
     */
    private void ajouterActionSupprimerSpecialiste(JButton bouton, Specialiste s) {
        bouton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Supprimer ce spécialiste ?", "Confirmer", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                utilisateurDAO.supprimer(s);
                refresh();
            }
        });
    }

    /**
     * Ajoute un horaire à un spécialiste.
     *
     * @param bouton     Bouton d'ajout.
     * @param s          Spécialiste concerné.
     * @param jourCombo  ComboBox des jours.
     * @param heureCombo ComboBox des heures.
     */
    private void ajouterActionAjouterHoraire(JButton bouton, Specialiste s, JComboBox<String> jourCombo, JComboBox<String> heureCombo) {
        bouton.addActionListener(e -> {
            JPanel panelChoixAjout = vue.creerPanelChoixHoraire(jourCombo, heureCombo);
            int result = JOptionPane.showConfirmDialog(null, panelChoixAjout, "Choisir un créneau", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String jour = (String) jourCombo.getSelectedItem();
                String heure = (String) heureCombo.getSelectedItem();

                try {
                    int jourInt = Horaire.convertirJourEnInt(jour);
                    Time heureDebut = Time.valueOf(LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm")));
                    int idHoraire = horaireDAO.getIdHoraireExistant(jourInt, heureDebut);

                    if (idHoraire == -1) {
                        JOptionPane.showMessageDialog(null, "Aucun horaire trouvé.");
                        return;
                    }

                    boolean success = edtDAO.ajouterLienSpecialisteHoraire(s.getId(), idHoraire);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Horaire ajouté !");
                        refresh();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ce créneau est déjà présent.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout.");
                }
            }
        });
    }

}
