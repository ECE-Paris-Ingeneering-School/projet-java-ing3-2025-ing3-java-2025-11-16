package org.chem.Dao;

import org.chem.Modele.Horaire;

import java.sql.Time;
import java.util.List;

public interface HoraireDAO {

    Horaire getHoraireById(int id);

    List<Horaire> getAllHoraires();

    boolean ajouterHoraire(Horaire horaire);

    boolean supprimerHoraire(int id);

    boolean mettreAJourHoraire(Horaire horaire);

    int getIdHoraireExistant(int jour, Time heureDebut);
}
