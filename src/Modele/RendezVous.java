package Modele;
import java.util.Date;

public class RendezVous {
    private int idSpecialiste;
    private int idPatient;
    private String notes;
    private Date horaire;
    private String lieu;

    // Constructeur par défaut
    public RendezVous() {}

    // Constructeur avec paramètres
    public RendezVous(int idSpecialiste, int idPatient, String notes, Date horaire, String lieu) {
        this.idSpecialiste = idSpecialiste;
        this.idPatient = idPatient;
        this.notes = notes;
        this.horaire = horaire;
        this.lieu = lieu;
    }

    // Getters et Setters
    public int getIdSpecialiste() {
        return idSpecialiste;
    }

    public void setIdSpecialiste(int idSpecialiste) {
        this.idSpecialiste = idSpecialiste;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getHoraire() {
        return horaire;
    }

    public void setHoraire(Date horaire) {
        this.horaire = horaire;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    // toString pour afficher l'objet sous forme de texte
    @Override
    public String toString() {
        return "RendezVous{" +
                "idSpecialiste=" + idSpecialiste +
                ", idPatient=" + idPatient +
                ", notes='" + notes + '\'' +
                ", horaire=" + horaire +
                ", lieu='" + lieu + '\'' +
                '}';
    }

    // equals et hashCode pour comparer les objets
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RendezVous that = (RendezVous) obj;
        return idSpecialiste == that.idSpecialiste && idPatient == that.idPatient && horaire.equals(that.horaire);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(idSpecialiste, idPatient, horaire);
    }
}
