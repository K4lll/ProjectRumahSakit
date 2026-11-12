package sistempendaftarandanpelayananpasien.model;

/**
 * Kelas abstrak dasar yang merepresentasikan entitas orang di lingkungan rumah sakit.
 * Menerapkan pilar Abstraksi (Abstraction) dan menjadi kelas induk (Inheritance)
 * bagi kelas Pasien dan Dokter.
 */
public abstract class Orang {

    protected String namaLengkap;
    protected String noTelepon;

    public Orang() {
    }

    public Orang(String namaLengkap, String noTelepon) {
        this.namaLengkap = namaLengkap;
        this.noTelepon = noTelepon;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNama() {
        return namaLengkap;
    }

    public void setNama(String nama) {
        this.namaLengkap = nama;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    /**
     * Method abstrak yang wajib diimplementasikan secara spesifik
     * oleh setiap kelas turunan (Polimorfisme).
     */
    public abstract String getPeran();

    /**
     * Method abstrak untuk menampilkan ringkasan informasi identitas profil.
     */
    public abstract String getInfoSingkat();
}
