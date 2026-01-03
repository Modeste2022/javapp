package view;

import controller.FinalController;
import model.Joueur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;


public class MatchStatsPage extends JFrame {

    private static final String[] COLUMNS = {
            "Joueur", "3pts", "2pts", "1pt", "Total Pts", "Rebonds", "Assists", "Fautes", "Contres"
    };


    public MatchStatsPage(String equipeNom, List<Joueur> joueurs) {

       FinalController controller = new FinalController(joueurs,equipeNom);

        //menu de sauvegarde et d'importation
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Fichier");
        JMenuItem importItem = new JMenuItem("Importer");
        JMenuItem exportItem = new JMenuItem("Exporter");
        fileMenu.add(importItem);
        fileMenu.add(exportItem);
        menuBar.add(fileMenu);

        // Ajout des écouteurs via le contrôleur
        controller.ajouterEcouteurs(importItem, exportItem, this);

        // Tri des joueurs par points marqués
        joueurs.sort(Comparator.comparingInt(j -> j.getStatistique().getTotalPoints()));

        setTitle("Résumé du match - " + equipeNom);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        //setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 71, 27)); // vert Bucks

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(0, 71, 27)); // même couleur que le fond

        JLabel titleLabel = new JLabel("Résumé des statistiques du match", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        headerPanel.add(titleLabel);

        JLabel equipeLabel = new JLabel("Équipe : " + equipeNom, SwingConstants.CENTER);
        equipeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        equipeLabel.setForeground(Color.WHITE);
        equipeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(equipeLabel);


        mainPanel.add(headerPanel, BorderLayout.NORTH);
        // === Tableau des statistiques ===
        DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0);
        int totalPoints = 0, totalRebonds = 0, totalAssists = 0, totalFautes = 0, totalContres = 0;
        int total3pts = 0, total2pts = 0, total1pt = 0;

        for (Joueur joueur : joueurs) {
            int _3pts = joueur.getStatistique().getStat("3PTS");
            int _2pts = joueur.getStatistique().getStat("2PTS");
            int _1pt = joueur.getStatistique().getStat("1PT");
            int rebonds = joueur.getStatistique().getStat("rebonds");
            int assists = joueur.getStatistique().getStat("assists");
            int fautes = joueur.getStatistique().getStat("fautes");
            int contres = joueur.getStatistique().getStat("contres");
            int total = joueur.getStatistique().getTotalPoints();

            total3pts += _3pts;
            total2pts += _2pts;
            total1pt += _1pt;
            totalPoints += total;
            totalRebonds += rebonds;
            totalAssists += assists;
            totalFautes += fautes;
            totalContres += contres;

            tableModel.addRow(new Object[]{
                    joueur.getNom() + " (N°" + joueur.getNumero() + ")",
                    _3pts, _2pts, _1pt, total, rebonds, assists, fautes, contres
            });
        }

        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre le tableau non modifiable
            }
        };

        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 300));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Calcul des pourcentages
        int totalTirs = total3pts + total2pts + total1pt;

        String pourcentage3pts;
        if (totalTirs > 0) {
            double ratio3pts = (total3pts * 100.0) / totalTirs;
            pourcentage3pts = String.format("%.2f%%", ratio3pts);
        } else {
            pourcentage3pts = "0%";
        }

        String pourcentage2pts;
        if (totalTirs > 0) {
            double ratio2pts = (total2pts * 100.0) / totalTirs;
            pourcentage2pts = String.format("%.2f%%", ratio2pts);
        } else {
            pourcentage2pts = "0%";
        }

        String pourcentage1pt;
        if (totalTirs > 0) {
            double ratio1pt = (total1pt * 100.0) / totalTirs;
            pourcentage1pt = String.format("%.2f%%", ratio1pt);
        } else {
            pourcentage1pt = "0%";
        }


        //Cartes de statistiques
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 3, 10, 10));
        statsPanel.setBackground(new Color(255, 255, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        statsPanel.add(createStatCard("Total points", String.valueOf(totalPoints)));
        statsPanel.add(createStatCard("Total fautes", String.valueOf(totalFautes)));
        statsPanel.add(createStatCard("Total Rebonds ", String.valueOf(totalRebonds)));

        statsPanel.add(createStatCard("% 3pts", pourcentage3pts));
        statsPanel.add(createStatCard("% 2pts", pourcentage2pts));
        statsPanel.add(createStatCard("% lancers-francs", pourcentage1pt));

        mainPanel.add(statsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
    }

    private JPanel createStatCard(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 102, 51));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }


}