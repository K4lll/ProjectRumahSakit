package sistempendaftarandanpelayananpasien.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import sistempendaftarandanpelayananpasien.auth.PasswordUtil;

/**
 * Pengelola berkas CSV lokal untuk penyimpanan data aplikasi rumah sakit.
 * Menjamin file dan direktori data terbuat otomatis jika belum ada.
 */
public class PenyimpananFile {

    public static final String FILE_PENGGUNA = "pengguna.csv";
    public static final String FILE_PASIEN = "pasien.csv";
    public static final String FILE_DOKTER = "dokter.csv";
    public static final String FILE_JADWAL = "jadwal.csv";
    public static final String FILE_PENDAFTARAN = "pendaftaran.csv";
    public static final String FILE_PELAYANAN = "pelayanan.csv";

    private static Path dataDirectory;

    static {
        inisialisasiDirektori();
        inisialisasiDataAwal();
    }

    private PenyimpananFile() {
    }

    /**
     * Menentukan dan memastikan keberadaan direktori penyimpanan data.
     */
    public static synchronized Path getDataDirectory() {
        if (dataDirectory == null || !Files.exists(dataDirectory)) {
            inisialisasiDirektori();
        }
        return dataDirectory;
    }

    private static void inisialisasiDirektori() {
        // Cek jika berjalan dari folder root NetBeans atau parent
        Path p1 = Paths.get("data");
        Path p2 = Paths.get("Sistem pendaftaran dan pelayanan pasien", "data");

        if (Files.exists(p1) && Files.isDirectory(p1)) {
            dataDirectory = p1.toAbsolutePath();
        } else if (Files.exists(p2) && Files.isDirectory(p2)) {
            dataDirectory = p2.toAbsolutePath();
        } else {
            try {
                if (Files.exists(Paths.get("nbproject"))) {
                    dataDirectory = Files.createDirectories(p1).toAbsolutePath();
                } else if (Files.exists(Paths.get("Sistem pendaftaran dan pelayanan pasien"))) {
                    dataDirectory = Files.createDirectories(p2).toAbsolutePath();
                } else {
                    dataDirectory = Files.createDirectories(p1).toAbsolutePath();
                }
            } catch (IOException e) {
                dataDirectory = Paths.get(System.getProperty("user.dir"), "data");
                dataDirectory.toFile().mkdirs();
            }
        }
    }

    public static Path getPath(String namaFile) {
        return getDataDirectory().resolve(namaFile);
    }

    /**
     * Membaca seluruh baris teks dari file CSV dengan encoding UTF-8.
     */
    public static synchronized List<String> bacaSemuaBaris(String namaFile) {
        List<String> baris = new ArrayList<>();
        File file = getPath(namaFile).toFile();
        if (!file.exists()) {
            return baris;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    baris.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Gagal membaca file " + namaFile + ": " + e.getMessage());
        }
        return baris;
    }

    /**
     * Menulis ulang seluruh baris teks ke file CSV dengan encoding UTF-8.
     */
    public static synchronized void tulisSemuaBaris(String namaFile, List<String> baris) {
        File file = getPath(namaFile).toFile();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (String line : baris) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Gagal menulis file " + namaFile + ": " + e.getMessage());
        }
    }

    /**
     * Menambahkan satu baris baru ke akhir file CSV.
     */
    public static synchronized void tambahBaris(String namaFile, String baris) {
        File file = getPath(namaFile).toFile();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            writer.write(baris);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Gagal menambahkan baris ke file " + namaFile + ": " + e.getMessage());
        }
    }

    /**
     * Membuat berkas CSV awal lengkap dengan header dan data demo jika belum ada.
     */
    public static synchronized void inisialisasiDataAwal() {
        File fPengguna = getPath(FILE_PENGGUNA).toFile();
        if (!fPengguna.exists()) {
            List<String> lines = new ArrayList<>();
            lines.add("username,password_hash,salt,peran");

            // Salt & Hash untuk akun bawaan
            String saltDemo = PasswordUtil.generateSalt();
            lines.add(CsvUtil.toCsvLine("demo", PasswordUtil.hashPassword("demo123", saltDemo), saltDemo, "pasien"));

            String saltFahrul = PasswordUtil.generateSalt();
            lines.add(CsvUtil.toCsvLine("fahrul", PasswordUtil.hashPassword("fahrul123", saltFahrul), saltFahrul, "pasien"));

            String saltDokter = PasswordUtil.generateSalt();
            lines.add(CsvUtil.toCsvLine("dokter", PasswordUtil.hashPassword("dokter123", saltDokter), saltDokter, "dokter"));

            String saltAndi = PasswordUtil.generateSalt();
            lines.add(CsvUtil.toCsvLine("andi", PasswordUtil.hashPassword("andi123", saltAndi), saltAndi, "dokter"));

            String saltSari = PasswordUtil.generateSalt();
            lines.add(CsvUtil.toCsvLine("sari", PasswordUtil.hashPassword("sari123", saltSari), saltSari, "dokter"));

            tulisSemuaBaris(FILE_PENGGUNA, lines);
        }

        File fPasien = getPath(FILE_PASIEN).toFile();
        if (!fPasien.exists()) {
            List<String> lines = new ArrayList<>();
            lines.add("id_pasien,username,nama_lengkap,nik,no_telepon,alamat,tanggal_lahir,jenis_kelamin,golongan_darah");
            lines.add(CsvUtil.toCsvLine("PAS-001", "demo", "Budi Santoso", "1471010101900001", "081234567890", "Jl. Sudirman No. 10, Pekanbaru", "01-01-1990", "Laki-laki", "O"));
            lines.add(CsvUtil.toCsvLine("PAS-002", "fahrul", "M. Fahrul Rozi", "3174015505920002", "082198765432", "Jl. Melati No. 45, Pekanbaru", "15-05-1992", "Laki-laki", "B"));
            lines.add(CsvUtil.toCsvLine("PAS-003", "siti", "Siti Rahmawati", "3174012012880003", "085211223344", "Jl. Ahmad Yani No. 12, Pekanbaru", "20-12-1988", "Perempuan", "A"));
            tulisSemuaBaris(FILE_PASIEN, lines);
        }

        File fDokter = getPath(FILE_DOKTER).toFile();
        if (!fDokter.exists()) {
            List<String> lines = new ArrayList<>();
            lines.add("id_dokter,username,nama_lengkap,spesialisasi,no_telepon,email");
            lines.add(CsvUtil.toCsvLine("DOC-001", "dokter", "dr. Andi Pratama, Sp.PD", "Penyakit Dalam", "081122334455", "andi@rssehat.com"));
            lines.add(CsvUtil.toCsvLine("DOC-002", "sari", "dr. Sari Wulandari, Sp.A", "Anak", "081133445566", "sari@rssehat.com"));
            lines.add(CsvUtil.toCsvLine("DOC-003", "rudi", "dr. Rudi Hartono, Sp.JP", "Jantung", "081144556677", "rudi@rssehat.com"));
            lines.add(CsvUtil.toCsvLine("DOC-004", "maya", "dr. Maya Lestari, Sp.KK", "Kulit & Kelamin", "081155667788", "maya@rssehat.com"));
            tulisSemuaBaris(FILE_DOKTER, lines);
        }

        File fJadwal = getPath(FILE_JADWAL).toFile();
        if (!fJadwal.exists()) {
            List<String> lines = new ArrayList<>();
            lines.add("id_jadwal,id_dokter,nama_dokter,hari,jam_mulai,jam_selesai,kuota");
            lines.add(CsvUtil.toCsvLine("JAD-001", "DOC-001", "dr. Andi Pratama, Sp.PD", "Senin", "08.00", "12.00", "20"));
            lines.add(CsvUtil.toCsvLine("JAD-002", "DOC-001", "dr. Andi Pratama, Sp.PD", "Rabu", "08.00", "12.00", "20"));
            lines.add(CsvUtil.toCsvLine("JAD-003", "DOC-002", "dr. Sari Wulandari, Sp.A", "Selasa", "09.00", "13.00", "15"));
            lines.add(CsvUtil.toCsvLine("JAD-004", "DOC-002", "dr. Sari Wulandari, Sp.A", "Kamis", "09.00", "13.00", "15"));
            lines.add(CsvUtil.toCsvLine("JAD-005", "DOC-003", "dr. Rudi Hartono, Sp.JP", "Rabu", "13.00", "17.00", "15"));
            lines.add(CsvUtil.toCsvLine("JAD-006", "DOC-003", "dr. Rudi Hartono, Sp.JP", "Jumat", "13.00", "17.00", "15"));
            lines.add(CsvUtil.toCsvLine("JAD-007", "DOC-004", "dr. Maya Lestari, Sp.KK", "Senin", "14.00", "18.00", "15"));
            lines.add(CsvUtil.toCsvLine("JAD-008", "DOC-004", "dr. Maya Lestari, Sp.KK", "Sabtu", "08.00", "11.00", "10"));
            tulisSemuaBaris(FILE_JADWAL, lines);
        }

        File fPendaftaran = getPath(FILE_PENDAFTARAN).toFile();
        if (!fPendaftaran.exists()) {
            List<String> lines = new ArrayList<>();
            lines.add("id_pendaftaran,id_pasien,nama_pasien,id_dokter,nama_dokter,id_jadwal,jadwal,tanggal_kunjungan,keluhan,status,alasan_penolakan,tanggal_daftar");
            lines.add(CsvUtil.toCsvLine("REG-001", "PAS-001", "Budi Santoso", "DOC-001", "dr. Andi Pratama, Sp.PD", "JAD-001", "Senin 08.00-12.00", "10-11-2026", "Demam 3 hari", "Selesai", "", "09-11-2026"));
            lines.add(CsvUtil.toCsvLine("REG-002", "PAS-001", "Budi Santoso", "DOC-002", "dr. Sari Wulandari, Sp.A", "JAD-003", "Selasa 09.00-13.00", "11-11-2026", "Batuk pilek", "Ditolak", "Jadwal dokter berhalangan", "10-11-2026"));
            lines.add(CsvUtil.toCsvLine("REG-003", "PAS-001", "Budi Santoso", "DOC-003", "dr. Rudi Hartono, Sp.JP", "JAD-005", "Rabu 13.00-17.00", "12-11-2026", "Nyeri dada ringan", "Diterima", "", "11-11-2026"));
            lines.add(CsvUtil.toCsvLine("REG-004", "PAS-001", "Budi Santoso", "DOC-001", "dr. Andi Pratama, Sp.PD", "JAD-002", "Rabu 08.00-12.00", "12-11-2026", "Kontrol lambung", "Menunggu", "", "11-11-2026"));
            tulisSemuaBaris(FILE_PENDAFTARAN, lines);
        }

        File fPelayanan = getPath(FILE_PELAYANAN).toFile();
        if (!fPelayanan.exists()) {
            List<String> lines = new ArrayList<>();
            lines.add("id_pelayanan,id_pendaftaran,id_pasien,nama_pasien,id_dokter,nama_dokter,tanggal_pelayanan,diagnosis,keluhan,tindakan_hasil,resep_obat,catatan_dokter");
            lines.add(CsvUtil.toCsvLine("PEL-001", "REG-001", "PAS-001", "Budi Santoso", "DOC-001", "dr. Andi Pratama, Sp.PD", "10-11-2026", "Infeksi virus ringan", "Demam 3 hari", "Istirahat dan hidrasi", "Paracetamol 500mg 3x1", "Banyak minum air putih, istirahat cukup"));
            tulisSemuaBaris(FILE_PELAYANAN, lines);
        }
    }

    public static void main(String[] args) {
        inisialisasiDataAwal();
        System.out.println("Data CSV berhasil diinisialisasi pada: " + getDataDirectory());
    }
}
