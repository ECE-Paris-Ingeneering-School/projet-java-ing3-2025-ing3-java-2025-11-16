package org.chem.Controleur;

import org.chem.Dao.RendezVousDAO;
import org.chem.Modele.RendezVous;
import org.chem.Vue.RendezVousVue;

import java.util.List;

public class RendezVousController {
    private final RendezVousVue vue;
    private final RendezVousDAO dao;

    public RendezVousController(RendezVousVue vue, RendezVousDAO dao) {
        this.vue = vue;
        this.dao = dao;
    }

    public void afficherRendezVousPatient(int idPatient) {
        vue.afficherRendezVous(idPatient);
    }
}
