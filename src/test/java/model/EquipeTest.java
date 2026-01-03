package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EquipeTest {

    private Equipe equipe;
    private Joueur joueur1;
    private Joueur joueur2;

    @BeforeEach
    void setUp() {
        equipe = new Equipe(1, "Lions", "France", "Lyon");

        joueur1 = new Joueur("Dupont", "Jean", "1990-01-01", 180, 75, 10, "Attaquant", 2015);
        joueur2 = new Joueur("Durand", "Paul", "1992-03-15", 190, 85, 12, "Défenseur", 2017);
    }

    @Test
    void testAjouterJoueur() {
        equipe.ajouterJoueur(joueur1);
        assertEquals(1, equipe.getJoueurs().size());
        assertTrue(equipe.getJoueurs().contains(joueur1));
    }

    @Test
    void testSupprimerJoueur() {
        equipe.ajouterJoueur(joueur1);
        equipe.ajouterJoueur(joueur2);
        equipe.supprimerJoueur(joueur1);
        assertEquals(1, equipe.getJoueurs().size());
        assertFalse(equipe.getJoueurs().contains(joueur1));
    }

    @Test
    void testGetJoueurs() {
        equipe.ajouterJoueur(joueur1);
        equipe.ajouterJoueur(joueur2);
        List<Joueur> joueurs = equipe.getJoueurs();
        assertEquals(2, joueurs.size());
        assertTrue(joueurs.contains(joueur1));
        assertTrue(joueurs.contains(joueur2));
    }

    @Test
    void testExisteDeja() {
        List<Equipe> equipes = new ArrayList<>();
        equipes.add(new Equipe(1, "Lions", "France", "Lyon"));
        equipes.add(new Equipe(2, "Tigers", "USA", "Boston"));

        assertTrue(equipe.existeDeja(equipes));

        Equipe nouvelleEquipe = new Equipe(3, "Panthers", "Canada", "Toronto");

        assertFalse(nouvelleEquipe.existeDeja(equipes));
    }

    @Test
    void testToString() {
        assertEquals("Lions (France, Lyon)liste des joueurs : []", equipe.toString());
    }

    @Test
    void testEquals() {
        Equipe equipeClone = new Equipe(1, "Lions", "France", "Lyon");

        assertEquals(equipe, equipeClone);

        Equipe autreEquipe = new Equipe(2, "Tigre", "France", "Lyon");

        assertNotEquals(equipe, autreEquipe);
    }
}