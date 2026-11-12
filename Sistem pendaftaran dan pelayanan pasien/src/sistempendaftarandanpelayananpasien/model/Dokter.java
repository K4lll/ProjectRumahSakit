package sistempendaftarandanpelayananpasien.model;

/**
 * Entitas profil dokter rumah sakit.
 * Mewarisi kelas abstrak Orang (Inheritance & Abstraction).
 */
public class Dokter extends Orang {

    private String idDokter;
    private String username;
    private String spesialisasi;
    private String email;

    public Dokter() {
        super();
    }

    public Dokter(String idDokter, String username, String namaLengkap, String spesialisasi, String noTelepon, String email) {
        super(namaLengkap, noTelepon);
        this.idDokter = idDokter;
        this.username = username;
        this.spesialisasi = spesialisasi;
        this.email = email;
    }

    @Override
    public String getPeran() {
        return "Dokter";
    }

    @Override
    public String getInfoSingkat() {
        return "Dokter: " + getNamaLengkap() + " (Spesialisasi: " + spesialisasi + ")";
    }

    public String getIdDokter() {
        return idDokter;
    }

    public void setIdDokter(String idDokter) {
        this.idDokter = idDokter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPoli() {
        return "Poli " + (spesialisasi != null && !spesialisasi.trim().isEmpty() ? spesialisasi : "Umum");
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getNamaLengkap() + " (" + spesialisasi + ")";
    }
}
