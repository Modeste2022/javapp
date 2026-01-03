// Fichier : src/model/dao/DAOEquipe.java
package model.dao;

import model.Equipe;
import model.Joueur;
import util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class DAOEquipe implements IDAOEquipe {
    private final ArrayList<Equipe> equipes = new ArrayList<>();
    private final IdGenerator idGenerator = new IdGenerator();

    @Override
    public int addEquipe(String nom, String pays, String ville) {
        int id = idGenerator.generateId();
        Equipe equipe = new Equipe(id, nom, pays, ville);
        equipes.add(equipe);
        return id;
    }

    @Override
    public boolean updateEquipe(Equipe equipe) {
        for (int i = 0; i < equipes.size(); i++) {
            if (equipes.get(i).getId() == equipe.getId()) {
                equipes.set(i, equipe);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteEquipe(Equipe equipe) {
        return equipes.remove(equipe);
    }

    @Override
    public Equipe getEquipeById(int id) {
        for (Equipe equipe : equipes) {
            if (equipe.getId() == id) {
                return equipe;
            }
        }
        return null;
    }
    @Override
    public void addJoueurToEquipe(int equipeId, Joueur joueur) {
        Equipe equipe = getEquipeById(equipeId);
        if (equipe != null) {
            equipe.ajouterJoueur(joueur);
        }
    }

    @Override
    public List<Equipe> getEquipes() {
        return new ArrayList<>(equipes);
    }

    // Nouvelle méthode pour récupérer les joueurs d'une équipe
    public List<Joueur> getJoueursByEquipeId(int equipeId) {
        Equipe equipe = getEquipeById(equipeId);
        if (equipe != null) {
            return equipe.getJoueurs();
        }
        return new ArrayList<>(); // Retourne une liste vide si l'équipe n'existe pas
    }


}