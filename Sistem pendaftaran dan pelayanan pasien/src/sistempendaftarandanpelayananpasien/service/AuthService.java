package sistempendaftarandanpelayananpasien.service;

import java.util.Optional;
import sistempendaftarandanpelayananpasien.auth.PasswordUtil;
import sistempendaftarandanpelayananpasien.auth.SessionManager;
import sistempendaftarandanpelayananpasien.model.Dokter;
import sistempendaftarandanpelayananpasien.model.Pasien;
import sistempendaftarandanpelayananpasien.model.Pengguna;
import sistempendaftarandanpelayananpasien.repository.DokterRepository;
import sistempendaftarandanpelayananpasien.repository.PasienRepository;
import sistempendaftarandanpelayananpasien.repository.PenggunaRepository;
import sistempendaftarandanpelayananpasien.utils.IDGenerator;

/**
 * Layanan bisnis untuk autentikasi pengguna, registrasi akun pasien baru,
 * serta verifikasi hak akses berbasis password hash dan salt.
 */
public class AuthService {

    private final PenggunaRepository penggunaRepo = new PenggunaRepository();
    private final PasienRepository pasienRepo = new PasienRepository();
    private final DokterRepository dokterRepo = new DokterRepository();

    /**
     * Memvalidasi login pengguna (pasien atau dokter) dengan password hash dan salt.
     * Jika sukses, otomatis mengatur SessionManager.
     */
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        Optional<Pengguna> optUser = penggunaRepo.cariByUsername(username.trim());
        if (optUser.isEmpty()) {
            return false;
        }

        Pengguna p = optUser.get();
        boolean valid = PasswordUtil.verifikasiPassword(password, p.getSalt(), p.getPasswordHash());
        if (!valid) {
            return false;
        }

        String namaLengkap = p.getUsername();
        if ("pasien".equalsIgnoreCase(p.getPeran())) {
            Optional<Pasien> optPasien = pasienRepo.cariByUsername(p.getUsername());
            if (optPasien.isPresent()) {
                namaLengkap = optPasien.get().getNamaLengkap();
                SessionManager.setAtribut("pasien", optPasien.get());
                SessionManager.setAtribut("idPasien", optPasien.get().getIdPasien());
                SessionManager.setAtribut("nik", optPasien.get().getNik());
                SessionManager.setAtribut("telp", optPasien.get().getNoTelepon());
                SessionManager.setAtribut("alamat", optPasien.get().getAlamat());
            }
        } else if ("dokter".equalsIgnoreCase(p.getPeran())) {
            Optional<Dokter> optDokter = dokterRepo.cariByUsername(p.getUsername());
            if (optDokter.isPresent()) {
                namaLengkap = optDokter.get().getNamaLengkap();
                SessionManager.setAtribut("dokter", optDokter.get());
                SessionManager.setAtribut("idDokter", optDokter.get().getIdDokter());
                SessionManager.setAtribut("spesialisasi", optDokter.get().getSpesialisasi());
            }
        }

        SessionManager.setSesi(p.getUsername(), namaLengkap, p.getPeran());
        return true;
    }

    /**
     * Mendaftarkan akun pasien baru dan membuat profil awal pasien.
     */
    public boolean registrasiPasien(String username, String password, String nama, String nik, String telp, String alamat) {
        if (penggunaRepo.cariByUsername(username).isPresent()) {
            return false;
        }

        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashPassword(password, salt);

        Pengguna pengguna = new Pengguna(username, hash, salt, "pasien");
        boolean okUser = penggunaRepo.tambah(pengguna);
        if (!okUser) {
            return false;
        }

        String idPasien = IDGenerator.generateId("PAS", pasienRepo.bacaSemua().size());
        Pasien pasien = new Pasien(idPasien, username, nama, nik, telp, alamat, "", "", "");
        pasienRepo.tambah(pasien);
        return true;
    }

    /**
     * Mengubah password pengguna aktif.
     */
    public boolean ubahPassword(String username, String passwordLama, String passwordBaru) {
        Optional<Pengguna> opt = penggunaRepo.cariByUsername(username);
        if (opt.isEmpty()) {
            return false;
        }
        Pengguna p = opt.get();
        if (!PasswordUtil.verifikasiPassword(passwordLama, p.getSalt(), p.getPasswordHash())) {
            return false;
        }
        String saltBaru = PasswordUtil.generateSalt();
        String hashBaru = PasswordUtil.hashPassword(passwordBaru, saltBaru);
        p.setSalt(saltBaru);
        p.setPasswordHash(hashBaru);
        return penggunaRepo.update(p);
    }

    public static void main(String[] args) {
        AuthService auth = new AuthService();
        boolean ok1 = auth.login("demo", "demo123");
        boolean ok2 = auth.login("dokter", "dokter123");
        boolean fail = auth.login("demo", "passwordsalah");
        System.out.println("TEST HASIL: demo=" + ok1 + ", dokter=" + ok2 + ", salah=" + fail);
    }
}
