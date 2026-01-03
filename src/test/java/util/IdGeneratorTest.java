package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    @Test
    void testGenerateId() {
        IdGenerator idGenerator = new IdGenerator();
        int id1 = idGenerator.generateId();
        int id2 = idGenerator.generateId();
        assertNotEquals(id1, id2);
        assertEquals(id1 + 1, id2);
    }
}
