package Vue;
import Modele.Utilisateur;

import javax.swing.*;


public class testBaseFrame extends JFrame {

    protected JPanel mainFond;
    protected JPanel BandeauTop;
    protected JLabel TitreSite;
    protected Utilisateur utilisateurConnecte;

    public testBaseFrame(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
        page();
    }

    public testBaseFrame() {
        this(null);
    }

    public void page(){
        setContentPane(mainFond);

        setTitle("Application Rendez-vous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public JPanel getFond(){return mainFond;}

}

