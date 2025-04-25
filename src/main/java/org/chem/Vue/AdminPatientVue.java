package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

public class AdminPatientVue extends JPanel {

    private JPanel listePatientsPanel;

    public AdminPatientVue() {
        setLayout(new BorderLayout());

        listePatientsPanel = new JPanel();
        listePatientsPanel.setLayout(new BoxLayout(listePatientsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listePatientsPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getListePatientsPanel() {return listePatientsPanel;}
}
