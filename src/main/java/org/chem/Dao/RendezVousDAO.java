package org.chem.Dao;
import java.util.*;

import org.chem.Modele.RendezVous;

public interface RendezVousDAO{

    List<RendezVous> getAllRendezVous();

    RendezVous getRendezVousById(int id);

    List<RendezVous> getRendezVousBySpecialiste(int idSpecialiste);

    List<RendezVous> getRendezVousByPatient(int idPatient);

    boolean ajouterRendezVous(RendezVous rdv);

    boolean estDejaReserve(int idHoraire, java.util.Date date);

    boolean supprimerRendezVous(int id);

    boolean modifierRendezVous(RendezVous rdv);
}
