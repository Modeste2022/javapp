package controller;

import model.Equipe;
import model.Joueur;

import view.ViewInterface;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class JoueurController {
    private final DefaultTableModel joueurTableModel;
    private final ViewInterface view;

    public JoueurController(DefaultTableModel joueurTableModel, ViewInterface view) {
        this.joueurTableModel = joueurTableModel;
        this.view = view;
    }

    public void addJoueur() {
        int selectedEquipeIndex = view.getSelectedEquipeIndex();
        if (selectedEquipeIndex < 0) {
            view.showMessageDialog("Veuillez sélectionner une équipe avant d'ajouter un joueur.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipe equipe = view.getEquipeController().getEquipeByIndex(selectedEquipeIndex);
        if (equipe == null) {
            view.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] joueurData = view.openJoueurDialog(null);
        if (joueurData != null) {
            Joueur joueur = new Joueur(
                    joueurData[0],
                    joueurData[1],
                    joueurData[2],
                    Integer.parseInt(joueurData[3]),
                    Integer.parseInt(joueurData[4]),
                    Integer.parseInt(joueurData[6]),
                    joueurData[5],
                    Integer.parseInt(joueurData[7])
            );
            joueur.setId(equipe.generateJoueurId());
            joueur.setEquipeId(equipe.getId());
            equipe.ajouterJoueur(joueur);

            joueurTableModel.addRow(new Object[]{
                    joueur.getId(),
                    joueur.getNom(),
                    joueur.getPrenom(),
                    joueur.getDateNaissance(),
                    joueur.getTaille(),
                    joueur.getPoids(),
                    joueur.getPoste(),
                    joueur.getNumero(),
                    joueur.getAnneeRejoint()
            });
            view.showMessageDialog("Joueur ajouté avec succès !", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void editJoueur(int selectedRow) {
        if (selectedRow < 0) {
            view.showMessageDialog("Veuillez sélectionner un joueur à modifier.", JOptionPane.ERROR_MESSAGE);
        return;
        }

        int selectedEquipeIndex = view.getSelectedEquipeIndex();
        if (selectedEquipeIndex < 0) {
            view.showMessageDialog("Veuillez sélectionner une équipe.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipe equipe = view.getEquipeController().getEquipeByIndex(selectedEquipeIndex);
        if (equipe == null) {
            view.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int joueurId = (int) joueurTableModel.getValueAt(selectedRow, 0);
        Joueur joueur = null;
        for (Joueur j : equipe.getJoueurs()) {
            if (j.getId() == joueurId) {
                joueur = j;
                break;
            }
        }

        if (joueur == null) {
            view.showMessageDialog("Joueur introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] joueurData = {
                joueur.getNom(),
                joueur.getPrenom(),
                joueur.getDateNaissance(),
                String.valueOf(joueur.getTaille()),
                String.valueOf(joueur.getPoids()),
                joueur.getPoste(),
                String.valueOf(joueur.getNumero()),
                String.valueOf(joueur.getAnneeRejoint())
        };

        String[] updatedJoueurData = view.openJoueurDialog(joueurData);
        if (updatedJoueurData != null) {
            try {
                joueur.setNom(updatedJoueurData[0]);
                joueur.setPrenom(updatedJoueurData[1]);
                joueur.setDateNaissance(updatedJoueurData[2]);
                joueur.setTaille(Integer.parseInt(updatedJoueurData[3]));
                joueur.setPoids(Integer.parseInt(updatedJoueurData[4]));
                joueur.setPoste(updatedJoueurData[5]);
                joueur.setNumero(Integer.parseInt(updatedJoueurData[6]));
                joueur.setAnneeRejoint(Integer.parseInt(updatedJoueurData[7]));

                joueurTableModel.setValueAt(updatedJoueurData[0], selectedRow, 1);
                joueurTableModel.setValueAt(updatedJoueurData[1], selectedRow, 2);
                joueurTableModel.setValueAt(updatedJoueurData[2], selectedRow, 3);
                joueurTableModel.setValueAt(updatedJoueurData[3], selectedRow, 4);
                joueurTableModel.setValueAt(updatedJoueurData[4], selectedRow, 5);
                joueurTableModel.setValueAt(updatedJoueurData[5], selectedRow, 6);
                joueurTableModel.setValueAt(updatedJoueurData[6], selectedRow, 7);
                joueurTableModel.setValueAt(updatedJoueurData[7], selectedRow, 8);

                view.showMessageDialog("Modification réussie !", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                view.showMessageDialog("Erreur dans les données numériques.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteJoueur(int selectedRow) {
        if (selectedRow < 0) {
            view.showMessageDialog("Veuillez sélectionner un joueur à supprimer.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedEquipeIndex = view.getSelectedEquipeIndex();
        if (selectedEquipeIndex < 0) {
            view.showMessageDialog("Veuillez sélectionner une équipe.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipe equipe = view.getEquipeController().getEquipeByIndex(selectedEquipeIndex);
        if (equipe == null) {
            view.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int joueurId = (int) joueurTableModel.getValueAt(selectedRow, 0);
        Joueur joueur = null;
        for (Joueur j : equipe.getJoueurs()) {
            if (j.getId() == joueurId) {
                joueur = j;
                break;
            }
        }

        if (joueur != null && view.showConfirmationDialog("Êtes-vous sûr de vouloir supprimer le joueur " + joueur.getNom() + " ?")) {
            equipe.supprimerJoueur(joueur);
            joueurTableModel.removeRow(selectedRow);
            view.showMessageDialog("Joueur supprimé avec succès !", JOptionPane.INFORMATION_MESSAGE);
            loadJoueursByEquipe(equipe.getId());
        } else {
            view.showMessageDialog("Joueur introuvable.", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void handleAddJoueurAction(JButton ajouterJoueurBtn) {
        ajouterJoueurBtn.addActionListener(e -> addJoueur());
    }

    public void handleEditJoueurAction(JButton modifierJoueurBtn, JTable joueurTable) {
        modifierJoueurBtn.addActionListener(e -> editJoueur(joueurTable.getSelectedRow()));
    }

    public void handleDeleteJoueurAction(JButton supprimerJoueurBtn, JTable joueurTable) {
        supprimerJoueurBtn.addActionListener(e -> deleteJoueur(joueurTable.getSelectedRow()));
    }


    public void loadJoueursByEquipe(int equipeId) {
        joueurTableModel.setRowCount(0);
        Equipe equipe = view.getEquipeController().getEquipeById(equipeId);

        if (equipe != null) {
            for (Joueur joueur : equipe.getJoueurs()) {
                String dateNaissance = joueur.getDateNaissance();
                LocalDate naissanceDate = LocalDate.parse(dateNaissance, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String formattedDate = naissanceDate.format(view.getOutputFormatter());
                joueurTableModel.addRow(new Object[]{
                        joueur.getId(),
                        joueur.getNom(),
                        joueur.getPrenom(),
                        formattedDate,
                        joueur.getTaille(),
                        joueur.getPoids(),
                        joueur.getPoste(),
                        joueur.getNumero(),
                        joueur.getAnneeRejoint()
                });
            }
        }
    }
}