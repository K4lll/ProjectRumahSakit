package sistempendaftarandanpelayananpasien.service;

import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.Pendaftaran;
import sistempendaftarandanpelayananpasien.model.StatusPendaftaran;
import sistempendaftarandanpelayananpasien.repository.PendaftaranRepository;
import sistempendaftarandanpelayananpasien.utils.FormatTanggal;
import sistempendaftarandanpelayananpasien.utils.IDGenerator;

/**
 * Layanan bisnis pendaftaran pasien ke layanan dokter spesialis / umum.
 */
public class PendaftaranService {

    private static final PendaftaranService INSTANCE = new PendaftaranService();

    public static PendaftaranService getInstance() {
        return INSTANCE;
    }

    private final PendaftaranRepository pendaftaranRepo = new PendaftaranRepository();
    private final PasienService pasienService = PasienService.getInstance();
    private final DokterService dokterService = DokterService.getInstance();

    public List<Pendaftaran> getAllPendaftaran() {
        return pendaftaranRepo.bacaSemua();
    }

    public Optional<Pendaftaran> getPendaftaranById(String idPendaftaran) {
        return pendaftaranRepo.cariById(idPendaftaran);
    }

    public List<Pendaftaran> getPendaftaranByPasien(String idPasienOrUsername) {
        return pendaftaranRepo.cariByPasien(idPasienOrUsername);
    }

    public List<Pendaftaran> getPendaftaranByDokter(String idDokterOrNama) {
        return pendaftaranRepo.cariByDokter(idDokterOrNama);
    }

    public Pendaftaran daftarBaru(String idPasien, String idDokter, String tanggalKunjungan, String keluhan, boolean isBpjs) {
        var optDok = dokterService.getDokterById(idDokter);
        String namaDok = optDok.isPresent() ? optDok.get().getNamaLengkap() : idDokter;
        var optPas = pasienService.getProfilById(idPasien);
        if (optPas.isEmpty()) {
            optPas = pasienService.getProfilByUsername(idPasien);
        }
        String namaPas = optPas.isPresent() ? optPas.get().getNamaLengkap() : idPasien;
        String keluhanFinal = keluhan + (isBpjs ? " [BPJS]" : " [Umum]");
        String idReg = daftarBaru(idPasien, namaPas, idDokter, namaDok, "JAD001", "Pagi", tanggalKunjungan, keluhanFinal);
        return pendaftaranRepo.cariById(idReg).orElse(null);
    }

    public String daftarBaru(String idPasien, String namaPasien, String idDokter, String namaDokter, String idJadwal, String jadwal, String tanggalKunjungan, String keluhan) {
        String idReg = IDGenerator.generateId("REG", pendaftaranRepo.bacaSemua().size());
        Pendaftaran p = new Pendaftaran(
                idReg,
                idPasien,
                namaPasien,
                idDokter,
                namaDokter,
                idJadwal,
                jadwal,
                tanggalKunjungan,
                keluhan,
                StatusPendaftaran.MENUNGGU,
                "",
                FormatTanggal.hariIni()
        );
        pendaftaranRepo.tambah(p);
        return idReg;
    }

    public boolean ubahPendaftaran(String idPendaftaran, String jadwalBaru, String keluhanBaru) {
        Optional<Pendaftaran> opt = pendaftaranRepo.cariById(idPendaftaran);
        if (opt.isEmpty()) {
            return false;
        }
        Pendaftaran p = opt.get();
        if (!StatusPendaftaran.canCancelOrEdit(p.getStatus())) {
            return false; // Hanya status Menunggu yang boleh diubah
        }
        p.setJadwal(jadwalBaru);
        p.setKeluhan(keluhanBaru);
        return pendaftaranRepo.update(p);
    }

    public boolean batalkanPendaftaran(String idPendaftaran) {
        Optional<Pendaftaran> opt = pendaftaranRepo.cariById(idPendaftaran);
        if (opt.isEmpty()) {
            return false;
        }
        Pendaftaran p = opt.get();
        if (!StatusPendaftaran.canCancelOrEdit(p.getStatus())) {
            return false; // Hanya status Menunggu yang boleh dibatalkan pasien
        }
        p.setStatus(StatusPendaftaran.DIBATALKAN);
        return pendaftaranRepo.update(p);
    }

    public boolean terimaPendaftaran(String idPendaftaran) {
        Optional<Pendaftaran> opt = pendaftaranRepo.cariById(idPendaftaran);
        if (opt.isEmpty()) {
            return false;
        }
        Pendaftaran p = opt.get();
        p.setStatus(StatusPendaftaran.DITERIMA);
        return pendaftaranRepo.update(p);
    }

    public boolean tolakPendaftaran(String idPendaftaran, String alasan) {
        Optional<Pendaftaran> opt = pendaftaranRepo.cariById(idPendaftaran);
        if (opt.isEmpty()) {
            return false;
        }
        Pendaftaran p = opt.get();
        p.setStatus(StatusPendaftaran.DITOLAK);
        p.setAlasanPenolakan(alasan);
        return pendaftaranRepo.update(p);
    }

    public boolean ubahStatus(String idPendaftaran, String status) {
        Optional<Pendaftaran> opt = pendaftaranRepo.cariById(idPendaftaran);
        if (opt.isEmpty()) {
            return false;
        }
        Pendaftaran p = opt.get();
        p.setStatus(status);
        return pendaftaranRepo.update(p);
    }
}
