package sistempendaftarandanpelayananpasien.service;

import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.Pasien;
import sistempendaftarandanpelayananpasien.repository.PasienRepository;

/**
 * Layanan logika bisnis untuk pengelolaan data profil pasien.
 */
public class PasienService {

    private static final PasienService INSTANCE = new PasienService();

    public static PasienService getInstance() {
        return INSTANCE;
    }

    private final PasienRepository pasienRepo = new PasienRepository();

    public List<Pasien> getAllPasien() {
        return pasienRepo.bacaSemua();
    }

    public Optional<Pasien> getProfilByUsername(String username) {
        return pasienRepo.cariByUsername(username);
    }

    public Optional<Pasien> getProfilById(String idPasien) {
        return pasienRepo.cariById(idPasien);
    }

    public Pasien getPasienById(String idPasienOrUsername) {
        Optional<Pasien> opt = pasienRepo.cariById(idPasienOrUsername);
        if (opt.isPresent()) {
            return opt.get();
        }
        return pasienRepo.cariByUsername(idPasienOrUsername).orElse(null);
    }

    public boolean perbaruiProfil(Pasien pasien) {
        return pasienRepo.update(pasien);
    }

    public boolean simpanPasien(Pasien pasien) {
        if (pasienRepo.cariById(pasien.getIdPasien()).isPresent()) {
            return pasienRepo.update(pasien);
        } else {
            pasienRepo.tambah(pasien);
            return true;
        }
    }
}
