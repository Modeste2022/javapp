package model;
import java.io.Serializable;
import java.util.Objects;

public abstract class Personne implements Serializable {
    private String nom;
    private String prenom;
    private String dateNaissance;
    private int taille;
    private int poids;

    public Personne() {
        this.nom = "";
        this.prenom = "";
        this.dateNaissance = "";
        this.taille = 0;
        this.poids = 0;
    }

    public Personne(String nom, String prenom, String dateNaissance, int taille, int poids) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.taille = taille;
        this.poids = poids;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " (" + dateNaissance + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return taille == personne.taille && poids == personne.poids &&
                Objects.equals(nom, personne.nom) && Objects.equals(prenom, personne.prenom) &&
                Objects.equals(dateNaissance, personne.dateNaissance);
    }

}