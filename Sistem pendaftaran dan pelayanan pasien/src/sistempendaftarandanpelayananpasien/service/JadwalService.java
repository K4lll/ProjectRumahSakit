package sistempendaftarandanpelayananpasien.service;

import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.JadwalPraktik;
import sistempendaftarandanpelayananpasien.repository.JadwalRepository;
import sistempendaftarandanpelayananpasien.utils.IDGenerator;

/**
 * Layanan logika bisnis untuk manajemen jadwal praktik dokter.
 */
public class JadwalService {

    private static final JadwalService INSTANCE = new JadwalService();

    public static JadwalService getInstance() {
        return INSTANCE;
    }

    private final JadwalRepository jadwalRepo = new JadwalRepository();

    public List<JadwalPraktik> getAllJadwal() {
        return jadwalRepo.bacaSemua();
    }

    public List<JadwalPraktik> getJadwalByDokter(String idDokterOrNama) {
        return jadwalRepo.cariByDokter(idDokterOrNama);
    }

    public Optional<JadwalPraktik> getJadwalById(String idJadwal) {
        return jadwalRepo.cariById(idJadwal);
    }

    public boolean tambahJadwal(String idDokter, String namaDokter, String hari, String jamMulai, String jamSelesai, int kuota) {
        String idJadwal = IDGenerator.generateId("JAD", jadwalRepo.bacaSemua().size());
        JadwalPraktik j = new JadwalPraktik(idJadwal, idDokter, namaDokter, hari, jamMulai, jamSelesai, kuota);
        jadwalRepo.tambah(j);
        return true;
    }

    public boolean updateJadwal(JadwalPraktik jadwal) {
        return jadwalRepo.update(jadwal);
    }

    public boolean hapusJadwal(String idJadwal) {
        return jadwalRepo.hapus(idJadwal);
    }
}
