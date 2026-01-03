package util;

import model.Equipe;
import model.Joueur;
import model.dao.IDAOEquipe;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EquipeImporter {

    public static void importEquipe(JFrame parentFrame, IDAOEquipe daoEquipe) {
        JFileChooser fileChooser = new JFileChooser(new File("src/main/resources/equipe"));
        fileChooser.setDialogTitle("Choisissez un fichier à importer");
        int userSelection = fileChooser.showOpenDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToImport = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileToImport))) {
                String line;
                String nom = null, pays = null, ville = null;
                Equipe equipe = null;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("Équipe :")) {
                        nom = line.substring(8).trim();
                    } else if (line.startsWith("Pays :")) {
                        pays = line.substring(6).trim();
                    } else if (line.startsWith("Ville :")) {
                        ville = line.substring(7).trim();
                        if (nom != null && pays != null && ville != null) {
                            Equipe tempEquipe = new Equipe(0, nom, pays, ville);
                            if (tempEquipe.existeDeja(daoEquipe.getEquipes())) {
                                JOptionPane.showMessageDialog(parentFrame,
                                        "L'équipe \"" + nom + "\" existe déjà.",
                                        "Erreur",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            int equipeId = daoEquipe.addEquipe(nom, pays, ville);
                            equipe = new Equipe(equipeId, nom, pays, ville);
                        }
                    } else if (line.startsWith("-") && equipe != null) {
                        String[] joueurData = line.substring(1).split(", ");
                        String[] nomPrenom = joueurData[0].trim().split(" ");
                        String dateNaissance = joueurData[1].split(":")[1].trim();
                        int taille = Integer.parseInt(joueurData[2].split(":")[1].trim().replace(" cm", ""));
                        int poids = Integer.parseInt(joueurData[3].split(":")[1].trim().replace(" kg", ""));
                        String poste = joueurData[4].split(":")[1].trim();
                        int numero = Integer.parseInt(joueurData[5].split(":")[1].trim());
                        int anneeRejoint = Integer.parseInt(joueurData[6].split(":")[1].trim());

                        Joueur joueur = new Joueur(nomPrenom[0], nomPrenom[1], dateNaissance, taille, poids, numero, poste, anneeRejoint);
                        joueur.setId(equipe.generateJoueurId());
                        joueur.setEquipeId(equipe.getId());
                        equipe.ajouterJoueur(joueur);
                        daoEquipe.addJoueurToEquipe(equipe.getId(), joueur); // Ajout dans DAOEquipe
                    }
                }

                JOptionPane.showMessageDialog(parentFrame, "Importation réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(parentFrame, "Erreur lors de l'importation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}