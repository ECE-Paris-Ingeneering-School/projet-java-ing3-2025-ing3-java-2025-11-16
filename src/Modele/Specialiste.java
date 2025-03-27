package Modele;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Specialiste {

    private int IDspecialiste;
    private String nom;
    private String prenom;
    private String specialisation;
    private Set<String> lieuxConsultation;
    private Set<String> disponibilites;

    public Specialiste(String prenom, String nom, int IDspecialiste, String specialisation, ArrayList<String> lieuxConsultation, ArrayList<String> disponibilites) {
        this.prenom = prenom;
        this.nom = nom;
        this.IDspecialiste = IDspecialiste;
        this.specialisation = specialisation;
        this.lieuxConsultation = new HashSet<>(lieuxConsultation);
        this.disponibilites = new HashSet<>(disponibilites);
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


    public int getIDspecialiste(){
        return IDspecialiste;
    }

    public Set<String> getLieuxConsultation() {
        return new HashSet<>(lieuxConsultation);
    }

    public Set<String> getDisponibilites() {
        return new HashSet<>(disponibilites);
    }
    public void ajouterLieux (String lieu){
        lieuxConsultation.add(lieu);
    }

    public void ajouterDisponibilites(String date){
       disponibilites.add(date);
    }

    public void afficher(){
        System.out.println(this);
    }

    public String toString(){
        return "Spécialiste{" +
                "IDspécialiste=" + IDspecialiste +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", spécialisation='" +specialisation + '\'' +
                ", ses lieux de consultation sont : " + lieuxConsultation +
                ", ses disponibilités sont les suivantes : " + disponibilites +
                '}';
    }
}






