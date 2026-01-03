package model.dao;

import model.Equipe;
import model.Joueur;

import java.util.List;

public interface IDAOEquipe {
    int addEquipe(String nom, String pays, String ville);
    boolean updateEquipe(Equipe equipe);
    boolean deleteEquipe(Equipe equipe);
    Equipe getEquipeById(int id);
    void addJoueurToEquipe(int equipeId, Joueur joueur);
    List<Equipe> getEquipes();
    List<Joueur> getJoueursByEquipeId(int equipeId);

}
