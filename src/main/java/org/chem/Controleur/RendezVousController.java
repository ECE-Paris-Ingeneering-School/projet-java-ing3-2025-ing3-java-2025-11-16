package org.chem.Controleur;

import org.chem.Dao.RendezVousDAO;
import org.chem.Modele.RendezVous;
import org.chem.Vue.RendezVousVue;

import java.util.List;

public class RendezVousController {
    private final RendezVousVue vue;

    public RendezVousController(RendezVousVue vue) {
        this.vue = vue;
    }

    public void afficherRendezVousPatient(int idPatient) {
        vue.afficherRendezVous(idPatient);
    }
}
