package sistempendaftarandanpelayananpasien.model;

/**
 * Entitas profil pasien rumah sakit.
 */
public class Pasien {

    private String idPasien;
    private String username;
    private String namaLengkap;
    private String nik;
    private String noTelepon;
    private String alamat;
    private String tanggalLahir;
    private String jenisKelamin;
    private String golonganDarah;

    public Pasien() {
    }

    public Pasien(String idPasien, String username, String namaLengkap, String nik, String noTelepon, String alamat, String tanggalLahir, String jenisKelamin, String golonganDarah) {
        this.idPasien = idPasien;
        this.username = username;
        this.namaLengkap = namaLengkap;
        this.nik = nik;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.golonganDarah = golonganDarah;
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

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getNama() {
        return namaLengkap;
    }

    public void setNama(String nama) {
        this.namaLengkap = nama;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
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
