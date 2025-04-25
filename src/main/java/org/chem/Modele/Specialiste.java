package org.chem.Modele;
import java.util.*;

public class Specialiste extends Utilisateur {
    private String specialisation;
    private String lieu;
    private ArrayList<Horaire> emploiDuTemps = new ArrayList<>();

    public Specialiste(int id,String nom, String prenom, String email, String mdp, String specialisation, String lieu) {
        super(id ,nom, prenom, email, mdp, TypeUtilisateur.SPECIALISTE);
        this.specialisation = specialisation;
        this.lieu = lieu;
    }

    public Specialiste(String nom, String prenom, String email, String mdp, String specialisation, String lieu) {
        super(nom,prenom,email,mdp);
        this.specialisation = specialisation;
        this.lieu = lieu;
    }

    public String getSpecialisation() { return specialisation; }
    public void setSpecialisation(String specialisation) { this.specialisation = specialisation; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    @Override
    public String toString() {
        return super.toString() + ", Spécialisation : " + specialisation + ", Lieu : " + lieu;
    }

    public ArrayList<Horaire> getEmploiDuTemps() {
        return emploiDuTemps;
    }

    public void setEmploiDuTemps(ArrayList<Horaire> emploiDuTemps) {
        this.emploiDuTemps = emploiDuTemps;
    }
}
