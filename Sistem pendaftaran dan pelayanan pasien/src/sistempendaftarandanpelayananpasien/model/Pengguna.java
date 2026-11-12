package sistempendaftarandanpelayananpasien.model;

/**
 * Entitas data pengguna untuk kebutuhan autentikasi dan hak akses.
 */
public class Pengguna {

    private String username;
    private String passwordHash;
    private String salt;
    private String peran; // "pasien" atau "dokter"

    public Pengguna() {
    }

    public Pengguna(String username, String passwordHash, String salt, String peran) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.peran = peran;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPeran() {
        return peran;
    }

    public void setPeran(String peran) {
        this.peran = peran;
    }
}
