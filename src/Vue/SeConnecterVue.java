package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SeConnecterVue extends JDialog {
    private JTextField idField;
    private JPasswordField MdPField;
    private JButton connectButton;

    public SeConnecterVue (Frame fenetre1 ){
        super(fenetre1, "Connexion", true);
        initialisation();
        pack();
        setLocationRelativeTo(fenetre1);
    }

    private void initialisation(){
        JPanel panel = new JPanel(new GridLayout(3,2,10,10));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(20);

        JLabel MdPLabel = new JLabel("Mot de Passe:");
        MdPField = new JPasswordField(20);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(MdPLabel);
        panel.add(MdPField);

        connectButton = new JButton("Se connecter");
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(connectButton);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String MdP = new String(MdPField.getPassword());
                JOptionPane.showMessageDialog(SeConnecterVue.this, "ID: " + id + "\n Mot de Passe:  " + MdP);
                dispose();
            }
        });

        getContentPane().add(panel,BorderLayout.CENTER);

    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SeConnecterVue dialog = new SeConnecterVue(null);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
    }
}
