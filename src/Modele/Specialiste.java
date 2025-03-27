package Modele;

import java.util.ArrayList;

public class Specialiste {

    private int IDspecialiste;
    private String nom;
    private String prenom;
    private String specialisation;
    private ArrayList<String> lieuxConsultation;
    private ArrayList<String> disponibilites;

    public Specialiste(String prenom, String nom, int IDspecialiste, String specialisation, ArrayList<String> lieuxConsultation, ArrayList<String> disponibilites) {
        this.prenom = prenom;
        this.nom = nom;
        this.IDspecialiste = IDspecialiste;
        this.specialisation = specialisation;
        this.lieuxConsultation = new ArrayList<>();
        this.disponibilites = new ArrayList<>();

    }

    public String getNom() {
        return nom;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public String getPrenom() {
        return prenom;
    }

    public ArrayList<String> getLieuxConsultation() {
        return lieuxConsultation;
    }

    public ArrayList<String> getDisponibilites() {
        return disponibilites;
    }

    public int getIDspecialiste(){
        return IDspecialiste;
    }

    /*public String toString(){
        return "Spécialiste{" +
                "IDspécialiste=" + IDspecialiste +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", typePatient='" + TypePatient + '\'' +
                ", inscriptionDate=" + inscriptionDate +
                ", historiqueRDV=" + historiqueRDV +
                '}';
    }
}*/




}


