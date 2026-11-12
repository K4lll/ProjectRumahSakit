package sistempendaftarandanpelayananpasien.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.Pengguna;
import sistempendaftarandanpelayananpasien.storage.CsvUtil;
import sistempendaftarandanpelayananpasien.storage.PenyimpananFile;

/**
 * Repositori untuk operasi baca dan tulis data akun pengguna pada berkas pengguna.csv.
 */
public class PenggunaRepository {

    private static final String HEADER = "username,password_hash,salt,peran";

    public List<Pengguna> bacaSemua() {
        List<Pengguna> list = new ArrayList<>();
        List<String> baris = PenyimpananFile.bacaSemuaBaris(PenyimpananFile.FILE_PENGGUNA);

        for (int i = 0; i < baris.size(); i++) {
            if (i == 0 && baris.get(i).toLowerCase().contains("username")) {
                continue; // Lewati header
            }
            List<String> col = CsvUtil.parseLine(baris.get(i));
            if (col.size() >= 4) {
                list.add(new Pengguna(col.get(0), col.get(1), col.get(2), col.get(3)));
            }
        }
        return list;
    }

    public void simpanSemua(List<Pengguna> list) {
        List<String> baris = new ArrayList<>();
        baris.add(HEADER);
        for (Pengguna p : list) {
            baris.add(CsvUtil.toCsvLine(p.getUsername(), p.getPasswordHash(), p.getSalt(), p.getPeran()));
        }
        PenyimpananFile.tulisSemuaBaris(PenyimpananFile.FILE_PENGGUNA, baris);
    }

    public Optional<Pengguna> cariByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return bacaSemua().stream()
                .filter(p -> p.getUsername().equalsIgnoreCase(username.trim()))
                .findFirst();
    }

    public boolean tambah(Pengguna pengguna) {
        if (cariByUsername(pengguna.getUsername()).isPresent()) {
            return false;
        }
        List<Pengguna> list = bacaSemua();
        list.add(pengguna);
        simpanSemua(list);
        return true;
    }

    public boolean update(Pengguna pengguna) {
        List<Pengguna> list = bacaSemua();
        boolean updated = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equalsIgnoreCase(pengguna.getUsername())) {
                list.set(i, pengguna);
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
