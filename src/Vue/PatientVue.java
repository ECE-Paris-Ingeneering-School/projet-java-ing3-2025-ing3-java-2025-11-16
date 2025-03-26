package Vue;

import java.util.ArrayList;

public class PatientVue {
    private int IDpatient;
    private String mdp;
    private String nom;
    private String prenom;
    private String TypePatient;
    private ArrayList<String> historiqueRDV;
    private int inscriptionDate;

    public PatientVue(int IDpatient, String mdp, String nom, String prenom, String typePatient, ArrayList historiqueRDV, int inscriptionDate) {
        this.IDpatient = IDpatient;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        TypePatient = typePatient;
        this.historiqueRDV = historiqueRDV;
        this.inscriptionDate = inscriptionDate;
    }
}
