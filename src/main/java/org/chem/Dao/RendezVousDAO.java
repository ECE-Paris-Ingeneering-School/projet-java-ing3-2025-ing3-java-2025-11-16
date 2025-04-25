package org.chem.Dao;
import java.util.*;

import org.chem.Modele.RendezVous;

public interface RendezVousDAO{

    ArrayList<RendezVous> getAllRendezVous();

    RendezVous getRendezVousById(int id);

    ArrayList<RendezVous> getRendezVousBySpecialiste(int idSpecialiste);

    ArrayList<RendezVous> getRendezVousByPatient(int idPatient);

    boolean ajouterRendezVous(RendezVous rdv);

    boolean estDejaReserve(int idHoraire, java.util.Date date);

    boolean supprimerRendezVous(int id);

    boolean modifierRendezVous(RendezVous rdv);
}
