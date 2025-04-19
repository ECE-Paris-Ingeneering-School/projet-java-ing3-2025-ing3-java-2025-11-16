package org.chem.Modele;

import org.mindrot.jbcrypt.BCrypt;

public class Securite {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String mdpSaisi, String mdpCrypte) {
        return BCrypt.checkpw(mdpSaisi, mdpCrypte);
    }

}
