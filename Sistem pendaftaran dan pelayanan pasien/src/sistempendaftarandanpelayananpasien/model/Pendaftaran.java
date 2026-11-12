package sistempendaftarandanpelayananpasien.model;

/**
 * Entitas pendaftaran pelayanan medis pasien ke dokter.
 */
public class Pendaftaran {

    private String idPendaftaran;
    private String idPasien;
    private String namaPasien;
    private String idDokter;
    private String namaDokter;
    private String idJadwal;
    private String jadwal;
    private String tanggalKunjungan;
    private String keluhan;
    private String status;
    private String alasanPenolakan;
    private String tanggalDaftar;

    public Pendaftaran() {
    }

    public Pendaftaran(String idPendaftaran, String idPasien, String namaPasien, String idDokter, String namaDokter, String idJadwal, String jadwal, String tanggalKunjungan, String keluhan, String status, String alasanPenolakan, String tanggalDaftar) {
        this.idPendaftaran = idPendaftaran;
        this.idPasien = idPasien;
        this.namaPasien = namaPasien;
        this.idDokter = idDokter;
        this.namaDokter = namaDokter;
        this.idJadwal = idJadwal;
        this.jadwal = jadwal;
        this.tanggalKunjungan = tanggalKunjungan;
        this.keluhan = keluhan;
        this.status = status;
        this.alasanPenolakan = alasanPenolakan;
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getIdPendaftaran() {
        return idPendaftaran;
    }

    public String getNomorAntrean() {
        if (idPendaftaran != null && idPendaftaran.length() >= 3) {
            String digits = idPendaftaran.replaceAll("\\D+", "");
            if (!digits.isEmpty()) {
                return "A-" + digits;
            }
        }
        return idPendaftaran;
    }

    public String getTanggalPendaftaran() {
        return tanggalDaftar != null && !tanggalDaftar.trim().isEmpty() ? tanggalDaftar : tanggalKunjungan;
    }

    public void setIdPendaftaran(String idPendaftaran) {
        this.idPendaftaran = idPendaftaran;
    }

    public String getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(String idPasien) {
        this.idPasien = idPasien;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
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

    public String getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(String idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getTanggalKunjungan() {
        return tanggalKunjungan;
    }

    public void setTanggalKunjungan(String tanggalKunjungan) {
        this.tanggalKunjungan = tanggalKunjungan;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlasanPenolakan() {
        return alasanPenolakan;
    }

    public void setAlasanPenolakan(String alasanPenolakan) {
        this.alasanPenolakan = alasanPenolakan;
    }

    public String getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(String tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }
}
