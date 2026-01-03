package view;

import authenticator.Authenticator;
import authenticator.InMemoryAuthenticator;
import authenticator.PropertiesAuthenticator;

import controller.EquipeController;
import controller.JoueurController;
import controller.StatsController;

import model.dao.DAOEquipe;
import model.dao.IDAOEquipe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class MainFrame implements ViewInterface {

    private String currentUser = null; // Sert à suivre le statut de l'utilisateur connecté
    private Authenticator authenticator; // Authentificateur dynamique (Template Method)

    private final JFrame frame; // Fenêtre principale
    private final JList<String> equipeList;
    private final EquipeController equipeController;
    private final JoueurController joueurController;
    private final EquipeDialogManager equipeDialogManager;
    private final JoueurDialogManager joueurDialogManager;
    private DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MainFrame() {

        // Authentification par défaut : fichier properties
        authenticator = new PropertiesAuthenticator();

        // Initialisation de la fenêtre principale
        frame = new JFrame("Gestion des équipes et joueurs de basket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);
        frame.setMinimumSize(new Dimension(1400, 400));
        frame.setLayout(new BorderLayout());

        // ================================
        // Menu de navigation et statut utilisateur
        // ================================
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        menuBar.setBackground(new Color(0x00471B)); // Couleur de fond du menu

        JMenu fileMenu = new JMenu("Fichier");
        fileMenu.setForeground(Color.WHITE);

        JMenuItem importItem = new JMenuItem("Importer");
        JMenuItem exportItem = new JMenuItem("Exporter");
        fileMenu.add(importItem);
        fileMenu.add(exportItem);

        JMenu sessionMenu = new JMenu("Session");
        sessionMenu.setForeground(Color.WHITE);
        JMenuItem loginItem = new JMenuItem("Se connecter");
        JMenuItem logoutItem = new JMenuItem("Se déconnecter");
        logoutItem.setEnabled(false); // "Déconnecter" désactivé par défaut
        sessionMenu.add(loginItem);
        sessionMenu.add(logoutItem);

        JMenu parametreMenu = new JMenu("Paramètres");
        parametreMenu.setForeground(Color.WHITE);

        JMenuItem formatDate = new JMenuItem("Format date");
        JMenuItem authMemory = new JMenuItem("Authentification en mémoire");
        JMenuItem authFile = new JMenuItem("Authentification via fichier");

        parametreMenu.add(formatDate);
        parametreMenu.add(authMemory);
        parametreMenu.add(authFile);

        menuBar.add(fileMenu);
        menuBar.add(sessionMenu);
        menuBar.add(parametreMenu);

        JLabel userInfoLabel = new JLabel("Non connecté");
        userInfoLabel.setForeground(Color.WHITE);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(userInfoLabel);

        // Panel sud contenant un "bouton suivant"
        JButton suivantButton = new JButton("Suivant");
        suivantButton.setBackground(new Color(0x00471B));
        suivantButton.setForeground(Color.WHITE);
        suivantButton.setPreferredSize(new Dimension(120, 40));
        suivantButton.setEnabled(false); // Désactivé si non connecté

        // ================================
        // Actions pour la connexion
        // ================================
        loginItem.addActionListener(e -> {
            ConnexionFrame connexionFrame = new ConnexionFrame(frame, authenticator);
            connexionFrame.setVisible(true);

            String loggedInUser = connexionFrame.getLoggedInUser();
            if (loggedInUser != null) {
                currentUser = loggedInUser;
                userInfoLabel.setText("Connecté en tant que : " + currentUser);
                userInfoLabel.setForeground(Color.GREEN);
                loginItem.setEnabled(false);
                logoutItem.setEnabled(true);
                suivantButton.setEnabled(true);
            }
        });

        logoutItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Êtes-vous sûr de vouloir vous déconnecter ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                currentUser = null;
                userInfoLabel.setText("Non connecté");
                userInfoLabel.setForeground(Color.BLUE);
                loginItem.setEnabled(true);
                logoutItem.setEnabled(false);
                suivantButton.setEnabled(false);
                JOptionPane.showMessageDialog(frame, "Vous êtes déconnecté.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // ================================
        // Choix de la méthode d’authentification
        // ================================
        authMemory.addActionListener(e -> {
            authenticator = new InMemoryAuthenticator();
            showMessageDialog("Authentification en mémoire activée", JOptionPane.INFORMATION_MESSAGE);
        });

        authFile.addActionListener(e -> {
            authenticator = new PropertiesAuthenticator();
            showMessageDialog("Authentification via fichier activée", JOptionPane.INFORMATION_MESSAGE);
        });

        // ================================
        // Panneaux des équipes
        // ================================
        DefaultListModel<String> equipeListModel = new DefaultListModel<>();
        equipeList = new JList<>(equipeListModel);
        equipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane equipeScrollPane = new JScrollPane(equipeList);

        JButton ajouterEquipeBtn = new JButton("Ajouter équipe");
        ajouterEquipeBtn.setBackground(new Color(0x00471B));
        ajouterEquipeBtn.setForeground(Color.WHITE);

        JButton modifierEquipeBtn = new JButton("Modifier équipe");
        modifierEquipeBtn.setBackground(new Color(0x00471B));
        modifierEquipeBtn.setForeground(Color.WHITE);

        JButton supprimerEquipeBtn = new JButton("Supprimer équipe");
        supprimerEquipeBtn.setBackground(new Color(0x00471B));
        supprimerEquipeBtn.setForeground(Color.WHITE);

        JPanel equipeButtonPanel = new JPanel(new FlowLayout());
        equipeButtonPanel.add(ajouterEquipeBtn);
        equipeButtonPanel.add(modifierEquipeBtn);
        equipeButtonPanel.add(supprimerEquipeBtn);

        JPanel equipePanel = new JPanel(new BorderLayout());
        equipePanel.setBorder(BorderFactory.createTitledBorder("Équipes"));
        equipePanel.add(equipeScrollPane, BorderLayout.CENTER);
        equipePanel.add(equipeButtonPanel, BorderLayout.SOUTH);

        // ================================
        // Panneau des joueurs (AVEC IMAGE COMME AVANT)
        // ================================
        String[] colonnes = {"ID", "Nom", "Prénom", "Date de naissance", "Taille", "Poids", "Poste", "Numero", "Année de rejoint"};
        DefaultTableModel joueurTableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre les cellules non éditables
            }
        };

        JTable joueurTable = new JTable(joueurTableModel);
        joueurTable.getTableHeader().setReorderingAllowed(false);

        // Rendre le tableau transparent
        joueurTable.setOpaque(false);
        joueurTable.setBackground(new Color(0, 0, 0, 0));

        JScrollPane joueurScrollPane = new JScrollPane(joueurTable);
        joueurScrollPane.setOpaque(false);
        joueurScrollPane.getViewport().setOpaque(false);

        // Boutons joueurs (séparés de l'image)
        JButton ajouterJoueurBtn = new JButton("Ajouter joueur");
        ajouterJoueurBtn.setBackground(new Color(0x00471B));
        ajouterJoueurBtn.setForeground(Color.WHITE);

        JButton modifierJoueurBtn = new JButton("Modifier joueur");
        modifierJoueurBtn.setBackground(new Color(0x00471B));
        modifierJoueurBtn.setForeground(Color.WHITE);

        JButton supprimerJoueurBtn = new JButton("Supprimer joueur");
        supprimerJoueurBtn.setBackground(new Color(0x00471B));
        supprimerJoueurBtn.setForeground(Color.WHITE);

        JPanel joueurButtonPanel = new JPanel(new FlowLayout());
        joueurButtonPanel.add(ajouterJoueurBtn);
        joueurButtonPanel.add(modifierJoueurBtn);
        joueurButtonPanel.add(supprimerJoueurBtn);

        // Ajouter l'image d'arrière-plan (COMME AVANT)
        ImageIcon backgroundIcon = new ImageIcon(getClass().getClassLoader().getResource("img.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout()); // Permet d'ajouter des composants par-dessus

        // Ajouter SEULEMENT le tableau sur l'image
        backgroundLabel.add(joueurScrollPane, BorderLayout.CENTER);

        // Ajouter le panneau des joueurs avec l'arrière-plan
        JPanel joueurPanel = new JPanel(new BorderLayout());
        joueurPanel.setBorder(BorderFactory.createTitledBorder("Joueurs"));
        joueurPanel.add(backgroundLabel, BorderLayout.CENTER);

        // Les boutons sont en dessous, PAS sur l'image
        joueurPanel.add(joueurButtonPanel, BorderLayout.SOUTH);

        // ================================
        // Bas de page
        // ================================
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        southPanel.add(suivantButton);

        frame.add(equipePanel, BorderLayout.WEST);
        frame.add(joueurPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        // ================================
        // Création des DAO et contrôleurs
        // ================================
        IDAOEquipe daoEquipe = new DAOEquipe();
        equipeDialogManager = new EquipeDialogManager(frame);
        joueurDialogManager = new JoueurDialogManager(frame);

        // Instanciation des contrôleurs avec passage de la MainFrame
        equipeController = new EquipeController(daoEquipe, equipeListModel, this);
        joueurController = new JoueurController(joueurTableModel, this);

        // Connexions des actions liées aux équipes
        equipeController.handleAddEquipeAction(ajouterEquipeBtn);
        equipeController.handleEditEquipeAction(modifierEquipeBtn, equipeList);
        equipeController.handleDeleteEquipeAction(supprimerEquipeBtn, equipeList);

        // Connexions des actions liées aux joueurs
        joueurController.handleAddJoueurAction(ajouterJoueurBtn);
        joueurController.handleEditJoueurAction(modifierJoueurBtn, joueurTable);
        joueurController.handleDeleteJoueurAction(supprimerJoueurBtn, joueurTable);

        // Connexion du bouton "Suivant"
        StatsController statsController = new StatsController(this);
        suivantButton.addActionListener(e -> statsController.handleOpenEncoderStats());

        // Ajout d'un écouteur pour afficher les joueurs de l'équipe sélectionnée
        equipeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Éviter les événements multiples
                int selectedIndex = equipeList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    int equipeId = equipeController.getEquipeIdByIndex(selectedIndex); // Récupérer l'ID de l'équipe
                    joueurController.loadJoueursByEquipe(equipeId); // Charger les joueurs de l'équipe
                } else {
                    joueurController.loadJoueursByEquipe(-1); // Aucun joueur si aucune équipe sélectionnée
                }
            }
        });

        exportItem.addActionListener(e -> {
            int selectedEquipeIndex = getSelectedEquipeIndex();
            equipeController.exportEquipe(selectedEquipeIndex);
        });

        importItem.addActionListener(e -> equipeController.importEquipe());

        formatDate.addActionListener(e -> {
            String[] options = {"yyyy-MM-dd", "dd/MM/yyyy"};
            String choix = (String) JOptionPane.showInputDialog(
                    frame,
                    "Choisissez le format de date :",
                    "Format de date",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choix != null) {
                setOutputFormatter(DateTimeFormatter.ofPattern(choix));
                showMessageDialog("Format de date changé en : " + choix, JOptionPane.INFORMATION_MESSAGE);
                equipeController.loadEquipes();
            }
        });

        frame.setVisible(true);
    }

    // ================================
    // Implémentation ViewInterface
    // ================================
    @Override
    public int getSelectedEquipeIndex() {
        return equipeList.getSelectedIndex();
    }

    @Override
    public EquipeController getEquipeController() {
        return equipeController;
    }

    @Override
    public String[] openEquipeDialog(String[] currentData) {
        return equipeDialogManager.openEquipeDialog(currentData);
    }

    @Override
    public String[] openJoueurDialog(String[] currentData) {
        return joueurDialogManager.openJoueurDialog(currentData);
    }

    @Override
    public void showMessageDialog(String message, int messageType) {
        JOptionPane.showMessageDialog(frame, message, "Message", messageType);
    }

    @Override
    public boolean showConfirmationDialog(String message) {
        int result = JOptionPane.showConfirmDialog(frame, message, "Confirmation", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }

    @Override
    public DateTimeFormatter getOutputFormatter() {
        return outputFormatter;
    }

    public void setOutputFormatter(DateTimeFormatter outputFormatter) {
        this.outputFormatter = outputFormatter;
    }
}