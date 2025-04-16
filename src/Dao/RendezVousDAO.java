package Dao;
import java.util.*;

import Modele.RendezVous;

public interface RendezVousDAO{

    List<RendezVous> getAllRendezVous();

    RendezVous getRendezVousById(int id);

    List<RendezVous> getRendezVousBySpecialiste(int idSpecialiste);

    List<RendezVous> getRendezVousByPatient(int idPatient);

    boolean ajouterRendezVous(RendezVous rdv);

    boolean supprimerRendezVous(int id);

    boolean modifierRendezVous(RendezVous rdv);
}
