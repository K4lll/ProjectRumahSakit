package sistempendaftarandanpelayananpasien.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sistempendaftarandanpelayananpasien.model.Pelayanan;
import sistempendaftarandanpelayananpasien.storage.CsvUtil;
import sistempendaftarandanpelayananpasien.storage.PenyimpananFile;

/**
 * Repositori untuk data hasil pelayanan dan rekam medis pasien pada berkas pelayanan.csv.
 */
public class PelayananRepository {

    private static final String HEADER = "id_pelayanan,id_pendaftaran,id_pasien,nama_pasien,id_dokter,nama_dokter,tanggal_pelayanan,diagnosis,keluhan,tindakan_hasil,resep_obat,catatan_dokter";

    public List<Pelayanan> bacaSemua() {
        List<Pelayanan> list = new ArrayList<>();
        List<String> baris = PenyimpananFile.bacaSemuaBaris(PenyimpananFile.FILE_PELAYANAN);

        for (int i = 0; i < baris.size(); i++) {
            if (i == 0 && baris.get(i).toLowerCase().contains("id_pelayanan")) {
                continue;
            }
            List<String> c = CsvUtil.parseLine(baris.get(i));
            if (c.size() >= 10) {
                String resep = c.size() > 10 ? c.get(10) : "";
                String catatan = c.size() > 11 ? c.get(11) : "";
                list.add(new Pelayanan(c.get(0), c.get(1), c.get(2), c.get(3), c.get(4), c.get(5), c.get(6), c.get(7), c.get(8), c.get(9), resep, catatan));
            }
        }
        return list;
    }

    public void simpanSemua(List<Pelayanan> list) {
        List<String> baris = new ArrayList<>();
        baris.add(HEADER);
        for (Pelayanan p : list) {
            baris.add(CsvUtil.toCsvLine(
                    p.getIdPelayanan(),
                    p.getIdPendaftaran(),
                    p.getIdPasien(),
                    p.getNamaPasien(),
                    p.getIdDokter(),
                    p.getNamaDokter(),
                    p.getTanggalPelayanan(),
                    p.getDiagnosis(),
                    p.getKeluhan(),
                    p.getTindakanHasil(),
                    p.getResepObat(),
                    p.getCatatanDokter()
            ));
        }
        PenyimpananFile.tulisSemuaBaris(PenyimpananFile.FILE_PELAYANAN, baris);
    }

    public Optional<Pelayanan> cariById(String idPelayanan) {
        return bacaSemua().stream()
                .filter(p -> p.getIdPelayanan().equalsIgnoreCase(idPelayanan.trim()))
                .findFirst();
    }

    public Optional<Pelayanan> cariByPendaftaran(String idPendaftaran) {
        return bacaSemua().stream()
                .filter(p -> p.getIdPendaftaran().equalsIgnoreCase(idPendaftaran.trim()))
                .findFirst();
    }

    public List<Pelayanan> cariByPasien(String idPasienOrUsername) {
        List<Pelayanan> hasil = new ArrayList<>();
        if (idPasienOrUsername == null) {
            return hasil;
        }
        for (Pelayanan p : bacaSemua()) {
            if (p.getIdPasien().equalsIgnoreCase(idPasienOrUsername)
                    || p.getNamaPasien().equalsIgnoreCase(idPasienOrUsername)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public List<Pelayanan> cariByDokter(String idDokterOrNama) {
        List<Pelayanan> hasil = new ArrayList<>();
        if (idDokterOrNama == null) {
            return hasil;
        }
        for (Pelayanan p : bacaSemua()) {
            if (p.getIdDokter().equalsIgnoreCase(idDokterOrNama)
                    || p.getNamaDokter().equalsIgnoreCase(idDokterOrNama)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public void tambah(Pelayanan pelayanan) {
        List<Pelayanan> list = bacaSemua();
        list.add(pelayanan);
        simpanSemua(list);
    }
}
