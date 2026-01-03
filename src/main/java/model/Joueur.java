// Classe Joueur qui hérite de Personne
package model;

import model.dao.IStatistique;

import java.io.Serializable;
import java.util.Objects;

public class Joueur extends Personne implements Serializable {
    private int id;
    private int numero;
    private String poste;
    private int anneeRejoint;
    private int equipeId;
    private Statistique statistique;

    public Joueur() {
        super(); // appelle le constructeur vide de Personne
        this.statistique = new Statistique(); // si besoin
    }

    public Joueur(String nom, String prenom, String dateNaissance, int taille, int poids, int numero, String poste, int anneeRejoint) {
        super(nom, prenom, dateNaissance, taille, poids);
        this.id = 0;
        this.numero = numero;
        this.poste = poste;
        this.anneeRejoint = anneeRejoint;
        this.equipeId = 0;
        this.statistique = new Statistique(); // Initialisation de la statistique
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public int getAnneeRejoint() {
        return anneeRejoint;
    }

    public void setAnneeRejoint(int anneeRejoint) {
        this.anneeRejoint = anneeRejoint;
    }

    public int getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(int equipeId) {
        this.equipeId = equipeId;
    }

    @Override

    public String toString() {
        return getNom() + " (N°" + numero + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Joueur joueur)) return false;
        if (!super.equals(o)) return false;
        return numero == joueur.numero && anneeRejoint == joueur.anneeRejoint &&
                Objects.equals(poste, joueur.poste);
    }

    public IStatistique getStatistique() {
        return statistique;
    }

    public void setStatistique(Statistique statistique) {
        this.statistique = statistique;
    }


}