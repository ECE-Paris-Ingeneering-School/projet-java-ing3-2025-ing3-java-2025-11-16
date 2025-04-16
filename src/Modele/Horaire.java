package Modele;

import java.sql.Time;

public class Horaire {
    private int id;
    private int jourSemaine; // 1 = Dimanche, 2 = Lundi, ..., 7 = Samedi
    private Time heureDebut;
    private Time heureFin;

    // ✅ Constructeur vide
    public Horaire() {
    }

    // ✅ Constructeur avec tous les paramètres (sauf id)
    public Horaire(int jourSemaine, Time heureDebut, Time heureFin) {
        this.jourSemaine = jourSemaine;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    // Getters
    public int getId() { return id; }
    public int getJourSemaine() { return jourSemaine; }
    public Time getHeureDebut() { return heureDebut; }
    public Time getHeureFin() { return heureFin; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setJourSemaine(int jourSemaine) { this.jourSemaine = jourSemaine; }
    public void setHeureDebut(Time heureDebut) { this.heureDebut = heureDebut; }
    public void setHeureFin(Time heureFin) { this.heureFin = heureFin; }
}
