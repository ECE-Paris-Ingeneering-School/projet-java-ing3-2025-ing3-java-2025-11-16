package Controleur;

import Modele.RendezVous;
import java.util.ArrayList;
import java.util.List;

public class  RendezVousController {

    // Liste temporaire pour stocker les rendez-vous en mémoire
    private List<RendezVous> listeRendezVous;

    // Constructeur
    public RendezVousController() {
        this.listeRendezVous = new ArrayList<>();
    }

    // ✅ Ajouter un rendez-vous
    public boolean ajouterRendezVous(RendezVous rdv) {
        if (rdv != null) {
            listeRendezVous.add(rdv);
            return true;
        }
        return false;
    }

    // ✅ Obtenir la liste de tous les rendez-vous
    public List<RendezVous> getRendezVous() {
        return listeRendezVous;
    }
}


