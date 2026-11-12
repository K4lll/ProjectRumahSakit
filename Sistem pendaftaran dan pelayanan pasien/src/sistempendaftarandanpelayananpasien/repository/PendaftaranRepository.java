package sistempendaftarandanpelayananpasien.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.Pendaftaran;
import sistempendaftarandanpelayananpasien.storage.CsvUtil;
import sistempendaftarandanpelayananpasien.storage.PenyimpananFile;

/**
 * Repositori untuk operasi pendaftaran pasien pada berkas pendaftaran.csv.
 */
public class PendaftaranRepository {

    private static final String HEADER = "id_pendaftaran,id_pasien,nama_pasien,id_dokter,nama_dokter,id_jadwal,jadwal,tanggal_kunjungan,keluhan,status,alasan_penolakan,tanggal_daftar";

    public List<Pendaftaran> bacaSemua() {
        List<Pendaftaran> list = new ArrayList<>();
        List<String> baris = PenyimpananFile.bacaSemuaBaris(PenyimpananFile.FILE_PENDAFTARAN);

        for (int i = 0; i < baris.size(); i++) {
            if (i == 0 && baris.get(i).toLowerCase().contains("id_pendaftaran")) {
                continue;
            }
            List<String> c = CsvUtil.parseLine(baris.get(i));
            if (c.size() >= 10) {
                String alasan = c.size() > 10 ? c.get(10) : "";
                String tglDaftar = c.size() > 11 ? c.get(11) : "";
                list.add(new Pendaftaran(c.get(0), c.get(1), c.get(2), c.get(3), c.get(4), c.get(5), c.get(6), c.get(7), c.get(8), c.get(9), alasan, tglDaftar));
            }
        }
        return list;
    }

    public void simpanSemua(List<Pendaftaran> list) {
        List<String> baris = new ArrayList<>();
        baris.add(HEADER);
        for (Pendaftaran p : list) {
            baris.add(CsvUtil.toCsvLine(
                    p.getIdPendaftaran(),
                    p.getIdPasien(),
                    p.getNamaPasien(),
                    p.getIdDokter(),
                    p.getNamaDokter(),
                    p.getIdJadwal(),
                    p.getJadwal(),
                    p.getTanggalKunjungan(),
                    p.getKeluhan(),
                    p.getStatus(),
                    p.getAlasanPenolakan(),
                    p.getTanggalDaftar()
            ));
        }
        PenyimpananFile.tulisSemuaBaris(PenyimpananFile.FILE_PENDAFTARAN, baris);
    }

    public Optional<Pendaftaran> cariById(String idPendaftaran) {
        if (idPendaftaran == null) {
            return Optional.empty();
        }
        return bacaSemua().stream()
                .filter(p -> p.getIdPendaftaran().equalsIgnoreCase(idPendaftaran.trim()))
                .findFirst();
    }

    public List<Pendaftaran> cariByPasien(String idPasienOrUsername) {
        List<Pendaftaran> hasil = new ArrayList<>();
        if (idPasienOrUsername == null) {
            return hasil;
        }
        for (Pendaftaran p : bacaSemua()) {
            if (p.getIdPasien().equalsIgnoreCase(idPasienOrUsername)
                    || p.getNamaPasien().equalsIgnoreCase(idPasienOrUsername)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public List<Pendaftaran> cariByDokter(String idDokterOrNama) {
        List<Pendaftaran> hasil = new ArrayList<>();
        if (idDokterOrNama == null) {
            return hasil;
        }
        for (Pendaftaran p : bacaSemua()) {
            if (p.getIdDokter().equalsIgnoreCase(idDokterOrNama)
                    || p.getNamaDokter().equalsIgnoreCase(idDokterOrNama)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public void tambah(Pendaftaran pendaftaran) {
        List<Pendaftaran> list = bacaSemua();
        list.add(pendaftaran);
        simpanSemua(list);
    }

    public boolean update(Pendaftaran pendaftaran) {
        List<Pendaftaran> list = bacaSemua();
        boolean updated = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdPendaftaran().equalsIgnoreCase(pendaftaran.getIdPendaftaran())) {
                list.set(i, pendaftaran);
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
