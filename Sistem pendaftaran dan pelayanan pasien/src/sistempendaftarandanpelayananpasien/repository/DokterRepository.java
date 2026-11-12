package sistempendaftarandanpelayananpasien.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.Dokter;
import sistempendaftarandanpelayananpasien.storage.CsvUtil;
import sistempendaftarandanpelayananpasien.storage.PenyimpananFile;

/**
 * Repositori untuk membaca dan menyimpan data profil dokter pada berkas dokter.csv.
 */
public class DokterRepository {

    private static final String HEADER = "id_dokter,username,nama_lengkap,spesialisasi,no_telepon,email";

    public List<Dokter> bacaSemua() {
        List<Dokter> list = new ArrayList<>();
        List<String> baris = PenyimpananFile.bacaSemuaBaris(PenyimpananFile.FILE_DOKTER);

        for (int i = 0; i < baris.size(); i++) {
            if (i == 0 && baris.get(i).toLowerCase().contains("id_dokter")) {
                continue;
            }
            List<String> c = CsvUtil.parseLine(baris.get(i));
            if (c.size() >= 6) {
                list.add(new Dokter(c.get(0), c.get(1), c.get(2), c.get(3), c.get(4), c.get(5)));
            }
        }
        return list;
    }

    public void simpanSemua(List<Dokter> list) {
        List<String> baris = new ArrayList<>();
        baris.add(HEADER);
        for (Dokter d : list) {
            baris.add(CsvUtil.toCsvLine(d.getIdDokter(), d.getUsername(), d.getNamaLengkap(), d.getSpesialisasi(), d.getNoTelepon(), d.getEmail()));
        }
        PenyimpananFile.tulisSemuaBaris(PenyimpananFile.FILE_DOKTER, baris);
    }

    public Optional<Dokter> cariByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return bacaSemua().stream()
                .filter(d -> d.getUsername().equalsIgnoreCase(username.trim()))
                .findFirst();
    }

    public Optional<Dokter> cariById(String idDokter) {
        if (idDokter == null) {
            return Optional.empty();
        }
        return bacaSemua().stream()
                .filter(d -> d.getIdDokter().equalsIgnoreCase(idDokter.trim()))
                .findFirst();
    }

    public Optional<Dokter> cariByNama(String nama) {
        if (nama == null) {
            return Optional.empty();
        }
        return bacaSemua().stream()
                .filter(d -> d.getNamaLengkap().equalsIgnoreCase(nama.trim()))
                .findFirst();
    }

    public boolean update(Dokter dokter) {
        List<Dokter> list = bacaSemua();
        boolean updated = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdDokter().equalsIgnoreCase(dokter.getIdDokter())
                    || list.get(i).getUsername().equalsIgnoreCase(dokter.getUsername())) {
                list.set(i, dokter);
                updated = true;
                break;
            }
        }
        if (updated) {
            simpanSemua(list);
        }
        return updated;
    }
}
