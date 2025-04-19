package org.chem;

import org.chem.Controleur.ChoixConnexionControleur;
import org.chem.Controleur.ConnexionControleur;
import org.chem.Dao.DatabaseConnection;
import org.chem.Vue.ChoixConnexion;
import org.chem.Vue.Connexion;

import java.io.InputStream;
import java.util.Properties;

public class App {
    public static void main(String[] args) {

        Properties props = new Properties();
        InputStream input = DatabaseConnection.class.getResourceAsStream("/connection.properties");
        Connexion vue_connexion = new Connexion();
        try  {
        if (input != null) {
            props.load(input);
            String login = props.getProperty("autoconnect.email");
            String pwd = props.getProperty("autoconnect.pwd");
            vue_connexion = new Connexion(login, pwd);
        }
        } catch (Exception e) {
        }



        //new ChoixConnexion();
        //ChoixConnexion vue_choix = new ChoixConnexion();

        new ConnexionControleur(vue_connexion);
    }
}

