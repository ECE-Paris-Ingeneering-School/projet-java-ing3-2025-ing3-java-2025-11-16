package Dao;

import Modele.Horaire;
import java.util.List;

public interface HoraireDAO {

    Horaire getHoraireById(int id);

    List<Horaire> getAllHoraires();

    boolean ajouterHoraire(Horaire horaire);

    boolean supprimerHoraire(int id);

    boolean mettreAJourHoraire(Horaire horaire);
}
