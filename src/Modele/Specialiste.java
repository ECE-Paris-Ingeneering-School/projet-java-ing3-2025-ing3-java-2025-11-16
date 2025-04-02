package Modele;

public class Specialiste extends Utilisateur {
    private String specialisation;
    private String lieu;

    public Specialiste(int id, String nom, String prenom, String email, String mdp, String specialisation, String lieu) {
        super(id, nom, prenom, email, mdp);
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

}
