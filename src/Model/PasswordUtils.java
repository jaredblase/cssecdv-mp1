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

    public static boolean matchHashToPassword(String hash, char[] password) {
        try {
            return hash.equals(hashPassword(password, hash.substring(24)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
