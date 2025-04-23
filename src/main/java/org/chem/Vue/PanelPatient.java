package org.chem.Vue;

import org.chem.Modele.Patient;
import org.chem.Modele.RendezVous;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelPatient extends JPanel {
    private Patient patient;
    private JPanel RdvPanel;

    public PanelPatient(Patient p, ArrayList<RendezVous> listRdv) {
        this.patient = p;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(new Color(240, 248, 255));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        JLabel nomLabel = new JLabel(p.getPrenom() + " " + p.getNom());
        nomLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        infoPanel.add(nomLabel);

        RdvPanel = new JPanel(new GridLayout(0, 1));
        RdvPanel.setOpaque(false);

        add(infoPanel, BorderLayout.NORTH);
        add(RdvPanel, BorderLayout.CENTER);
    }


    public Patient getPatient() {return patient;}

    public void afficherRendezVous(ArrayList<String> rdvFormates) {
        RdvPanel.removeAll(); // Optionnel : pour rafraîchir s’il y avait déjà des rdv
        for (String texte : rdvFormates) {
            RdvPanel.add(new JLabel("• " + texte));
        }
        RdvPanel.revalidate();
        RdvPanel.repaint();
    }

}
