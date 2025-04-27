package org.chem.Vue;

import org.chem.Modele.Patient;
import org.chem.Modele.RendezVous;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panneau représentant un patient et la liste de ses rendez-vous.
 * Affiche le nom du patient et un ensemble de rendez-vous associés.
 */
public class PanelPatient extends JPanel {

    /** Instance du patient associée à ce panneau. */
    private Patient patient;

    /** Panel affichant la liste des rendez-vous du patient. */
    private JPanel RdvPanel;

    /**
     * Constructeur créant un panneau pour afficher un patient et ses rendez-vous.
     * Le panneau est structuré avec le nom du patient en haut et la liste des rendez-vous en dessous.
     *
     * @param p Le patient à afficher.
     * @param listRdv La liste des rendez-vous à afficher pour ce patient.
     */
    public PanelPatient(Patient p, ArrayList<RendezVous> listRdv) {
        this.patient = p;

        // Configuration du panneau principal
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Bordure grise
        setBackground(new Color(240, 248, 255)); // Couleur de fond
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 250)); // Taille maximale du panneau

        // Création du label pour afficher le nom du patient
        JLabel nomLabel = new JLabel(p.getPrenom() + " " + p.getNom());
        nomLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Panneau contenant les informations du patient
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false); // Transparence du panneau
        infoPanel.add(nomLabel); // Ajout du label du nom

        // Panneau contenant les rendez-vous du patient
        RdvPanel = new JPanel(new GridLayout(0, 1)); // Liste verticale
        RdvPanel.setOpaque(false);

        // Ajout des panneaux au panneau principal
        add(infoPanel, BorderLayout.NORTH);
        add(RdvPanel, BorderLayout.CENTER);
    }

    /**
     * Récupère l'objet patient associé à ce panneau.
     *
     * @return Le patient associé à ce panneau.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Affiche la liste des rendez-vous formatés pour ce patient.
     * Cette méthode rafraîchit le panneau des rendez-vous et affiche les nouveaux rendez-vous.
     *
     * @param rdvFormates Liste des rendez-vous formatés sous forme de chaîne de caractères.
     */
    public void afficherRendezVous(ArrayList<String> rdvFormates) {
        RdvPanel.removeAll(); // Rafraîchissement des rendez-vous précédents
        for (String texte : rdvFormates) {
            RdvPanel.add(new JLabel("• " + texte)); // Ajout d'un label pour chaque rendez-vous
        }
        RdvPanel.revalidate(); // Revalidation du panneau
        RdvPanel.repaint(); // Rafraîchissement du panneau
    }
}
