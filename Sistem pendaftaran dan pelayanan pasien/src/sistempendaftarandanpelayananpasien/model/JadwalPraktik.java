package sistempendaftarandanpelayananpasien.model;

/**
 * Entitas jadwal praktik dokter di rumah sakit.
 */
public class JadwalPraktik {

    private String idJadwal;
    private String idDokter;
    private String namaDokter;
    private String hari;
    private String jamMulai;
    private String jamSelesai;
    private int kuota;

    public JadwalPraktik() {
    }

    public JadwalPraktik(String idJadwal, String idDokter, String namaDokter, String hari, String jamMulai, String jamSelesai, int kuota) {
        this.idJadwal = idJadwal;
        this.idDokter = idDokter;
        this.namaDokter = namaDokter;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.kuota = kuota;
    }

    public String getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(String idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getIdDokter() {
        return idDokter;
    }

    public void setIdDokter(String idDokter) {
        this.idDokter = idDokter;
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

    public int getKuota() {
        return kuota;
    }

    public int getKuotaPasien() {
        return kuota;
    }

    public void setKuota(int kuota) {
        this.kuota = kuota;
    }

    public String getJamPraktik() {
        return jamMulai + "-" + jamSelesai;
    }

    public String getDeskripsiJadwal() {
        return hari + " " + jamMulai + "-" + jamSelesai;
    }
}
