package sistempendaftarandanpelayananpasien.model;

/**
 * Entitas profil dokter rumah sakit.
 */
public class Dokter {

    private String idDokter;
    private String username;
    private String namaLengkap;
    private String spesialisasi;
    private String noTelepon;
    private String email;

    public Dokter() {
    }

    public Dokter(String idDokter, String username, String namaLengkap, String spesialisasi, String noTelepon, String email) {
        this.idDokter = idDokter;
        this.username = username;
        this.namaLengkap = namaLengkap;
        this.spesialisasi = spesialisasi;
        this.noTelepon = noTelepon;
        this.email = email;
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

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getNama() {
        return namaLengkap;
    }

    public void setNama(String nama) {
        this.namaLengkap = nama;
    }

    public String getPoli() {
        return "Poli " + (spesialisasi != null && !spesialisasi.trim().isEmpty() ? spesialisasi : "Umum");
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
