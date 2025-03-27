package Modele;

public class Utilisateur {
    protected int ID;
    protected String nom;
    protected String mdp;
    protected String prenom;


    public Utilisateur(int ID,String nom ,String mdp , String prenom ){
        this.mdp=mdp;
        this.nom=nom;
        this.prenom=prenom;
        this.ID=ID;
    }

    public int getID() {
        return ID;
    }

    public String getNom() {
        return nom;
    }

    public String getMdp() {
        return mdp;
    }

    public String getPrenom() {
        return prenom;
    }

    public void afficherUtilisateur(){
        System.out.println("L'ID de l'utilisateur est : " +ID+ ",son nom est :"+nom+ ", son prénom est"+prenom+" et son mdp est :"+mdp);
    }
}
