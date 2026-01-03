package view;

import javax.swing.*;
import java.awt.*;

public class EquipeDialog extends JDialog {
    private JTextField nomField;
    private JTextField paysField;
    private JTextField villeField;
    private boolean confirmed = false;

    public EquipeDialog(JFrame parent, String[] existingData) {
        super(parent, "Équipe", true);
        initializeDialog();

        // Si des données existent, les préremplir
        if (existingData != null) {
            nomField.setText(existingData[0]);
            paysField.setText(existingData[1]);
            villeField.setText(existingData[2]);
        }
    }

    private void initializeDialog() {
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nom de l'équipe :"));
        nomField = new JTextField();
        formPanel.add(nomField);

        formPanel.add(new JLabel("Pays :"));
        paysField = new JTextField();
        formPanel.add(paysField);

        formPanel.add(new JLabel("Ville :"));
        villeField = new JTextField();
        formPanel.add(villeField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Annuler");

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
    }

    public String[] getEquipeData() {
        if (confirmed) {
            try {
                String nom = nomField.getText().trim();
                String pays = paysField.getText().trim();
                String ville = villeField.getText().trim();

                // Vérification des champs
                if (nom.length() < 3) {
                    throw new IllegalArgumentException("Le nom doit contenir au moins 3 lettres.");
                }
                if (pays.length() < 3) {
                    throw new IllegalArgumentException("Le pays doit contenir au moins 3 lettres.");
                }
                if (ville.length() < 3) {
                    throw new IllegalArgumentException("La ville doit contenir au moins 3 lettres.");
                }

                return new String[]{nom, pays, ville};
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }
        }

        return null;
    }
}