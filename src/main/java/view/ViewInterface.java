package view;

import controller.EquipeController;

import java.time.format.DateTimeFormatter;

public interface ViewInterface {
    String[] openEquipeDialog(String[] currentData);
    String[] openJoueurDialog(String[] currentData);
    void showMessageDialog(String message, int messageType);
    boolean showConfirmationDialog(String message);
    int getSelectedEquipeIndex();
    EquipeController getEquipeController();
    DateTimeFormatter getOutputFormatter();
    javax.swing.JFrame getFrame();
}