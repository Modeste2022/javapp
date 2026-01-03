package controller;

import model.Equipe;
import model.dao.IDAOEquipe;
import util.EquipeExporter;
import util.EquipeImporter;
import view.ViewInterface;

import javax.swing.*;
import java.util.List;

public final class EquipeController {
    private final IDAOEquipe model; // Modèle DAO pour gérer les équipes
    private final DefaultListModel<String> equipeListModel; // Modèle pour la vue
    private final ViewInterface view; // Référence à la view

    public EquipeController(IDAOEquipe model, DefaultListModel<String> equipeListModel, ViewInterface view) {
        this.model = model;
        this.equipeListModel = equipeListModel;
        this.view = view;
    }


    public void addEquipe() {
        String[] equipeData = view.openEquipeDialog(null); // null pour ajout
        if (equipeData != null) {
            Equipe nouvelleEquipe = new Equipe(0, equipeData[0], equipeData[1], equipeData[2]); // Créer une équipe temporaire
            if (nouvelleEquipe.existeDeja(model.getEquipes())) {
                view.showMessageDialog("Une équipe avec le même nom, pays et ville existe déjà.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int equipeId = model.addEquipe(equipeData[0], equipeData[1], equipeData[2]); // Ajouter l'équipe
            nouvelleEquipe = new Equipe(equipeId, equipeData[0], equipeData[1], equipeData[2]); // Inclure l'ID
            equipeListModel.addElement(nouvelleEquipe.getNom()); // Mise à jour de la liste
            view.showMessageDialog("Nouvelle équipe ajoutée avec succès !", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public void editEquipe(int selectedIndex) {
        if (selectedIndex < 0) {
            view.showMessageDialog("Veuillez sélectionner une équipe à modifier.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedEquipeName = equipeListModel.getElementAt(selectedIndex);
        Equipe equipe = null;
        for (Equipe e : model.getEquipes()) {
            if (e.getNom().equals(selectedEquipeName)) {
                equipe = e;
                break;
            }
        }
        if (equipe == null) {
            view.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Préremplir avec les données de l'équipe existante
        String[] equipeData = {equipe.getNom(), equipe.getPays(), equipe.getVille()};
        String[] updatedEquipeData = view.openEquipeDialog(equipeData);

        if (updatedEquipeData != null) {
            equipe.setNom(updatedEquipeData[0]);
            equipe.setPays(updatedEquipeData[1]);
            equipe.setVille(updatedEquipeData[2]);
            model.updateEquipe(equipe);

            equipeListModel.set(selectedIndex, equipe.getNom()); // Mise à jour de la liste
            view.showMessageDialog("Modification effectuée avec succès !", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Méthode pour supprimer une équipe existante.
     */
    public void deleteEquipe(int selectedIndex) {
        if (selectedIndex < 0) {
            view.showMessageDialog("Veuillez sélectionner une équipe à supprimer.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String equipeName = equipeListModel.getElementAt(selectedIndex);
        Equipe equipe = null;
        for (Equipe e : model.getEquipes()) {
            if (e.getNom().equals(equipeName)) {
                equipe = e;
                break;
            }
        }
        if (equipe != null && view.showConfirmationDialog("Êtes-vous sûr de vouloir supprimer l'équipe : " + equipe.getNom() + " ?")) {
            model.deleteEquipe(equipe);
            equipeListModel.remove(selectedIndex);
            view.showMessageDialog("Équipe supprimée avec succès !", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public int getEquipeIdByIndex(int index) {
        if (index < 0 || index >= model.getEquipes().size()) {
            return -1;
        }
        return model.getEquipes().get(index).getId(); // Retourne l'ID de l'équipe
    }

    public Equipe getEquipeByIndex(int index) {
        if (index < 0 || index >= model.getEquipes().size()) {
            return null;
        }
        return model.getEquipes().get(index);
    }

    public Equipe getEquipeById(int id) {
        return model.getEquipeById(id);
    }

    public void handleAddEquipeAction(JButton ajouterEquipeBtn) {
        ajouterEquipeBtn.addActionListener(e -> addEquipe());
    }

    public void handleEditEquipeAction(JButton modifierEquipeBtn, JList<String> equipeList) {
        modifierEquipeBtn.addActionListener(e -> {
            int selectedIndex = equipeList.getSelectedIndex();
            editEquipe(selectedIndex);
        });
    }

    public void handleDeleteEquipeAction(JButton supprimerEquipeBtn, JList<String> equipeList) {
        supprimerEquipeBtn.addActionListener(e -> {
            int selectedIndex = equipeList.getSelectedIndex();
            deleteEquipe(selectedIndex);
        });
    }
    public void exportEquipe(int selectedIndex) {
        if (selectedIndex < 0) {
            view.showMessageDialog("Veuillez sélectionner une équipe à exporter.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipe equipe = getEquipeByIndex(selectedIndex);
        if (equipe == null) {
            view.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EquipeExporter.exportEquipe(view.getFrame(), equipe);
    }

    public void importEquipe() {
        EquipeImporter.importEquipe(view.getFrame(), model);
        loadEquipes(); // Recharge les équipes après l'importation
    }


    public void loadEquipes() {
        List<Equipe> equipes = model.getEquipes();
        equipeListModel.clear();
        for (Equipe equipe : equipes) {
            equipeListModel.addElement(equipe.getNom());
        }
    }
}
