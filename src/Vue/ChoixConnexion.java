package Vue;

import javax.swing.*;
import java.awt.*;

public class ChoixConnexion extends BaseFrame {

    public ChoixConnexion(String typeUtilisateur) {

        super();
        JPanel mainPanel = getMainPanel();

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JButton btnConnexion = new JButton("Se connecter");
        JButton btnCreerCompte = new JButton("Créer un compte");
        JButton btnRetour = new JButton("Retour");

        panel.add(btnConnexion);
        panel.add(btnCreerCompte);
        panel.add(btnRetour);

        mainPanel.add(panel);

        // Actions des boutons
        btnConnexion.addActionListener(e -> {
            new Connexion(typeUtilisateur);
            dispose();
        });

        btnCreerCompte.addActionListener(e -> {
            new CreationCompte(typeUtilisateur);
            dispose();
        });
        btnRetour.addActionListener(e -> {
            new PageAccueil();
            dispose();
        });

        setVisible(true);
    }
}
