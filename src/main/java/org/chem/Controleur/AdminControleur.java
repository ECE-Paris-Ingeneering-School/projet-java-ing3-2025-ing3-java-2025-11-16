package org.chem.Controleur;

import org.chem.Dao.*;
import org.chem.Vue.*;

import javax.swing.*;

public class AdminControleur {

    private AdminVue adminVue;

    public AdminControleur(AdminVue adminVue) {
        this.adminVue = adminVue;

        initialiserOnglets(); // on organise tout ici
    }

    private void initialiserOnglets() {

        DatabaseConnection db = DatabaseConnection.getDefaultInstance();
        UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl(db);
        HoraireDAOImpl horaireDAO = new HoraireDAOImpl(db);
        EdtDAOImpl edtDAO = new EdtDAOImpl(db);
        RendezVousDAOImpl rendezVousDAO = new RendezVousDAOImpl(db);

        // === Onglet Patient ===

        AdminPatientVue panelPatients = new AdminPatientVue();
        new AdminPatientControleur(panelPatients,utilisateurDAO,rendezVousDAO,horaireDAO);
        adminVue.getGestionPatientsPanel().add(panelPatients);

        // === Onglet Spécialistes ===

        AdminSpecialisteVue panelSpecialistes = new AdminSpecialisteVue();
        new AdminSpecialisteControleur(panelSpecialistes, utilisateurDAO, horaireDAO, edtDAO,rendezVousDAO);
        adminVue.getGestionSpecialistesPanel().add(panelSpecialistes);




    }
}
