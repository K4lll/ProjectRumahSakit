package sistempendaftarandanpelayananpasien.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilitas untuk keamanan autentikasi: pembuatan salt acak,
 * hashing password dengan salt menggunakan algoritma SHA-256,
 * dan verifikasi password.
 */
public class PasswordUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {
    }

    /**
     * Menghasilkan 16-byte cryptographic salt dalam format Base64.
     */
    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Melakukan hash password dengan salt menggunakan SHA-256.
     * Hasil dikembalikan dalam representasi Base64.
     */
    public static String hashPassword(String password, String salt) {
        if (password == null || salt == null) {
            throw new IllegalArgumentException("Password dan salt tidak boleh null.");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            md.update(saltBytes);
            byte[] hashed = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritma SHA-256 tidak didukung.", e);
        }
    }

    /**
     * Memverifikasi kecocokan password input terhadap salt dan hash tersimpan.
     */
    public static boolean verifikasiPassword(String password, String salt, String hashTersimpan) {
        if (password == null || salt == null || hashTersimpan == null) {
            return false;
        }
        String calculated = hashPassword(password, salt);
        return MessageDigest.isEqual(
                calculated.getBytes(StandardCharsets.UTF_8),
                hashTersimpan.getBytes(StandardCharsets.UTF_8)
        );
    }
}
