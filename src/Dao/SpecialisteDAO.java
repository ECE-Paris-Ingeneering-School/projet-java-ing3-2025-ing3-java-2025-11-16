package Dao;

import Modele.Specialiste;
import java.util.List;

public interface SpecialisteDAO {
    List<Specialiste> getAllSpecialistes();

    Specialiste getSpecialisteById(int id);

    void ajouterSpecialiste(Specialiste specialiste);

    void supprimerSpecialiste(int id);
}
