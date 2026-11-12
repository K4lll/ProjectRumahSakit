package sistempendaftarandanpelayananpasien.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.JadwalPraktik;
import sistempendaftarandanpelayananpasien.storage.CsvUtil;
import sistempendaftarandanpelayananpasien.storage.PenyimpananFile;

/**
 * Repositori untuk membaca dan menyimpan data jadwal praktik dokter pada jadwal.csv.
 */
public class JadwalRepository {

    private static final String HEADER = "id_jadwal,id_dokter,nama_dokter,hari,jam_mulai,jam_selesai,kuota";

    public List<JadwalPraktik> bacaSemua() {
        List<JadwalPraktik> list = new ArrayList<>();
        List<String> baris = PenyimpananFile.bacaSemuaBaris(PenyimpananFile.FILE_JADWAL);

        for (int i = 0; i < baris.size(); i++) {
            if (i == 0 && baris.get(i).toLowerCase().contains("id_jadwal")) {
                continue;
            }
            List<String> c = CsvUtil.parseLine(baris.get(i));
            if (c.size() >= 7) {
                int kuota = 20;
                try {
                    kuota = Integer.parseInt(c.get(6));
                } catch (NumberFormatException ignored) {
                }
                list.add(new JadwalPraktik(c.get(0), c.get(1), c.get(2), c.get(3), c.get(4), c.get(5), kuota));
            }
        }
        return list;
    }

    public void simpanSemua(List<JadwalPraktik> list) {
        List<String> baris = new ArrayList<>();
        baris.add(HEADER);
        for (JadwalPraktik j : list) {
            baris.add(CsvUtil.toCsvLine(
                    j.getIdJadwal(),
                    j.getIdDokter(),
                    j.getNamaDokter(),
                    j.getHari(),
                    j.getJamMulai(),
                    j.getJamSelesai(),
                    String.valueOf(j.getKuota())
            ));
        }
        PenyimpananFile.tulisSemuaBaris(PenyimpananFile.FILE_JADWAL, baris);
    }

    public List<JadwalPraktik> cariByDokter(String idDokter) {
        List<JadwalPraktik> hasil = new ArrayList<>();
        for (JadwalPraktik j : bacaSemua()) {
            if (j.getIdDokter().equalsIgnoreCase(idDokter) || j.getNamaDokter().equalsIgnoreCase(idDokter)) {
                hasil.add(j);
            }
        }
        return hasil;
    }

    public Optional<JadwalPraktik> cariById(String idJadwal) {
        return bacaSemua().stream()
                .filter(j -> j.getIdJadwal().equalsIgnoreCase(idJadwal.trim()))
                .findFirst();
    }

    public void tambah(JadwalPraktik jadwal) {
        List<JadwalPraktik> list = bacaSemua();
        list.add(jadwal);
        simpanSemua(list);
    }

    public boolean update(JadwalPraktik jadwal) {
        List<JadwalPraktik> list = bacaSemua();
        boolean updated = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdJadwal().equalsIgnoreCase(jadwal.getIdJadwal())) {
                list.set(i, jadwal);
                updated = true;
                break;
            }
        }
        if (updated) {
            simpanSemua(list);
        }
        return updated;
    }

    public boolean hapus(String idJadwal) {
        List<JadwalPraktik> list = bacaSemua();
        boolean removed = list.removeIf(j -> j.getIdJadwal().equalsIgnoreCase(idJadwal));
        if (removed) {
            simpanSemua(list);
        }
        return removed;
    }
}
