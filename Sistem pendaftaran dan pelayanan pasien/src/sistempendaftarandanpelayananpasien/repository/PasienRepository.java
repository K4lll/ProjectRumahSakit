package sistempendaftarandanpelayananpasien.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.Pasien;
import sistempendaftarandanpelayananpasien.storage.CsvUtil;
import sistempendaftarandanpelayananpasien.storage.PenyimpananFile;

/**
 * Repositori untuk membaca dan menyimpan data profil pasien pada berkas pasien.csv.
 */
public class PasienRepository {

    private static final String HEADER = "id_pasien,username,nama_lengkap,nik,no_telepon,alamat,tanggal_lahir,jenis_kelamin,golongan_darah";

    public List<Pasien> bacaSemua() {
        List<Pasien> list = new ArrayList<>();
        List<String> baris = PenyimpananFile.bacaSemuaBaris(PenyimpananFile.FILE_PASIEN);

        for (int i = 0; i < baris.size(); i++) {
            if (i == 0 && baris.get(i).toLowerCase().contains("id_pasien")) {
                continue;
            }
            List<String> c = CsvUtil.parseLine(baris.get(i));
            if (c.size() >= 6) {
                String tgl = c.size() > 6 ? c.get(6) : "";
                String jk = c.size() > 7 ? c.get(7) : "";
                String gol = c.size() > 8 ? c.get(8) : "";
                list.add(new Pasien(c.get(0), c.get(1), c.get(2), c.get(3), c.get(4), c.get(5), tgl, jk, gol));
            }
        }
        return list;
    }

    public void simpanSemua(List<Pasien> list) {
        List<String> baris = new ArrayList<>();
        baris.add(HEADER);
        for (Pasien p : list) {
            baris.add(CsvUtil.toCsvLine(
                    p.getIdPasien(),
                    p.getUsername(),
                    p.getNamaLengkap(),
                    p.getNik(),
                    p.getNoTelepon(),
                    p.getAlamat(),
                    p.getTanggalLahir(),
                    p.getJenisKelamin(),
                    p.getGolonganDarah()
            ));
        }
        PenyimpananFile.tulisSemuaBaris(PenyimpananFile.FILE_PASIEN, baris);
    }

    public Optional<Pasien> cariByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return bacaSemua().stream()
                .filter(p -> p.getUsername().equalsIgnoreCase(username.trim()))
                .findFirst();
    }

    public Optional<Pasien> cariById(String idPasien) {
        if (idPasien == null) {
            return Optional.empty();
        }
        return bacaSemua().stream()
                .filter(p -> p.getIdPasien().equalsIgnoreCase(idPasien.trim()))
                .findFirst();
    }

    public void tambah(Pasien pasien) {
        List<Pasien> list = bacaSemua();
        list.add(pasien);
        simpanSemua(list);
    }

    public boolean update(Pasien pasien) {
        List<Pasien> list = bacaSemua();
        boolean updated = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equalsIgnoreCase(pasien.getUsername())
                    || list.get(i).getIdPasien().equalsIgnoreCase(pasien.getIdPasien())) {
                list.set(i, pasien);
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
