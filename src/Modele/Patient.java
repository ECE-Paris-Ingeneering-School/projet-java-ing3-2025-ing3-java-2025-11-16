package Modele;
import java.util.ArrayList;


public class Patient{
    private int IDpatient;
    private String mdp;
    private String nom;
    private String prenom;
    private String TypePatient;
    private ArrayList<String> historiqueRDV;
    private int inscriptionDate;

    public Patient(int IDpatient, String mdp, String nom, String prenom, String typePatient, ArrayList historiqueRDV, int inscriptionDate) {
        this.IDpatient = IDpatient;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        TypePatient = typePatient;
        this.historiqueRDV = historiqueRDV;
        this.inscriptionDate = inscriptionDate;
    }

    public int getIDpatient(){
        return IDpatient;
    }

    public void setIDpatient(String rdv){
        this.historiqueRDV.add(rdv);
    }

    public int getInscriptionDate(){
        return inscriptionDate;
    }

    public String toString(){

        return "Patient{" +
                "IDpatient=" + IDpatient +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", typePatient='" + TypePatient + '\'' +
                ", inscriptionDate=" + inscriptionDate +
                ", historiqueRDV=" + historiqueRDV +
                '}';
    }
}