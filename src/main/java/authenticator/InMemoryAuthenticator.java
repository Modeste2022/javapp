package authenticator;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAuthenticator extends Authenticator {

    private final Map<String, String> users;

    public InMemoryAuthenticator() {
        users = new HashMap<>();
        loadDefaultUsers();
    }

    private void loadDefaultUsers() {
        // Tu peux mettre ce que tu veux ici
        users.put("ecole", "hepl");
        users.put("super", "mario");
        users.put("belgique", "europe");
    }

    public void addUser(String username, String password) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Le nom d'utilisateur existe déjà !");
        }
        users.put(username, password);
    }

    @Override
    protected boolean isLoginExists(String username) {
        return users.containsKey(username);
    }

    @Override
    protected String getPassword(String username) {
        return users.get(username);
    }
}

