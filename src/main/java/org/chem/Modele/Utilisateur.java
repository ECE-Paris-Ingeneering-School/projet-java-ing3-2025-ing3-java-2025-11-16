package org.chem.Modele;

public class Utilisateur {
    protected int id;
    protected String nom;
    protected String mdp;
    protected String prenom;
    protected String email;

    public Utilisateur() {
    }

    // Constructeur avec ID (pour récupération depuis la base)
    public Utilisateur(int id, String nom, String prenom, String email, String mdp) {
        this.id = id;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Utilisateur(String nom, String prenom, String email, String mdp) {
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
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

    // Méthode pour afficher les informations de l'utilisateur
    public void afficherUtilisateur() {
        System.out.println("ID : " + id);
        System.out.println("Nom : " + nom);
        System.out.println("Prénom : " + prenom);
        System.out.println("Email : " + email);
        // Si tu veux afficher le mot de passe, tu peux le décommenter (mais attention à la sécurité)
        // System.out.println("Mot de passe : " + mdp);
    }

    @Override
    public String toString() {
        return "Utilisateur " +
                ", Nom : " + nom +
                ", Prénom : " + prenom +
                ", Email : " + email +
                ", Type d'utilisateur : " + this.getClass().getSimpleName();
    }
}
