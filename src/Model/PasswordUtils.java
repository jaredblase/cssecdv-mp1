package Model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordUtils {
    public static String hashPassword(char[] password) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return hashPassword(password, salt);
    }

    public static String hashPassword(char[] password, String salt) throws Exception {
        return hashPassword(password, Base64.getDecoder().decode(salt));
    }

    public static String hashPassword(char[] password, byte[] salt) throws Exception {
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

    public static void validatePassword(char[] password) throws Exception {
        if (password.length < 8) {
            throw new Exception("Password should be 8 characters long.");
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        if (!hasUpperCase) {
            throw new Exception("Password must contain at least one uppercase letter");
        }
        if (!hasLowerCase) {
            throw new Exception("Password must contain at least one lowercase letter");
        }
        if (!hasDigit) {
            throw new Exception("Password must contain at least one digit");
        }
        if (!hasSpecial) {
            throw new Exception("Password must contain at least one special character");
        }
    }

    public static boolean matchHashToPassword(String hash, char[] password) {
        try {
            return hash.equals(hashPassword(password, hash.substring(24)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
