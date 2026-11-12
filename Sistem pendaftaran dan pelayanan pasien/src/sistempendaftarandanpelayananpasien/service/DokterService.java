package sistempendaftarandanpelayananpasien.service;

import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.Dokter;
import sistempendaftarandanpelayananpasien.repository.DokterRepository;

/**
 * Layanan logika bisnis untuk profil dokter dan direktori dokter.
 */
public class DokterService {

    private static final DokterService INSTANCE = new DokterService();

    public static DokterService getInstance() {
        return INSTANCE;
    }

    private final DokterRepository dokterRepo = new DokterRepository();

    public List<Dokter> getAllDokter() {
        return dokterRepo.bacaSemua();
    }

    public Optional<Dokter> getDokterByUsername(String username) {
        return dokterRepo.cariByUsername(username);
    }

    public Optional<Dokter> getDokterById(String idDokter) {
        return dokterRepo.cariById(idDokter);
    }

    public Optional<Dokter> getDokterByNama(String nama) {
        return dokterRepo.cariByNama(nama);
    }

    public boolean perbaruiProfil(Dokter dokter) {
        return dokterRepo.update(dokter);
    }
}
