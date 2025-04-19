package org.chem.Modele;
import java.util.Date;

public class RendezVous {
    private int id;
    private int idSpecialiste;
    private int idPatient;
    private int idHoraire;
    private Date date;
    private String notes;
    private String lieu;


    // Constructeur
    public RendezVous(int idSpecialiste, int idPatient, int idHoraire, Date date, String notes, String lieu) {
        this.idSpecialiste = idSpecialiste;
        this.idPatient = idPatient;
        this.idHoraire = idHoraire;
        this.date = date;
        this.notes = notes;
        this.lieu = lieu;
    }

    public RendezVous() {

    }

    public int getId() { return id; }
    public int getIdSpecialiste() { return idSpecialiste; }
    public int getIdPatient() { return idPatient; }
    public int getIdHoraire() { return idHoraire; }
    public Date getDate() { return date; }
    public String getNotes() { return notes; }
    public String getLieu() { return lieu; }

    public void setId(int id) { this.id = id; }
    public void setIdSpecialiste(int idSpecialiste) { this.idSpecialiste = idSpecialiste; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }
    public void setIdHoraire(int idHoraire) { this.idHoraire = idHoraire; }
    public void setDate(Date date) { this.date = date; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setLieu(String lieu) { this.lieu = lieu; }


}
