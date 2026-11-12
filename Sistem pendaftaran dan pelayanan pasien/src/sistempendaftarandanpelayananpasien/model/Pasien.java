package sistempendaftarandanpelayananpasien.model;

/**
 * Entitas profil pasien rumah sakit.
 * Mewarisi kelas abstrak Orang (Inheritance & Abstraction).
 */
public class Pasien extends Orang {

    private String idPasien;
    private String username;
    private String nik;
    private String alamat;
    private String tanggalLahir;
    private String jenisKelamin;
    private String golonganDarah;

    public Pasien() {
        super();
    }

    public Pasien(String idPasien, String username, String namaLengkap, String nik, String noTelepon, String alamat, String tanggalLahir, String jenisKelamin, String golonganDarah) {
        super(namaLengkap, noTelepon);
        this.idPasien = idPasien;
        this.username = username;
        this.nik = nik;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.golonganDarah = golonganDarah;
    }

    @Override
    public String getPeran() {
        return "Pasien";
    }

    @Override
    public String getInfoSingkat() {
        return "Pasien: " + getNamaLengkap() + " (NIK: " + nik + ")";
    }

    public String getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(String idPasien) {
        this.idPasien = idPasien;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getGolonganDarah() {
        return golonganDarah;
    }

    public void setGolonganDarah(String golonganDarah) {
        this.golonganDarah = golonganDarah;
    }
}
