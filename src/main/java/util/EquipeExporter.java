package util;

import model.Equipe;
import model.Joueur;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EquipeExporter {

    public static void exportEquipe(JFrame parentFrame, Equipe equipe) {
        if (equipe == null) {
            JOptionPane.showMessageDialog(parentFrame, "Équipe introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser(new File("src/main/resources/equipe"));
        fileChooser.setDialogTitle("Choisissez l'emplacement pour exporter l'équipe");
        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write("Équipe : " + equipe.getNom());
                writer.newLine();
                writer.write("Pays : " + equipe.getPays());
                writer.newLine();
                writer.write("Ville : " + equipe.getVille());
                writer.newLine();
                writer.write("Joueurs :");
                writer.newLine();

                for (Joueur joueur : equipe.getJoueurs()) {
                    writer.write("- " + joueur.getNom() + " " + joueur.getPrenom() + ", " +
                            "Date de naissance : " + joueur.getDateNaissance() + ", " +
                            "Taille : " + joueur.getTaille() + " cm, " +
                            "Poids : " + joueur.getPoids() + " kg, " +
                            "Poste : " + joueur.getPoste() + ", " +
                            "Numéro : " + joueur.getNumero() + ", " +
                            "Année de rejoint : " + joueur.getAnneeRejoint());
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(parentFrame, "Équipe exportée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parentFrame, "Erreur lors de l'exportation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}