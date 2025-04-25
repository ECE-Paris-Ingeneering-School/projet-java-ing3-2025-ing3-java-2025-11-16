package org.chem.Vue;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.chem.Modele.*;

public class PanelSpecialiste extends JPanel {

    private final Specialiste specialiste;
    private final JButton btnSupprimerSpecialiste;
    private final JButton btnModifierSpecialiste;
    private final JButton btnAjouterHoraire;
    private final JPanel edtPanel;
    private final JPanel RdvPanel;

    public PanelSpecialiste(Specialiste s, ArrayList<RendezVous> listRdv) {
        this.specialiste = s;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(new Color(240, 248, 255));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        JLabel nomLabel = new JLabel(s.getPrenom() + " " + s.getNom() + " - " + s.getSpecialisation());
        nomLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        infoPanel.add(nomLabel);

        btnModifierSpecialiste = new JButton("Modifier le spécialiste");
        btnSupprimerSpecialiste = new JButton("Supprimer le spécialiste");
        btnAjouterHoraire = new JButton("Ajouter un horaire");

        JPanel actionsPanel = new JPanel();
        actionsPanel.setOpaque(false);
        actionsPanel.add(btnAjouterHoraire);
        actionsPanel.add(btnModifierSpecialiste);
        actionsPanel.add(btnSupprimerSpecialiste);

        // Emploi du temps
        edtPanel = new JPanel();
        edtPanel.setLayout(new BoxLayout(edtPanel, BoxLayout.Y_AXIS));
        edtPanel.setOpaque(false);

        ArrayList<Horaire> edt = s.getEmploiDuTemps();

        if (edt.isEmpty()) {
            edtPanel.add(new JLabel("Aucun horaire défini."));
        } else {
            for (Horaire h : edt) {
                JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
                ligne.setOpaque(false);

                String texte = Horaire.convertirJourIntEnString(h.getJourSemaine()) + " : " + h.getHeureDebut() + " - " + h.getHeureFin();
                JLabel label = new JLabel(texte);

                JButton btnModiferHoraire = new JButton("Modifier");
                btnModiferHoraire.setActionCommand("modifier_" + h.getId());


                JButton btnSupprimerHoraire = new JButton("Supprimer");
                btnSupprimerHoraire.setActionCommand("supprimer_" + h.getId());

                ligne.add(label);
                ligne.add(btnModiferHoraire);
                ligne.add(btnSupprimerHoraire);

                edtPanel.add(ligne);
            }
        }
        RdvPanel = new JPanel(new GridLayout(0, 1));
        RdvPanel.setOpaque(false);

        add(infoPanel, BorderLayout.NORTH);
        add(edtPanel, BorderLayout.WEST);
        add(actionsPanel, BorderLayout.SOUTH);
        add(RdvPanel, BorderLayout.EAST);

    }

    public void afficherRendezVous(ArrayList<String> rdvFormates) {
        RdvPanel.removeAll(); // Optionnel : pour rafraîchir s’il y avait déjà des rdv
        for (String texte : rdvFormates) {
            RdvPanel.add(new JLabel("• " + texte));
        }
        RdvPanel.revalidate();
        RdvPanel.repaint();
    }

    public JButton getBtnModifierSpecialiste() { return btnModifierSpecialiste; }
    public JButton getBtnSupprimerSpecialiste() { return btnSupprimerSpecialiste; }
    public JButton getBtnAjouterHoraire() { return btnAjouterHoraire; }
    public Specialiste getSpecialiste() { return specialiste; }
    public JPanel getEmploiDuTempsPanel() { return edtPanel; } // pour le contrôleur si besoin

}
