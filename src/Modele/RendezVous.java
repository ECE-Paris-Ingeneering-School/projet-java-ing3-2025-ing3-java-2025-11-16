package Modele;

import java.time.LocalDateTime;

public class RendezVous {
    private int IDrdv;
    Specialiste specialiste;
    Patient patient;
    private String statut;//confirmé , ou annulé ou reporté
    private LocalDateTime dateHeure;

    public RendezVous(int IDrdv , String statut, LocalDateTime dateHeure , Specialiste specialiste, Patient patient){
        this.IDrdv=IDrdv;
        this.statut=statut;
        this.specialiste=specialiste;
        this.dateHeure=dateHeure;
        this.patient=patient;
    }

    public int getIDrdv(){
        return IDrdv;
    }

    public String statut(){
        return statut;
    }

    public Specialiste getSpecialiste() {
        return specialiste;
    }

    public Patient getPatient(){
        return patient;

    }

    public LocalDateTime dateHeure(){
        return dateHeure;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "idRDV=" + IDrdv +
                ", patient=" + patient.getNom() + " " + patient.getPrenom() +
                ", specialiste=" + specialiste.getNom() + " " + specialiste.getPrenom() +
                ", dateHeure=" + dateHeure +
                ", statut='" + statut + '\'' +
                '}';
    }
}
