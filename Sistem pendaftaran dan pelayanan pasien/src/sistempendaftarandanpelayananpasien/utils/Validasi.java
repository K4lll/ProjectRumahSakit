package sistempendaftarandanpelayananpasien.utils;

/**
 * Utilitas validasi input formulir pengguna, NIK, dan data pendaftaran.
 */
public class Validasi {

    private Validasi() {
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isValidNik(String nik) {
        return nik != null && nik.trim().matches("\\d{16}");
    }

    public static boolean isValidTelepon(String telp) {
        return telp != null && telp.trim().matches("\\d{9,15}");
    }

    public static boolean isValidUsername(String username) {
        return username != null && username.trim().matches("^[a-zA-Z0-9_]{3,20}$");
    }
}
