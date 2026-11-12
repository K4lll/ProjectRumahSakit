package sistempendaftarandanpelayananpasien.auth;

import java.util.HashMap;
import java.util.Map;

/**
 * Pengelola sesi pengguna aktif (pasien maupun dokter) dalam aplikasi.
 */
public class SessionManager {

    private static String username;
    private static String nama;
    private static String peran; // "pasien" atau "dokter"
    private static final Map<String, Object> atribut = new HashMap<>();

    private SessionManager() {
    }

    public static synchronized void setSesi(String user, String namaLengkap, String role) {
        username = user;
        nama = namaLengkap;
        peran = role;
    }

    public static synchronized void setAtribut(String kunci, Object nilai) {
        atribut.put(kunci, nilai);
    }

    public static synchronized Object getAtribut(String kunci) {
        return atribut.get(kunci);
    }

    public static synchronized String getUsername() {
        return username;
    }

    public static synchronized String getNama() {
        return nama;
    }

    public static synchronized String getPeran() {
        return peran;
    }

    public static synchronized boolean isLogin() {
        return username != null && !username.trim().isEmpty();
    }

    public static synchronized SessionManager getSesi() {
        return isLogin() ? new SessionManager() : null;
    }

    public boolean isLoggedIn() {
        return isLogin();
    }

    public static synchronized boolean isPasien() {
        return "pasien".equalsIgnoreCase(peran);
    }

    public static synchronized boolean isDokter() {
        return "dokter".equalsIgnoreCase(peran);
    }

    public static synchronized void logout() {
        username = null;
        nama = null;
        peran = null;
        atribut.clear();
    }
}
