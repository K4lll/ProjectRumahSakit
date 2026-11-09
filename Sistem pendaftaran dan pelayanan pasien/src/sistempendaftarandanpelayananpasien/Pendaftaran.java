/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistempendaftarandanpelayananpasien;

/**
 *
 * @author LENOVO
 */
public class Pendaftaran {

    private String namaPasien;
    private String namaDokter;
    private String keluhan;
    private String tanggal;
    private String waktu;
    private String status = "Menunggu"; // jadi "Terkonfirmasi" setelah dokter menekan Konfirmasi

    public Pendaftaran() {
    }

    public Pendaftaran(String namaPasien, String namaDokter, String keluhan, String tanggal, String waktu) {
        this.namaPasien = namaPasien;
        this.namaDokter = namaDokter;
        this.keluhan = keluhan;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
