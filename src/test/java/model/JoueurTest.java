package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

    private Joueur joueur;

    @BeforeEach
    void setUp() {
        joueur = new Joueur("Dupont", "Jean", "1990-01-01", 180, 75, 10, "Attaquant", 2015);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("Dupont", joueur.getNom());
        assertEquals("Jean", joueur.getPrenom());
        assertEquals("1990-01-01", joueur.getDateNaissance());
        assertEquals(180, joueur.getTaille());
        assertEquals(75, joueur.getPoids());
        assertEquals(10, joueur.getNumero());
        assertEquals("Attaquant", joueur.getPoste());
        assertEquals(2015, joueur.getAnneeRejoint());

        joueur.setNom("Durand");
        joueur.setPrenom("Paul");
        joueur.setDateNaissance("1992-03-15");
        joueur.setTaille(190);
        joueur.setPoids(85);
        joueur.setNumero(12);
        joueur.setPoste("Défenseur");
        joueur.setAnneeRejoint(2017);

        assertEquals("Durand", joueur.getNom());
        assertEquals("Paul", joueur.getPrenom());
        assertEquals("1992-03-15", joueur.getDateNaissance());
        assertEquals(190, joueur.getTaille());
        assertEquals(85, joueur.getPoids());
        assertEquals(12, joueur.getNumero());
        assertEquals("Défenseur", joueur.getPoste());
        assertEquals(2017, joueur.getAnneeRejoint());
    }

    @Test
    void testToString() {
        String expected = "Dupont (N°10)";
        assertEquals(expected, joueur.toString());
    }

    @Test
    void testEquals() {
        Joueur joueurClone = new Joueur("Dupont", "Jean", "1990-01-01", 180, 75, 10, "Attaquant", 2015);

        assertEquals(joueur, joueurClone);

        Joueur autreJoueur = new Joueur("Durand", "Paul", "1992-03-15", 190, 85, 12, "Défenseur", 2017);

        assertNotEquals(joueur, autreJoueur);
    }
}