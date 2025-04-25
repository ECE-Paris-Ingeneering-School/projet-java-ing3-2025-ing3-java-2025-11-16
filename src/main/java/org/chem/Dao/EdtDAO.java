package org.chem.Dao;

import org.chem.Modele.Edt;
import java.util.List;

public interface EdtDAO {

    Edt getEdtById(int id);

    List<Edt> getEdtBySpecialiste(int idSpecialiste);

    boolean ajouterEdt(Edt edt);

    boolean ajouterLienSpecialisteHoraire(int idSpecialiste, int idHoraire);

    boolean supprimerLienSpecialisteHoraire(int idSpecialiste, int idHoraire);

    boolean modifierLienHoraireSpecialiste(int idSpecialiste, int ancienIdHoraire, int nouveauIdHoraire);
}
