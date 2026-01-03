package authenticator;

public abstract class Authenticator {
    public boolean authenticate(String username, String password) {
        if (isLoginExists(username)) {
            String storedPassword = getPassword(username);
            return storedPassword != null &&
                    storedPassword.equals(password);
        }
        return false;
    }

    protected abstract boolean isLoginExists(String username);

    protected abstract String getPassword(String username);

    public void addUser(String trim, String newPassword) {
    }
}
