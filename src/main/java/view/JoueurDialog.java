package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class JoueurDialog extends JDialog {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateNaissanceField;
    private JTextField tailleField;
    private JTextField poidsField;
    private JComboBox<String> posteComboBox;
    private JTextField numeroField;
    private JTextField anneeRejointField;
    private boolean confirmed = false;

    public JoueurDialog(JFrame parent, String[] joueurInfo) {
        super(parent, "Joueur", true);
        initializeDialog();

        if (joueurInfo != null) {
            nomField.setText(joueurInfo[0]);
            prenomField.setText(joueurInfo[1]);
            dateNaissanceField.setText(joueurInfo[2]);
            tailleField.setText(joueurInfo[3]);
            poidsField.setText(joueurInfo[4]);
            posteComboBox.setSelectedItem(joueurInfo[5]);
            numeroField.setText(joueurInfo[6]);
            anneeRejointField.setText(joueurInfo[7]);
        }
    }

    private void initializeDialog() {
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        formPanel.add(nomField);

        formPanel.add(new JLabel("Prénom :"));
        prenomField = new JTextField();
        formPanel.add(prenomField);

        formPanel.add(new JLabel("Date de naissance :"));
        dateNaissanceField = new JTextField();
        formPanel.add(dateNaissanceField);

        formPanel.add(new JLabel("Taille :"));
        tailleField = new JTextField();
        formPanel.add(tailleField);

        formPanel.add(new JLabel("Poids :"));
        poidsField = new JTextField();
        formPanel.add(poidsField);

        formPanel.add(new JLabel("Poste :"));
        posteComboBox = new JComboBox<>(new String[]{"Meneur", "Arrière", "Ailier", "Ailier fort", "Pivot"});
        formPanel.add(posteComboBox);

        formPanel.add(new JLabel("Numéro :"));
        numeroField = new JTextField();
        formPanel.add(numeroField);

        formPanel.add(new JLabel("Année de rejoint :"));
        anneeRejointField = new JTextField();
        formPanel.add(anneeRejointField);

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

    private String dateFormatter(String inputDate) throws IllegalArgumentException {
        DateTimeFormatter inputFormatter1 = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter inputFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(inputDate, inputFormatter1).format(outputFormatter);
        } catch (DateTimeParseException e1) {
            try {
                return LocalDate.parse(inputDate, inputFormatter2).format(outputFormatter);
            } catch (DateTimeParseException e2) {
                try {
                    return LocalDate.parse(inputDate, inputFormatter3).format(outputFormatter);
                } catch (DateTimeParseException e3) {
                    throw new IllegalArgumentException("La date doit être au format d/M/yyyy ou dd/MM/yyyy ou yyyy-MM-dd .");
                }
            }
        }
    }

    public String[] getJoueurData() {
        if (confirmed) {
            try {
                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                String dateNaissance = dateFormatter(dateNaissanceField.getText().trim());
                int taille = Integer.parseInt(tailleField.getText().trim());
                int poids = Integer.parseInt(poidsField.getText().trim());
                String poste = (String) posteComboBox.getSelectedItem();
                int numero = Integer.parseInt(numeroField.getText().trim());
                int anneeRejoint = Integer.parseInt(anneeRejointField.getText().trim());

                if (nom.length() < 3) {
                    throw new IllegalArgumentException("Le nom doit contenir au moins 3 caractères.");
                }
                if (prenom.length() < 3) {
                    throw new IllegalArgumentException("Le prénom doit contenir au moins 3 caractères.");
                }

                LocalDate naissanceDate = LocalDate.parse(dateNaissance, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (naissanceDate.isAfter(LocalDate.now())) {
                    throw new IllegalArgumentException("La date de naissance ne peut pas être dans le futur.");
                }
                if (naissanceDate.getYear() < 1940) {
                    throw new IllegalArgumentException("L'année de naissance ne peut pas être avant 1940.");
                }

                return new String[]{
                        nom, prenom, dateNaissance,
                        String.valueOf(taille), String.valueOf(poids),
                        poste, String.valueOf(numero), String.valueOf(anneeRejoint)
                };
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Les champs numériques doivent contenir des nombres valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return null;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }
}