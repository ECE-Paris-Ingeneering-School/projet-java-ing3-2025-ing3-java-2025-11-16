package Vue;

import javax.swing.*;
import java.awt.*;

public class ChoixConnexion extends BaseFrame {

    public ChoixConnexion(String typeUtilisateur) {
        super();
        JPanel contenu = getCenterPanel();
        contenu.setLayout(new BorderLayout());

        // ------ Centre avec les deux boutons ------
        JPanel centerPanel = new JPanel(new GridBagLayout()); // Centrage vertical & horizontal
        JPanel boutonsPanel = new JPanel(new GridLayout(2, 1, 20, 20)); // Espacement vertical

        JButton btnConnexion = new JButton("Se connecter");
        JButton btnCreerCompte = new JButton("Créer un compte");

        // Appliquer une police et une taille
        Font bigFont = new Font("Arial", Font.BOLD, 20);
        Dimension bigSize = new Dimension(250, 60);
        btnConnexion.setFont(bigFont);
        btnCreerCompte.setFont(bigFont);
        btnConnexion.setPreferredSize(bigSize);
        btnCreerCompte.setPreferredSize(bigSize);

        boutonsPanel.add(btnConnexion);
        boutonsPanel.add(btnCreerCompte);

        centerPanel.add(boutonsPanel);
        contenu.add(centerPanel, BorderLayout.CENTER);

        // ------ Bas avec bouton retour aligné à gauche ------
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnRetour = new JButton("Retour");
        btnRetour.setFont(new Font("Arial", Font.PLAIN, 16));
        bottomPanel.add(btnRetour, BorderLayout.WEST);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contenu.add(bottomPanel, BorderLayout.SOUTH);

        // ------ Actions des boutons ------
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
