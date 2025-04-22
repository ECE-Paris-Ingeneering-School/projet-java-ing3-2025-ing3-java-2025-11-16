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

        AdminSpecialisteVue panelSpecialistes = new AdminSpecialisteVue();

        DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");
        UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl(db);
        HoraireDAOImpl horaireDAO = new HoraireDAOImpl(db);
        EdtDAOImpl edtDAO = new EdtDAOImpl(db);

        // === Onglet Spécialistes ===
        new AdminSpecialisteControleur(panelSpecialistes, utilisateurDAO, horaireDAO, edtDAO);

        adminVue.getGestionSpecialistesPanel().add(panelSpecialistes);


    }
}
