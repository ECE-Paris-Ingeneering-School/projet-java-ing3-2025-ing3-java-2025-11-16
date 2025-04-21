package org.chem.Modele;

public class Admin extends Utilisateur{


    public Admin(int id,String nom, String prenom, String email, String password){
        super(id, nom, prenom, email, password, TypeUtilisateur.ADMIN);
    }

    public Admin(String nom, String prenom, String email, String password){
        super(nom, prenom, email, password);
    }

}
