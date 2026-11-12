package sistempendaftarandanpelayananpasien.model;

/**
 * Entitas data pelayanan, diagnosa, dan hasil pemeriksaan medis.
 */
public class Pelayanan {

    private String idPelayanan;
    private String idPendaftaran;
    private String idPasien;
    private String namaPasien;
    private String idDokter;
    private String namaDokter;
    private String tanggalPelayanan;
    private String diagnosis;
    private String keluhan;
    private String tindakanHasil;
    private String resepObat;
    private String catatanDokter;

    public Pelayanan() {
    }

    public Pelayanan(String idPelayanan, String idPendaftaran, String idPasien, String namaPasien, String idDokter, String namaDokter, String tanggalPelayanan, String diagnosis, String keluhan, String tindakanHasil, String resepObat, String catatanDokter) {
        this.idPelayanan = idPelayanan;
        this.idPendaftaran = idPendaftaran;
        this.idPasien = idPasien;
        this.namaPasien = namaPasien;
        this.idDokter = idDokter;
        this.namaDokter = namaDokter;
        this.tanggalPelayanan = tanggalPelayanan;
        this.diagnosis = diagnosis;
        this.keluhan = keluhan;
        this.tindakanHasil = tindakanHasil;
        this.resepObat = resepObat;
        this.catatanDokter = catatanDokter;
    }

    public String getIdPelayanan() {
        return idPelayanan;
    }

    public void setIdPelayanan(String idPelayanan) {
        this.idPelayanan = idPelayanan;
    }

    public String getIdPendaftaran() {
        return idPendaftaran;
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

    public String getTanggalPelayanan() {
        return tanggalPelayanan;
    }

    public void setTanggalPelayanan(String tanggalPelayanan) {
        this.tanggalPelayanan = tanggalPelayanan;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getDiagnosa() {
        return diagnosis;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosis = diagnosa;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getTindakanHasil() {
        return tindakanHasil;
    }

    public void setTindakanHasil(String tindakanHasil) {
        this.tindakanHasil = tindakanHasil;
    }

    public String getResepObat() {
        return resepObat;
    }

    public void setResepObat(String resepObat) {
        this.resepObat = resepObat;
    }

    public String getCatatanDokter() {
        return catatanDokter;
    }

    public void setCatatanDokter(String catatanDokter) {
        this.catatanDokter = catatanDokter;
    }
}
