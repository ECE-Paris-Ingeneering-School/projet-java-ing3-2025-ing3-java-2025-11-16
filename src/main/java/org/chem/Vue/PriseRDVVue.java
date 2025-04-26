package org.chem.Vue;

import java.awt.*;
import javax.swing.*;

public class PriseRDVVue extends JPanel {
    private JButton btnSuiv, btnPrec;
    private JPanel gridPanel;
    private JLabel moisLabel;

    public PriseRDVVue() {
        setLayout(new BorderLayout());
        btnSuiv = new JButton("→ suiv");
        btnPrec = new JButton("← pred");
        moisLabel = new JLabel("", SwingConstants.CENTER);

        JPanel header = new JPanel(new BorderLayout());
        header.add(btnPrec, BorderLayout.WEST);
        header.add(moisLabel, BorderLayout.CENTER);
        header.add(btnSuiv, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        gridPanel = new JPanel();
        add(gridPanel, BorderLayout.CENTER);
    }

    public void setMoisLabel(String texte) {
        moisLabel.setText(texte);
    }

    public void setGridPanel(JPanel panel) {
        gridPanel.removeAll();
        gridPanel.add(panel);
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // Expose les boutons pour que le contrôleur ajoute les actions
    public JButton getBtnSuiv() { return btnSuiv; }
    public JButton getBtnPrec() { return btnPrec; }
}
