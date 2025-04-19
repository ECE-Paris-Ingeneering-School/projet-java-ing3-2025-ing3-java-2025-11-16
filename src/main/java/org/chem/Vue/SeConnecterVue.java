package org.chem.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SeConnecterVue extends JDialog {
    private JTextField idField;
    private JPasswordField MdPField;
    private JButton connectButton;
    private JButton inscrireButton;

    public SeConnecterVue() {
        setTitle("Connexion");
        setSize(800, 500);
        setVisible(true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        connectButton = new JButton("Se connecter");
        inscrireButton = new JButton("S'inscrire");

        buttonPanel.add(connectButton);
        buttonPanel.add(inscrireButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);


        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String MdP = new String(MdPField.getPassword());
                dispose();
            }
        });


        inscrireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new InscrireVue();
            }
        });
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