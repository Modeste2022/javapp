package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatistiqueTest {

    private Statistique statistique;

    @BeforeEach
    void setUp() {
        statistique = new Statistique();
    }

    @Test
    void testInitialisation() {
        assertEquals(0, statistique.get_1PT());
        assertEquals(0, statistique.get_2PTS());
        assertEquals(0, statistique.get_3PTS());
        assertEquals(0, statistique.getFautes());
        assertEquals(0, statistique.getRebonds());
        assertEquals(0, statistique.getAssist());
        assertEquals(0, statistique.getContre());
    }

    @Test
    void testIncrementer() {
        statistique.incrementer("1PT");
        statistique.incrementer("2PTS");
        statistique.incrementer("3PTS");
        statistique.incrementer("fautes");
        statistique.incrementer("rebonds");
        statistique.incrementer("assists");
        statistique.incrementer("contres");

        assertEquals(1, statistique.get_1PT());
        assertEquals(1, statistique.get_2PTS());
        assertEquals(1, statistique.get_3PTS());
        assertEquals(1, statistique.getFautes());
        assertEquals(1, statistique.getRebonds());
        assertEquals(1, statistique.getAssist());
        assertEquals(1, statistique.getContre());
    }

    @Test
    void testDecrementer() {
        statistique.incrementer("1PT");
        statistique.decrementer("1PT");
        assertEquals(0, statistique.get_1PT());
    }

    @Test
    void testReinitialiser() {
        statistique.incrementer("1PT");
        statistique.incrementer("2PTS");
        statistique.reinitialiser();
        assertEquals(0, statistique.get_1PT());
        assertEquals(0, statistique.get_2PTS());
    }

    @Test
    void testGetTotalPoints() {
        statistique.incrementer("1PT");
        statistique.incrementer("2PTS");
        statistique.incrementer("3PTS");
        assertEquals(6, statistique.getTotalPoints());
    }
}