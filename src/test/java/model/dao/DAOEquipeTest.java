package model.dao;

import model.Equipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAOEquipeTest {

    private DAOEquipe daoEquipe;

    @BeforeEach
    void setUp() {
        daoEquipe = new DAOEquipe();
    }

    @Test
    void testAddEquipe() {
        int id = daoEquipe.addEquipe("Lions", "France", "Lyon");
        Equipe equipe = daoEquipe.getEquipeById(id);
        assertNotNull(equipe);
        assertEquals("Lions", equipe.getNom());
    }

    @Test
    void testUpdateEquipe() {
        int id = daoEquipe.addEquipe("Lions", "France", "Lyon");
        Equipe equipe = daoEquipe.getEquipeById(id);
        equipe.setNom("Tigers");
        assertTrue(daoEquipe.updateEquipe(equipe));
        assertEquals("Tigers", daoEquipe.getEquipeById(id).getNom());
    }

    @Test
    void testDeleteEquipe() {
        int id = daoEquipe.addEquipe("Lions", "France", "Lyon");
        Equipe equipe = daoEquipe.getEquipeById(id);
        assertTrue(daoEquipe.deleteEquipe(equipe));
        assertNull(daoEquipe.getEquipeById(id));
    }

    @Test
    void testGetEquipes() {
        daoEquipe.addEquipe("Lions", "France", "Lyon");
        daoEquipe.addEquipe("Tigers", "USA", "Boston");
        List<Equipe> equipes = daoEquipe.getEquipes();
        assertEquals(2, equipes.size());
    }
}