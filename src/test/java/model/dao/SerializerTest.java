package model.dao;

import model.Joueur;
import util.Serializer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SerializerTest {

    @Test
    public void testSerialisationEtDeserialisation() throws Exception {
        // Création d'une liste de joueurs fictifs
        List<Joueur> joueursOriginal = new ArrayList<>();
        Joueur joueur1 = new Joueur("Durant", "Kevin", "1988-09-29", 208, 109, 7, "Ailier", 2007);
        joueur1.getStatistique().incrementer("3PTS");
        joueursOriginal.add(joueur1);

        Joueur joueur2 = new Joueur("James", "LeBron", "1984-12-30", 206, 113, 6, "Ailier fort", 2003);
        joueur2.getStatistique().incrementer("1PT");
        joueursOriginal.add(joueur2);

        // Exportation
        File fichierTest = new File("test_joueurs.ser");
        Serializer.sauvegarder(joueursOriginal, fichierTest.getPath());

        // Importation
        List<Joueur> joueursImportes = Serializer.charger(fichierTest.getPath());

        // === Vérifications ===
        assertNotNull(joueursImportes);
        assertEquals(joueursOriginal.size(), joueursImportes.size());
        assertEquals(joueursOriginal.get(0).getNom(), joueursImportes.get(0).getNom());
        assertEquals(joueursOriginal.get(1).getStatistique().getStat("1PT"),
                joueursImportes.get(1).getStatistique().getStat("1PT"));

        // Nettoyage
        if (fichierTest.exists()) {
            assertTrue(fichierTest.delete());
        }
    }
}