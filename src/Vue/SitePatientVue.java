package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SitePatientVue extends JFrame {
    // Barre de saisie pour la recherche
    private JTextField BarDeRecherche;
    //Zone d'affichage (à implémenter plus tard si nécessaire)
    private JTextArea affichage;

    public SitePatientVue() {
        setTitle("Portail Médical");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // Barre de Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        // Éléments du menu : historique et rendez-vous du patient
        JMenuItem itemHistorique = new JMenuItem("Historique");
        JMenuItem itemRdv = new JMenuItem("Mes RDV");

        menu.add(itemHistorique);
        menu.add(itemRdv);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        //  Section de Recherche
        JPanel centrePanel = new JPanel(new BorderLayout());
        BarDeRecherche = new JTextField(20);
        JButton buttonRecherche = new JButton("Rechercher");
       // Action déclenchée quand clic sur le bouton
        buttonRecherche.addActionListener(e -> rechercher());

        JLabel labelRecherche = new JLabel("Spécialistes ou lieux : ");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.add(labelRecherche);
        searchPanel.add(BarDeRecherche);
        searchPanel.add(buttonRecherche);

        centrePanel.add(searchPanel, BorderLayout.CENTER);
        add(centrePanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void rechercher() {
        String terme = BarDeRecherche.getText();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SitePatientVue::new);
    }
}
