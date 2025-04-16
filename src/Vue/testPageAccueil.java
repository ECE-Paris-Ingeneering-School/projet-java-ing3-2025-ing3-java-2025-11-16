package Vue;
import javax.swing.*;

public class testPageAccueil extends JFrame {

    private JPanel mainAccueil;
    private JPanel BandeauTop;
    private JLabel TitreSite;

    public testPageAccueil() {

        setContentPane(mainAccueil);

        setTitle("Application Rendez-vous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        setVisible(true);

    }


}
