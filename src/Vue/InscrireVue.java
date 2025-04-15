package Vue;


import javax.swing.*;
import java.awt.*;


public class InscrireVue extends JDialog {
    private JTextField prenomField;
    private JTextField nomField;
    private JTextField idField;
    private JPasswordField mdpField;
    private JButton validerButton;

    public InscrireVue() {
        initialisation();
        setSize(400, 300);
        setVisible(true);
    }

    private void initialisation() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Prénom:"));
        prenomField = new JTextField(20);
        panel.add(prenomField);


        panel.add(new JLabel("Nom:"));
        nomField = new JTextField(20);
        panel.add(nomField);


        panel.add(new JLabel("Identifiant:"));
        idField = new JTextField(20);
        panel.add(idField);


        panel.add(new JLabel("Mot de passe:"));
        mdpField = new JPasswordField(20);
        panel.add(mdpField);


        validerButton = new JButton("Valider l'inscription");
        panel.add(new JLabel());
        panel.add(validerButton);

        validerButton.addActionListener(e -> {
            String prenom = prenomField.getText();
            String nom = nomField.getText();
            String id = idField.getText();
            String mdp = new String(mdpField.getPassword());

            JOptionPane.showMessageDialog(this,
                    "Inscription réussie!\n" +
                            "Prénom: " + prenom + "\n" +
                            "Nom: " + nom + "\n" +
                            "ID: " + id);
            dispose();
        });

        getContentPane().add(panel);
    }
}