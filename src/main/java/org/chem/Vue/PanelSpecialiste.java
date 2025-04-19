package org.chem.Vue;

import org.chem.Modele.*;

import javax.swing.*;
import java.awt.*;

public class PanelSpecialiste extends JPanel {

    private final Specialiste specialiste;
    private final JButton btnSupprimer;
    private final JButton btnAjouterHoraire;

    public PanelSpecialiste(Specialiste s) {
        this.specialiste = s;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(new Color(240, 248, 255));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel nomLabel = new JLabel(s.getPrenom() + " " + s.getNom() + " - " + s.getSpecialisation());
        nomLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        infoPanel.add(nomLabel);

        btnSupprimer = new JButton("Supprimer");
        btnAjouterHoraire = new JButton("Ajouter horaire");

        JPanel actionsPanel = new JPanel();
        actionsPanel.setOpaque(false);
        actionsPanel.add(btnAjouterHoraire);
        actionsPanel.add(btnSupprimer);

        add(infoPanel, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.EAST);
    }

    public JButton getBtnSupprimer() { return btnSupprimer; }
    public JButton getBtnAjouterHoraire() { return btnAjouterHoraire; }
    public Specialiste getSpecialiste() { return specialiste; }
}
