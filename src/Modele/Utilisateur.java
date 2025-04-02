package Modele;

public class Utilisateur {
    protected int ID_utilisateur;
    protected String nom;
    protected String mdp;
    protected String prenom;
    protected String email;


    public Utilisateur(int ID,String nom ,String mdp , String prenom, String email ) {
        this.mdp=mdp;
        this.nom=nom;
        this.prenom=prenom;
        this.ID_utilisateur=ID;
        this.email=email;
    }
    public int getId() { return ID_utilisateur; }
    public void setId(int id) { this.ID_utilisateur = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }

    @Override
    public String toString() {
        return "Utilisateur " +
                "ID :" + ID_utilisateur +
                ", Nom : " + nom +
                ", Prénom : " + prenom +
                ", Email : " + email  +
                ", Type : " + this.getClass().getSimpleName();
    }

}


