package org.chem.Controleur;

import org.chem.Dao.*;
import org.chem.Vue.*;

import javax.swing.*;

/**
 * Contrôleur principal pour l'administration.
 * <p>
 * Il initialise l'interface graphique d'administration en créant et en liant
 * les vues et contrôleurs pour la gestion des patients et des spécialistes.
 * </p>
 */
public class AdminControleur {

    /** Vue principale de l'administration. */
    private AdminVue adminVue;

    /**
     * Constructeur du contrôleur d'administration.
     *
     * @param adminVue la vue d'administration associée
     */
    public AdminControleur(AdminVue adminVue) {
        this.adminVue = adminVue;
        initialiserOnglets(); // Organisation des panneaux dans l'interface
    }

    /**
     * Initialise les différents onglets de l'interface d'administration :
     * <ul>
     *   <li>Onglet de gestion des patients</li>
     *   <li>Onglet de gestion des spécialistes</li>
     * </ul>
     *
     * <p>Chaque onglet possède sa propre vue et son propre contrôleur,
     * avec accès aux DAO pour interagir avec la base de données.</p>
     */
    private void initialiserOnglets() {
        DatabaseConnection db = DatabaseConnection.getDefaultInstance();
        UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl(db);
        HoraireDAOImpl horaireDAO = new HoraireDAOImpl(db);
        EdtDAOImpl edtDAO = new EdtDAOImpl(db);
        RendezVousDAOImpl rendezVousDAO = new RendezVousDAOImpl(db);

        // === Onglet Patient ===
        AdminPatientVue panelPatients = new AdminPatientVue();
        new AdminPatientControleur(panelPatients, utilisateurDAO, rendezVousDAO, horaireDAO);
        adminVue.getGestionPatientsPanel().add(panelPatients);

        // === Onglet Spécialistes ===
        AdminSpecialisteVue panelSpecialistes = new AdminSpecialisteVue();
        new AdminSpecialisteControleur(panelSpecialistes, utilisateurDAO, horaireDAO, edtDAO, rendezVousDAO);
        adminVue.getGestionSpecialistesPanel().add(panelSpecialistes);
    }
}
