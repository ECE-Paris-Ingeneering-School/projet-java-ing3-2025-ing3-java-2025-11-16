package org.chem.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SeConnecterVue extends JDialog {
    // Champs de saisie pour l'identifiant et le mot de passe
    private JTextField idField;
    private JPasswordField MdPField;

    // Boutons pour les actions de connexion et d'inscription
    private JButton connectButton;
    private JButton inscrireButton;

    public SeConnecterVue() {
        setTitle("Connexion");
        setSize(800, 500);
        setVisible(true);
        // Le Panel Principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        // Ici , la zone de saisie
        JPanel inputPanel = new JPanel(new GridLayout(2, 2,1,1));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(20);

        JLabel MdPLabel = new JLabel("Mot de Passe:");
        MdPField = new JPasswordField(20);

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(MdPLabel);
        inputPanel.add(MdPField);

        panel.add(inputPanel, BorderLayout.CENTER);
        // C'est la zone des bouttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        connectButton = new JButton("Se connecter");
        inscrireButton = new JButton("S'inscrire");

        buttonPanel.add(connectButton);
        buttonPanel.add(inscrireButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Ici , l'action des bouttons
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String MdP = new String(MdPField.getPassword());
                dispose();
            }
        });

//Lorsqu'on clique sur s'inscrire, on ouvre la vue d'inscription
        inscrireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new InscrireVue();
            }
        });
        // On ajoute du panel global à la fenêtre
        getContentPane().add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SeConnecterVue dialog = new SeConnecterVue();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
    }
}