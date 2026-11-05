/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistem.pendaftaran.dan.pelayanan.pasien;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Penyimpanan data ke file teks di folder "data" (relatif terhadap folder
 * kerja program; di NetBeans itu folder root project).
 *
 * Satu baris = satu data, kolom dipisah tanda "|". Karakter "|", "\" dan
 * baris baru di dalam isian otomatis di-escape, jadi aman dipakai di keluhan
 * atau alamat. Password disimpan sebagai hash PBKDF2 + salt, tidak pernah
 * disimpan apa adanya.
 *
 * @author LENOVO
 */
public class PenyimpananFile {

    public static final String PERAN_PASIEN = "pasien";
    public static final String PERAN_DOKTER = "dokter";

    private static final String FOLDER = "data";
    private static final String FILE_PASIEN = "pasien.txt";
    private static final String FILE_DOKTER = "dokter.txt";
    private static final String FILE_JADWAL = "jadwal.txt";
    private static final String FILE_PENDAFTARAN = "pendaftaran.txt";
    private static final String FILE_PENGGUNA = "pengguna.txt";

    private static final int ITERASI_HASH = 65536;
    private static final int PANJANG_SALT = 16;
    private static final int PANJANG_HASH_BIT = 256;

    // ------------------------------------------------------------------
    // Pasien
    // ------------------------------------------------------------------

    public static synchronized void simpanPasien(Pasien p) throws IOException {
        tambahBaris(FILE_PASIEN, p.getNama(), p.getAlamat(), p.getTempatTanggalLahir(),
                String.valueOf(p.getUmur()), p.getJenisKelamin());
    }

    public static synchronized List<Pasien> semuaPasien() throws IOException {
        List<Pasien> hasil = new ArrayList<>();
        for (String[] k : bacaBaris(FILE_PASIEN, 5)) {
            int umur;
            try {
                umur = Integer.parseInt(k[3]);
            } catch (NumberFormatException e) {
                continue; // baris rusak dilewati
            }
            hasil.add(new Pasien(k[0], k[1], k[2], umur, k[4]));
        }
        return hasil;
    }

    // ------------------------------------------------------------------
    // Dokter
    // ------------------------------------------------------------------

    public static synchronized void simpanDokter(Dokter d) throws IOException {
        tambahBaris(FILE_DOKTER, d.getNama(), d.getSpesialisasi());
    }

    public static synchronized List<Dokter> semuaDokter() throws IOException {
        List<Dokter> hasil = new ArrayList<>();
        for (String[] k : bacaBaris(FILE_DOKTER, 2)) {
            hasil.add(new Dokter(k[0], k[1]));
        }
        return hasil;
    }

    // ------------------------------------------------------------------
    // Jadwal praktik dokter
    // ------------------------------------------------------------------

    public static synchronized void simpanJadwal(JadwalPraktik j) throws IOException {
        tambahBaris(FILE_JADWAL, j.getNamaDokter(), j.getHari(), j.getJamMulai(), j.getJamSelesai());
    }

    public static synchronized List<JadwalPraktik> semuaJadwal() throws IOException {
        List<JadwalPraktik> hasil = new ArrayList<>();
        for (String[] k : bacaBaris(FILE_JADWAL, 4)) {
            hasil.add(new JadwalPraktik(k[0], k[1], k[2], k[3]));
        }
        return hasil;
    }

    // ------------------------------------------------------------------
    // Pendaftaran (kunjungan pasien)
    // ------------------------------------------------------------------

    public static synchronized void simpanPendaftaran(Pendaftaran p) throws IOException {
        tambahBaris(FILE_PENDAFTARAN, p.getNamaPasien(), p.getNamaDokter(), p.getKeluhan(),
                p.getTanggal(), p.getWaktu(), p.getStatus());
    }

    public static synchronized List<Pendaftaran> semuaPendaftaran() throws IOException {
        List<Pendaftaran> hasil = new ArrayList<>();
        for (String[] k : bacaBaris(FILE_PENDAFTARAN, 6)) {
            Pendaftaran p = new Pendaftaran(k[0], k[1], k[2], k[3], k[4]);
            p.setStatus(k[5]);
            hasil.add(p);
        }
        return hasil;
    }

    /**
     * Mengubah status satu pendaftaran. Pendaftaran dicari lewat kombinasi
     * nama pasien, nama dokter, tanggal, dan waktu (bukan nomor baris, supaya
     * tetap benar walau tabel di layar disaring atau diurutkan ulang).
     *
     * @return true kalau ada pendaftaran yang cocok dan sudah diubah
     */
    public static synchronized boolean ubahStatusPendaftaran(String namaPasien, String namaDokter,
            String tanggal, String waktu, String statusBaru) throws IOException {
        List<String[]> baris = bacaBaris(FILE_PENDAFTARAN, 6);
        boolean ketemu = false;
        for (String[] k : baris) {
            if (!ketemu && k[0].equals(namaPasien) && k[1].equals(namaDokter)
                    && k[3].equals(tanggal) && k[4].equals(waktu)) {
                k[5] = statusBaru;
                ketemu = true; // hanya satu baris pertama yang cocok
            }
        }
        if (ketemu) {
            tulisSemua(FILE_PENDAFTARAN, baris);
        }
        return ketemu;
    }

    // ------------------------------------------------------------------
    // Akun login (password di-hash)
    // ------------------------------------------------------------------

    /**
     * Menambah akun baru.
     *
     * @return false kalau username itu sudah dipakai (tanpa membedakan huruf besar/kecil)
     */
    public static synchronized boolean tambahPengguna(String username, String password, String peran)
            throws IOException {
        if (penggunaAda(username)) {
            return false;
        }
        byte[] salt = new byte[PANJANG_SALT];
        new SecureRandom().nextBytes(salt);
        String hash = hitungHash(password, salt);
        tambahBaris(FILE_PENGGUNA, username.trim(), peran, Base64.getEncoder().encodeToString(salt), hash);
        return true;
    }

    public static synchronized boolean penggunaAda(String username) throws IOException {
        for (String[] k : bacaBaris(FILE_PENGGUNA, 4)) {
            if (k[0].equalsIgnoreCase(username.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cek login. Username tidak membedakan huruf besar/kecil, password
     * membedakan. Peran harus cocok (akun pasien tidak bisa masuk sebagai dokter).
     */
    public static synchronized boolean cekLogin(String username, String password, String peran)
            throws IOException {
        for (String[] k : bacaBaris(FILE_PENGGUNA, 4)) {
            if (k[0].equalsIgnoreCase(username.trim())) {
                if (!k[1].equals(peran)) {
                    return false;
                }
                byte[] salt = Base64.getDecoder().decode(k[2]);
                byte[] hashTersimpan = k[3].getBytes(StandardCharsets.UTF_8);
                byte[] hashInput = hitungHash(password, salt).getBytes(StandardCharsets.UTF_8);
                return MessageDigest.isEqual(hashTersimpan, hashInput);
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Bagian dalam (helper baca/tulis file)
    // ------------------------------------------------------------------

    private static String hitungHash(String password, byte[] salt) throws IOException {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERASI_HASH, PANJANG_HASH_BIT);
            SecretKeyFactory pabrik = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return Base64.getEncoder().encodeToString(pabrik.generateSecret(spec).getEncoded());
        } catch (java.security.GeneralSecurityException e) {
            throw new IOException("Gagal menghitung hash password", e);
        }
    }

    private static Path pathFile(String namaFile) throws IOException {
        Path folder = Paths.get(FOLDER);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }
        return folder.resolve(namaFile);
    }

    private static void tambahBaris(String namaFile, String... kolom) throws IOException {
        String baris = gabung(kolom) + System.lineSeparator();
        Files.write(pathFile(namaFile), baris.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private static void tulisSemua(String namaFile, List<String[]> semuaBaris) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String[] k : semuaBaris) {
            sb.append(gabung(k)).append(System.lineSeparator());
        }
        Files.write(pathFile(namaFile), sb.toString().getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /** Membaca file; file belum ada = daftar kosong. Baris yang jumlah kolomnya salah dilewati. */
    private static List<String[]> bacaBaris(String namaFile, int jumlahKolom) throws IOException {
        List<String[]> hasil = new ArrayList<>();
        Path path = pathFile(namaFile);
        if (!Files.exists(path)) {
            return hasil;
        }
        for (String baris : Files.readAllLines(path, StandardCharsets.UTF_8)) {
            if (baris.trim().isEmpty()) {
                continue;
            }
            String[] mentah = baris.split("\\|", -1);
            if (mentah.length != jumlahKolom) {
                continue;
            }
            String[] kolom = new String[jumlahKolom];
            for (int i = 0; i < jumlahKolom; i++) {
                kolom[i] = bukaEscape(mentah[i]);
            }
            hasil.add(kolom);
        }
        return hasil;
    }

    private static String gabung(String[] kolom) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < kolom.length; i++) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(escape(kolom[i] == null ? "" : kolom[i]));
        }
        return sb.toString();
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("|", "\\p")
                .replace("\r", "\\r").replace("\n", "\\n");
    }

    private static String bukaEscape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < s.length()) {
                char n = s.charAt(++i);
                switch (n) {
                    case 'p': sb.append('|'); break;
                    case 'n': sb.append('\n'); break;
                    case 'r': sb.append('\r'); break;
                    default: sb.append(n); break; // termasuk "\\" -> "\"
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
