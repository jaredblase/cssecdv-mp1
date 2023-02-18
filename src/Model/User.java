package Model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class User {
    private int id;
    private String username;
    private String password;
    private int role;
    private int locked;

    private static String hashPassword(char[] password) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return hashPassword(password, salt);
    }

    private static String hashPassword(char[] password, String salt) throws Exception {
        return hashPassword(password, Base64.getDecoder().decode(salt));
    }

    private static String hashPassword(char[] password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hashed = factory.generateSecret(spec).getEncoded();

            String hash = Base64.getEncoder().encodeToString(hashed);
            String saltString = Base64.getEncoder().encodeToString(salt);
            return hash + saltString;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("An error has occurred in hashing!");
        }
    }

    public User(String username, char[] password) throws Exception {
        this(username, password, 2);
    }

    public User(String username, char[] password, int role) throws Exception {
        this(username, password, role, 0);
    }

    public User(String username, char[] password, int role, int locked) throws Exception {
        this.username = username;
        this.password = hashPassword(password);
        this.role = role;
        this.locked = locked;
    }

    public User(int id, String username, String password, int role, int locked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.locked = locked;
    }

    public boolean matchPassword(char[] password) {
        try {
            return this.password.equals(hashPassword(password, this.password.substring(24)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(char[] password) throws Exception {
        this.password = hashPassword(password);
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }
}
