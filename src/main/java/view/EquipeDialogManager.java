package view;

import javax.swing.*;

public class EquipeDialogManager {
    private final JFrame parent;

    public EquipeDialogManager(JFrame parent) {
        this.parent = parent;
    }

    public String[] openEquipeDialog(String[] currentData) {
        EquipeDialog dialog = new EquipeDialog(parent, currentData);
        dialog.setVisible(true);
        return dialog.getEquipeData();
    }
}