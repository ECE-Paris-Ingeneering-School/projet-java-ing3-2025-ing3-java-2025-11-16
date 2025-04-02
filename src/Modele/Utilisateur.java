package Modele;

public class Utilisateur {
    protected int id;
    protected String nom;
    protected String mdp;
    protected String prenom;
    protected String email;

    // Constructeur avec ID (pour récupération depuis la base)
    public Utilisateur(int id, String nom, String mdp, String prenom, String email) {
        this.id = id;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Utilisateur(String nom ,String mdp , String prenom, String email ) {
        this.mdp=mdp;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
    }

    public int getId() { return id; }
    public void setId(int ID) { this.id = ID; }

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
                ", Nom : " + nom +
                ", Prénom : " + prenom +
                ", Email : " + email  +
                ", Type : " + this.getClass().getSimpleName();
    }

}


