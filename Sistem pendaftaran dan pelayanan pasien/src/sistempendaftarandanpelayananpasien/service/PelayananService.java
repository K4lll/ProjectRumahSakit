package sistempendaftarandanpelayananpasien.service;

import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.Pelayanan;
import sistempendaftarandanpelayananpasien.model.StatusPendaftaran;
import sistempendaftarandanpelayananpasien.repository.PelayananRepository;
import sistempendaftarandanpelayananpasien.repository.PendaftaranRepository;
import sistempendaftarandanpelayananpasien.utils.FormatTanggal;
import sistempendaftarandanpelayananpasien.utils.IDGenerator;

/**
 * Layanan bisnis untuk pencatatan dan penelusuran hasil pemeriksaan medis pasien.
 */
public class PelayananService {

    private static final PelayananService INSTANCE = new PelayananService();

    public static PelayananService getInstance() {
        return INSTANCE;
    }

    private final PelayananRepository pelayananRepo = new PelayananRepository();
    private final PendaftaranRepository pendaftaranRepo = new PendaftaranRepository();

    public List<Pelayanan> getAllPelayanan() {
        return pelayananRepo.bacaSemua();
    }

    public List<Pelayanan> getPelayananByPasien(String idPasienOrUsername) {
        return pelayananRepo.cariByPasien(idPasienOrUsername);
    }

    public List<Pelayanan> getPelayananByDokter(String idDokterOrNama) {
        return pelayananRepo.cariByDokter(idDokterOrNama);
    }

    public Optional<Pelayanan> getPelayananById(String idPelayanan) {
        return pelayananRepo.cariById(idPelayanan);
    }

    public Optional<Pelayanan> getPelayananByPendaftaran(String idPendaftaran) {
        return pelayananRepo.cariByPendaftaran(idPendaftaran);
    }

    public String simpanHasilPemeriksaan(String idPendaftaran, String idPasien, String namaPasien,
            String idDokter, String namaDokter, String diagnosis, String keluhan,
            String tindakanHasil, String resepObat, String catatanDokter) {

        String idPelayanan = IDGenerator.generateId("PEL", pelayananRepo.bacaSemua().size());
        Pelayanan pel = new Pelayanan(
                idPelayanan,
                idPendaftaran,
                idPasien,
                namaPasien,
                idDokter,
                namaDokter,
                FormatTanggal.hariIni(),
                diagnosis,
                keluhan,
                tindakanHasil,
                resepObat,
                catatanDokter
        );
        pelayananRepo.tambah(pel);

        // Update status pendaftaran menjadi Selesai jika idPendaftaran diberikan
        if (idPendaftaran != null && !idPendaftaran.isEmpty()) {
            pendaftaranRepo.cariById(idPendaftaran).ifPresent(p -> {
                p.setStatus(StatusPendaftaran.SELESAI);
                pendaftaranRepo.update(p);
            });
        }

        return idPelayanan;
    }
}
