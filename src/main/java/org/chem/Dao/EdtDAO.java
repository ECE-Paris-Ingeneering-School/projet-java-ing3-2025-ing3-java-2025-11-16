package org.chem.Dao;

import org.chem.Modele.Edt;
import java.util.List;

public interface EdtDAO {
    Edt getEdtById(int id);
    List<Edt> getEdtBySpecialiste(int idSpecialiste);
    boolean ajouterEdt(Edt edt);
    boolean supprimerEdt(int id);
}
