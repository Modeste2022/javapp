package view;

import authenticator.Authenticator;

import javax.swing.*;
import java.awt.*;

public class ConnexionFrame extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private final Authenticator authenticator;  // <-- On utilise celui passé par MainFrame
    private String loggedInUser;

    public ConnexionFrame(JFrame parent, Authenticator authenticator) {
        super(parent, "Connexion", true);
        this.authenticator = authenticator;     // <-- On garde la bonne instance
        initializeDialog();
        setLocationRelativeTo(parent);
    }

    private void initializeDialog() {
        setSize(400, 200);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nom d'utilisateur:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("Connexion");
        JButton addUserButton = new JButton("Ajouter utilisateur");
        JButton cancelButton = new JButton("Annuler");

        loginButton.addActionListener(e -> handleLogin());
        addUserButton.addActionListener(e -> handleAddUser());
        cancelButton.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(loginButton);

        buttonPanel.add(loginButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (authenticator.authenticate(username, password)) {  // <-- On utilise le bon authenticator
            JOptionPane.showMessageDialog(this, "Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            loggedInUser = username;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private void handleAddUser() {
        String newUsername = JOptionPane.showInputDialog(this, "Entrez le nouveau nom d'utilisateur:");
        if (newUsername == null || newUsername.trim().isEmpty()) return;

        String newPassword = JOptionPane.showInputDialog(this, "Entrez le mot de passe pour l'utilisateur:");
        if (newPassword == null || newPassword.trim().isEmpty()) return;

        try {
            // On suppose que l'authenticator a une méthode addUser()
            authenticator.addUser(newUsername.trim(), newPassword);
            JOptionPane.showMessageDialog(this, "Utilisateur ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }
}