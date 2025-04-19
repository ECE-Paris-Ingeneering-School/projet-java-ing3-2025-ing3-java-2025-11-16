package Modele;

import java.sql.Time;

public class Horaire {
    private int id;
    // Jour de la semaine représenté par un entier
    private int jourSemaine; // 1 = Dimanche, 2 = Lundi, ..., 7 = Samedi
    private Time heureDebut;
    private Time heureFin;

    public Horaire() {
    }

    // Constructeur avec tous les paramètres (sauf id)
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

    public static String convertirJourIntEnString(int jour) {
        return switch (jour) {
            case 1 -> "Lundi";
            case 2 -> "Mardi";
            case 3 -> "Mercredi";
            case 4 -> "Jeudi";
            case 5 -> "Vendredi";
            case 6 -> "Samedi";
            case 7 -> "Dimanche";
            default -> "Inconnu"; //Gestion du cas invalide
        };
    }

    public static int convertirJourEnInt(String jour) { //Ici on convertit un nom de jour en entier
        return switch (jour.toLowerCase()) {
            case "lundi" -> 1;
            case "mardi" -> 2;
            case "mercredi" -> 3;
            case "jeudi" -> 4;
            case "vendredi" -> 5;
            case "samedi" -> 6;
            case "dimanche" -> 7;
            default -> throw new IllegalArgumentException("Jour invalide : " + jour);
        };
    }


}
