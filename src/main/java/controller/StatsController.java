package controller;

import model.Equipe;
import model.Joueur;
import view.EncoderStats;
import view.MainFrame;
import view.MatchStatsPage;

import javax.swing.*;

public class StatsController {
    private final MainFrame mainFrame;

    public StatsController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void handleOpenEncoderStats() {
        int selectedEquipeIndex = mainFrame.getSelectedEquipeIndex();
        if (selectedEquipeIndex < 0) {
            mainFrame.showMessageDialog("Veuillez sélectionner une équipe avant de continuer.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipe equipe = mainFrame.getEquipeController().getEquipeByIndex(selectedEquipeIndex);
        if (equipe != null) {
            // Initialisez l'interface EncoderStats
            EncoderStats encoderStats = new EncoderStats(equipe.getNom(), equipe.getJoueurs());

            // Ajoutez des écouteurs aux boutons
            encoderStats.setPlusButtonListener(e -> handleIncrementStat(encoderStats));
            encoderStats.setMinusButtonListener(e -> handleDecrementStat(encoderStats));
            encoderStats.setStatsButtonListener(e -> handleViewStats(equipe));
        } else {
            mainFrame.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleIncrementStat(EncoderStats encoderStats) {
        JTable table = encoderStats.getTable();
        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();

        if (isStatColumnValid(selectedRow, selectedColumn, table)) {
            Joueur joueur = encoderStats.getJoueurs().get(selectedRow);
            String statType = table.getColumnName(selectedColumn);
            joueur.getStatistique().incrementer(statType);

            updateStatInView(encoderStats, table, selectedRow, selectedColumn, joueur, statType);
        } else {
            encoderStats.showMessage("Veuillez sélectionner une cellule valide.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDecrementStat(EncoderStats encoderStats) {
        JTable table = encoderStats.getTable();
        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();

        if (isStatColumnValid(selectedRow, selectedColumn, table)) {
            Joueur joueur = encoderStats.getJoueurs().get(selectedRow);
            String statType = table.getColumnName(selectedColumn);
            joueur.getStatistique().decrementer(statType);

            updateStatInView(encoderStats, table, selectedRow, selectedColumn, joueur, statType);
        } else {
            encoderStats.showMessage("Veuillez sélectionner une cellule valide.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isStatColumnValid(int selectedRow, int selectedColumn, JTable table) {
        return selectedRow >= 0 && selectedColumn > 0 && selectedColumn < table.getColumnCount() - 1;
    }

    private void updateStatInView(EncoderStats encoderStats, JTable table, int row, int column, Joueur joueur, String statType) {
        table.setValueAt(joueur.getStatistique().getStat(statType), row, column);
        table.setValueAt(joueur.getStatistique().getTotalPoints(), row, table.getColumnCount() - 1);
    }

    private void handleViewStats(Equipe equipe) {
        SwingUtilities.invokeLater(() -> new MatchStatsPage(equipe.getNom(), equipe.getJoueurs()));
    }

}