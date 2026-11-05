/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistem.pendaftaran.dan.pelayanan.pasien;

/**
 *
 * @author LENOVO
 */
public class Pasien {

    private String nama;
    private String alamat;
    private String tempatTanggalLahir; // sama seperti isian di FormPendaftaran (satu kolom)
    private int umur;
    private String jenisKelamin;       // "Pria" atau "Wanita"

    public Pasien() {
    }

    public Pasien(String nama, String alamat, String tempatTanggalLahir, int umur, String jenisKelamin) {
        this.nama = nama;
        this.alamat = alamat;
        this.tempatTanggalLahir = tempatTanggalLahir;
        this.umur = umur;
        this.jenisKelamin = jenisKelamin;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTempatTanggalLahir() {
        return tempatTanggalLahir;
    }

    public void setTempatTanggalLahir(String tempatTanggalLahir) {
        this.tempatTanggalLahir = tempatTanggalLahir;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}
