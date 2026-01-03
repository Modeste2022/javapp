package controller;

import model.Joueur;
import model.Statistique;
import util.Serializer;
import view.MatchStatsPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FinalController {

    private final List<Joueur> joueurs;
    private String EquipeNom;

    public FinalController(List<Joueur> joueurs,String equipeNom) {
        this.joueurs = joueurs;
        this.EquipeNom = equipeNom;
    }

    public void ajouterEcouteurs(JMenuItem importItem, JMenuItem exportItem, JFrame parent) {
        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importerJoueurs(parent);
            }
        });

        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exporterJoueurs(parent);
            }
        });

//REMET LES STATISTIQUES A ZERO QUAND LA FENETRE SE FERME
        parent.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Joueur joueur : joueurs) {
                    joueur.setStatistique(new Statistique());
                }
            }
        });
    }
    private void exporterJoueurs(JFrame parent) {
        try {
            File dossier = new File("sauvegarde");
            if (!dossier.exists()) {
                dossier.mkdirs();
            }

            JFileChooser fileChooser = new JFileChooser(dossier);
            fileChooser.setDialogTitle("Choisissez le nom du fichier à exporter");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int userSelection = fileChooser.showSaveDialog(parent);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fichier = fileChooser.getSelectedFile();

                if (!fichier.getName().endsWith(".ser")) {
                    fichier = new File(fichier.getPath() + ".ser");
                }

                List<Object> equipeData = List.of(EquipeNom, joueurs);

                Serializer.sauvegarder(equipeData, fichier.getPath());
                JOptionPane.showMessageDialog(parent, "Exportation réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent, "Erreur lors de l'exportation : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importerJoueurs(JFrame parent) {
        try {
            File dossier = new File("sauvegarde");
            if (!dossier.exists()) {
                dossier.mkdirs();
            }

            JFileChooser fileChooser = new JFileChooser(dossier);
            fileChooser.setDialogTitle("Choisissez un fichier à importer");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int userSelection = fileChooser.showOpenDialog(parent);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fichier = fileChooser.getSelectedFile();

                if (!fichier.exists() || fichier.length() == 0) {
                    JOptionPane.showMessageDialog(parent, "Le fichier sélectionné est invalide ou vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Charger la liste contenant le nom de l'équipe et les joueurs
                List<Object> equipeData = Serializer.charger(fichier.getPath());
                if (equipeData != null && equipeData.size() == 2) {
                    EquipeNom = (String) equipeData.get(0);
                    joueurs.clear();
                    joueurs.addAll((List<Joueur>) equipeData.get(1));

                    parent.dispose();
                    SwingUtilities.invokeLater(() -> new MatchStatsPage(EquipeNom, joueurs));

                    JOptionPane.showMessageDialog(parent, "Importation réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parent, "Le fichier ne contient pas de données valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(parent, "Erreur lors de l'importation : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

}