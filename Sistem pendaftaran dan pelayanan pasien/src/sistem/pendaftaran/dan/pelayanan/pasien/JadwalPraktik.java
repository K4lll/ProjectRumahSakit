/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistem.pendaftaran.dan.pelayanan.pasien;

/**
 *
 * @author LENOVO
 */
public class JadwalPraktik {

    private String namaDokter;
    private String hari;       // contoh: "Senin"
    private String jamMulai;   // contoh: "08:00"
    private String jamSelesai; // contoh: "12:00"

    public JadwalPraktik() {
    }

    public JadwalPraktik(String namaDokter, String hari, String jamMulai, String jamSelesai) {
        this.namaDokter = namaDokter;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai) {
        this.jamSelesai = jamSelesai;
    }
}
