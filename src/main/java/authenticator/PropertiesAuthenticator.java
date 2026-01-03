package authenticator;

import java.io.*;
import java.util.Properties;

public class PropertiesAuthenticator extends Authenticator {
    private static final String PROPERTIES_FILE = "users.properties";
    private final Properties usersProperties;

    public PropertiesAuthenticator() {
        usersProperties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        // Charger le fichier properties
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            usersProperties.load(fis);
        } catch (FileNotFoundException e) {
            // Si le fichier n'existe pas encore, on le crée
            try (FileOutputStream fos = new FileOutputStream(PROPERTIES_FILE)) {
                usersProperties.store(fos, "Fichier utilisateurs (logins & mots de passe)");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProperties() {
        // Sauvegarder les données dans le fichier properties
        try (FileOutputStream fos = new FileOutputStream(PROPERTIES_FILE)) {
            usersProperties.store(fos, "Fichier utilisateurs (logins & mots de passe)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String username, String password) {
        if (usersProperties.containsKey(username)) {
            throw new IllegalArgumentException("Le nom d'utilisateur existe déjà !");
        }
        usersProperties.setProperty(username, password);
        saveProperties();
    }


    @Override
    protected boolean isLoginExists(String username) {
        return usersProperties.containsKey(username);
    }

    @Override
    protected String getPassword(String username) {
        return usersProperties.getProperty(username);
    }
}
