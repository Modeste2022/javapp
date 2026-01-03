package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipe implements Serializable {
    private final int id;
    private String nom;
    private String pays;
    private String ville;
    private final List<Joueur> joueurs; //collection d’objets Joueur

    public Equipe(int id, String nom, String pays, String ville) {
        this.id = id;
        this.nom = nom;
        this.pays = pays;
        this.ville = ville;
        this.joueurs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void ajouterJoueur(Joueur joueur) {
        joueurs.add(joueur);
    }

    public void supprimerJoueur(Joueur joueur) {
        joueurs.remove(joueur);
    }

    public int generateJoueurId() {
        if (joueurs.isEmpty()) {
            return 1; // Si aucun joueur, commencer à 1.
        } else {
            int maxId = 0;
            for (Joueur joueur : joueurs) {
                if (joueur.getId() > maxId) {
                    maxId = joueur.getId(); // Trouver le plus grand ID
                }
            }
            return maxId + 1; // Retourner l'ID suivant
        }
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }  //collection d’objets Joueur

    public boolean existeDeja(List<Equipe> equipes) {
        for (Equipe equipe : equipes) {
            if (equipe.getNom().equals(this.nom) &&
                    equipe.getPays().equals(this.pays) &&
                    equipe.getVille().equals(this.ville)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return nom + " (" + pays + ", " + ville + ")" + "liste des joueurs : " + joueurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipe equipe = (Equipe) o;
        return nom.equals(equipe.nom) && pays.equals(equipe.pays) && ville.equals(equipe.ville);
    }
}