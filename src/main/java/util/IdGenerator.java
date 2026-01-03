package util;

public class IdGenerator {
    private int currentId = 1;

    public synchronized int generateId() {
        return currentId++;
    }
}
