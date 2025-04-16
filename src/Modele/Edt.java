package Modele;

public class Edt {
    private int id;
    private int idSpecialiste;
    private int idHoraire;

    // ✅ Constructeur vide
    public Edt() {
    }

    // ✅ Constructeur avec paramètres (sauf id)
    public Edt(int idSpecialiste, int idHoraire) {
        this.idSpecialiste = idSpecialiste;
        this.idHoraire = idHoraire;
    }

    // Getters
    public int getId() { return id; }
    public int getIdSpecialiste() { return idSpecialiste; }
    public int getIdHoraire() { return idHoraire; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setIdSpecialiste(int idSpecialiste) { this.idSpecialiste = idSpecialiste; }
    public void setIdHoraire(int idHoraire) { this.idHoraire = idHoraire; }
}
