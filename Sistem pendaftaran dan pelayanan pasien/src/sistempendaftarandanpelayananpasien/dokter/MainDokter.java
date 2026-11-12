/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistempendaftarandanpelayananpasien.dokter;

import java.util.List;
import sistempendaftarandanpelayananpasien.auth.SessionManager;
import sistempendaftarandanpelayananpasien.model.Dokter;
import sistempendaftarandanpelayananpasien.model.JadwalPraktik;
import sistempendaftarandanpelayananpasien.model.Pelayanan;
import sistempendaftarandanpelayananpasien.model.Pendaftaran;
import sistempendaftarandanpelayananpasien.service.DokterService;
import sistempendaftarandanpelayananpasien.service.JadwalService;
import sistempendaftarandanpelayananpasien.service.PelayananService;
import sistempendaftarandanpelayananpasien.service.PendaftaranService;

/**
 *
 * @author LENOVO
 */
public class MainDokter extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainDokter.class.getName());

    private static final java.awt.Color VENICE = new java.awt.Color(0x16587B);
    private static final java.awt.Color ROCK = new java.awt.Color(0x84B3CE);
    private boolean loading;

    private final DokterService dokterService = DokterService.getInstance();
    private final JadwalService jadwalService = JadwalService.getInstance();
    private final PendaftaranService pendaftaranService = PendaftaranService.getInstance();
    private final PelayananService pelayananService = PelayananService.getInstance();
    private Dokter dokterSaatIni = null;

    /**
     * Creates new form MainDokter
     */
    public MainDokter() {
        initComponents();
        setMinimumSize(new java.awt.Dimension(1000, 650));
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        gayaTabel(tblPasien);
        gayaTabel(tblRiwayat);
        muatDataAwal();
        refresh();
        tampil("dashboard", btnDashboard);
    }

    private void muatDataAwal() {
        String user = SessionManager.getUsername();
        if (user == null || user.trim().isEmpty()) {
            user = FormLoginDokter.dokterAktif;
        }

        if (user != null && !user.trim().isEmpty()) {
            var optDok = dokterService.getDokterByUsername(user);
            if (optDok.isEmpty()) {
                optDok = dokterService.getDokterByNama(user);
            }
            if (optDok.isEmpty()) {
                optDok = dokterService.getDokterById(user);
            }
            if (optDok.isPresent()) {
                dokterSaatIni = optDok.get();
            }
        }

        if (dokterSaatIni == null) {
            List<Dokter> allDok = dokterService.getAllDokter();
            if (!allDok.isEmpty()) {
                dokterSaatIni = allDok.get(0);
            }
        }

        if (dokterSaatIni != null) {
            txtNama.setText(dokterSaatIni.getNamaLengkap());
            lblNamaDokter.setText(dokterSaatIni.getNamaLengkap());
            txtSpesialis.setText(dokterSaatIni.getSpesialisasi());
            txtTelp.setText(dokterSaatIni.getNoTelepon());
            List<JadwalPraktik> jdw = jadwalService.getJadwalByDokter(dokterSaatIni.getIdDokter());
            if (!jdw.isEmpty()) {
                cbHari.setSelectedItem(jdw.get(0).getHari());
                txtJam.setText(jdw.get(0).getJamMulai() + " - " + jdw.get(0).getJamSelesai());
            }
        } else if (user != null) {
            txtNama.setText(user);
            lblNamaDokter.setText(user);
        }

        mp().setRowCount(0);
        List<Pendaftaran> listP;
        if (dokterSaatIni != null) {
            listP = pendaftaranService.getPendaftaranByDokter(dokterSaatIni.getIdDokter());
            if (listP.isEmpty()) {
                listP = pendaftaranService.getPendaftaranByDokter(dokterSaatIni.getNamaLengkap());
            }
            if (listP.isEmpty()) {
                listP = pendaftaranService.getAllPendaftaran();
            }
        } else {
            listP = pendaftaranService.getAllPendaftaran();
        }

        for (Pendaftaran p : listP) {
            mp().addRow(new Object[]{
                p.getIdPendaftaran(),
                p.getNamaPasien(),
                p.getTanggalKunjungan(),
                p.getKeluhan(),
                p.getStatus() != null ? p.getStatus() : "Menunggu"
            });
        }

        mr().setRowCount(0);
        List<Pelayanan> listPel = pelayananService.getAllPelayanan();
        for (Pelayanan pel : listPel) {
            mr().addRow(new Object[]{
                pel.getIdPendaftaran(),
                pel.getNamaPasien(),
                pel.getTanggalPelayanan(),
                pel.getKeluhan(),
                pel.getDiagnosis(),
                pel.getTindakanHasil()
            });
        }
    }

    public MainDokter(String kartu) {
        this();
        switch (kartu) {
            case "profil" -> tampil("profil", btnProfil);
            case "pasien" -> tampil("pasien", btnPasien);
            case "pemeriksaan" -> {
                tampil("pasien", btnPasien);
                new FormDetailPendaftaran(null, FormDetailPendaftaran.HALAMAN_PEMERIKSAAN).setVisible(true);
            }
            case "riwayat" -> tampil("riwayat", btnRiwayat);
            default -> tampil("dashboard", btnDashboard);
        }
    }
    private void pesan(String s) {
        javax.swing.JOptionPane.showMessageDialog(this, s);
    }

    private javax.swing.table.DefaultTableModel mp() {
        return (javax.swing.table.DefaultTableModel) tblPasien.getModel();
    }

    private javax.swing.table.DefaultTableModel mr() {
        return (javax.swing.table.DefaultTableModel) tblRiwayat.getModel();
    }

    private void gayaTabel(javax.swing.JTable t) {
        t.setRowHeight(28);
        t.setFont(new java.awt.Font("Segoe UI", 0, 13));
        t.setSelectionBackground(ROCK);
        t.setSelectionForeground(VENICE);
        t.getTableHeader().setBackground(VENICE);
        t.getTableHeader().setForeground(java.awt.Color.WHITE);
        t.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 13));
    }

    private void tampil(String kartu, javax.swing.JButton aktif) {
        ((java.awt.CardLayout) pnlContent.getLayout()).show(pnlContent, kartu);
        for (javax.swing.JButton b : new javax.swing.JButton[]{btnDashboard, btnProfil, btnPasien, btnPeriksa, btnRiwayat}) {
            b.setBackground(b == aktif ? ROCK : VENICE);
            b.setForeground(b == aktif ? VENICE : java.awt.Color.WHITE);
        }
    }

    private int baris() {
        int r = tblPasien.getSelectedRow();
        if (r < 0) {
            pesan("Pilih pasien terlebih dahulu.");
        }
        return r;
    }

    private int cariBaris(String no) {
        for (int i = 0; i < mp().getRowCount(); i++) {
            if (no.equals(mp().getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    private int barisPeriksa() {
        Object s = cbPasien.getSelectedItem();
        return s == null ? -1 : cariBaris(s.toString().split(" - ")[0]);
    }

    private void isiKeluhan() {
        int r = barisPeriksa();
        txtKeluhan.setText(r < 0 ? "" : (String) mp().getValueAt(r, 3));
    }

    private void refresh() {
        int menunggu = 0, selesai = 0;
        java.util.List<String> aktif = new java.util.ArrayList<>();
        for (int i = 0; i < mp().getRowCount(); i++) {
            String st = (String) mp().getValueAt(i, 4);
            if (st.equals("Menunggu")) {
                menunggu++;
            } else if (st.equals("Selesai")) {
                selesai++;
            } else if (st.equals("Diterima") || st.equals("Sedang Diperiksa")) {
                aktif.add(mp().getValueAt(i, 0) + " - " + mp().getValueAt(i, 1));
            }
        }
        lblTotalNilai.setText(String.valueOf(mp().getRowCount()));
        lblMenungguNilai.setText(String.valueOf(menunggu));
        lblSelesaiNilai.setText(String.valueOf(selesai));
        Object lama = cbPasien.getSelectedItem();
        loading = true;
        cbPasien.setModel(new javax.swing.DefaultComboBoxModel<>(aktif.toArray(new String[0])));
        if (lama != null && aktif.contains(lama.toString())) {
            cbPasien.setSelectedItem(lama);
            loading = false;
        } else {
            loading = false;
            isiKeluhan();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSidebar = new javax.swing.JPanel();
        pnlSideTop = new javax.swing.JPanel();
        pnlBrand = new javax.swing.JPanel();
        lblApp = new javax.swing.JLabel();
        lblNamaDokter = new javax.swing.JLabel();
        pnlNav = new javax.swing.JPanel();
        btnDashboard = new javax.swing.JButton();
        btnProfil = new javax.swing.JButton();
        btnPasien = new javax.swing.JButton();
        btnPeriksa = new javax.swing.JButton();
        btnRiwayat = new javax.swing.JButton();
        pnlSideBottom = new javax.swing.JPanel();
        btnBeranda = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        pnlContent = new javax.swing.JPanel();
        pnlDashboard = new javax.swing.JPanel();
        pnlDashTop = new javax.swing.JPanel();
        lblJudulDash = new javax.swing.JLabel();
        pnlStat = new javax.swing.JPanel();
        cardTotal = new javax.swing.JPanel();
        lblTotalNilai = new javax.swing.JLabel();
        lblTotalNama = new javax.swing.JLabel();
        cardMenunggu = new javax.swing.JPanel();
        lblMenungguNilai = new javax.swing.JLabel();
        lblMenungguNama = new javax.swing.JLabel();
        cardSelesai = new javax.swing.JPanel();
        lblSelesaiNilai = new javax.swing.JLabel();
        lblSelesaiNama = new javax.swing.JLabel();
        pnlWelcome = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        pnlProfil = new javax.swing.JPanel();
        pnlProfilTop = new javax.swing.JPanel();
        lblJudulProfil = new javax.swing.JLabel();
        pnlProfilForm = new javax.swing.JPanel();
        pnlProfilIsi = new javax.swing.JPanel();
        lblNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        lblSpesialis = new javax.swing.JLabel();
        txtSpesialis = new javax.swing.JTextField();
        lblStr = new javax.swing.JLabel();
        txtStr = new javax.swing.JTextField();
        lblTelp = new javax.swing.JLabel();
        txtTelp = new javax.swing.JTextField();
        lblHari = new javax.swing.JLabel();
        cbHari = new javax.swing.JComboBox<>();
        lblJam = new javax.swing.JLabel();
        txtJam = new javax.swing.JTextField();
        lblKosong = new javax.swing.JLabel();
        btnSimpanProfil = new javax.swing.JButton();
        pnlPasien = new javax.swing.JPanel();
        lblJudulPasien = new javax.swing.JLabel();
        pnlPasienBody = new javax.swing.JPanel();
        spPasien = new javax.swing.JScrollPane();
        tblPasien = new javax.swing.JTable();
        pnlPasienAksi = new javax.swing.JPanel();
        btnDetail = new javax.swing.JButton();
        btnTerima = new javax.swing.JButton();
        btnTolak = new javax.swing.JButton();
        pnlPeriksa = new javax.swing.JPanel();
        lblJudulPeriksa = new javax.swing.JLabel();
        pnlPemBody = new javax.swing.JPanel();
        pnlPilih = new javax.swing.JPanel();
        lblPilih = new javax.swing.JLabel();
        cbPasien = new javax.swing.JComboBox<>();
        pnlInput = new javax.swing.JPanel();
        pnlKeluhan = new javax.swing.JPanel();
        lblKeluhan = new javax.swing.JLabel();
        spKeluhan = new javax.swing.JScrollPane();
        txtKeluhan = new javax.swing.JTextArea();
        pnlDiagnosis = new javax.swing.JPanel();
        lblDiagnosis = new javax.swing.JLabel();
        spDiagnosis = new javax.swing.JScrollPane();
        txtDiagnosis = new javax.swing.JTextArea();
        pnlHasil = new javax.swing.JPanel();
        lblHasil = new javax.swing.JLabel();
        spHasil = new javax.swing.JScrollPane();
        txtHasil = new javax.swing.JTextArea();
        pnlPemAksi = new javax.swing.JPanel();
        btnMulai = new javax.swing.JButton();
        btnSelesai = new javax.swing.JButton();
        pnlRiwayat = new javax.swing.JPanel();
        lblJudulRiwayat = new javax.swing.JLabel();
        spRiwayat = new javax.swing.JScrollPane();
        tblRiwayat = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Portal Dokter");

        pnlSidebar.setBackground(new java.awt.Color(22, 88, 123));
        pnlSidebar.setPreferredSize(new java.awt.Dimension(230, 560));
        pnlSidebar.setLayout(new java.awt.BorderLayout());

        pnlSideTop.setBackground(new java.awt.Color(22, 88, 123));
        pnlSideTop.setLayout(new java.awt.BorderLayout());

        pnlBrand.setBackground(new java.awt.Color(22, 88, 123));
        pnlBrand.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 15, 20));
        pnlBrand.setPreferredSize(new java.awt.Dimension(230, 90));
        pnlBrand.setLayout(new java.awt.GridLayout(0, 1, 0, 2));

        lblApp.setText("Klinik Sehat");
        lblApp.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblApp.setForeground(new java.awt.Color(255, 255, 255));
        pnlBrand.add(lblApp);

        lblNamaDokter.setText("Dokter");
        lblNamaDokter.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNamaDokter.setForeground(new java.awt.Color(132, 179, 206));
        pnlBrand.add(lblNamaDokter);

        pnlSideTop.add(pnlBrand, java.awt.BorderLayout.NORTH);

        pnlNav.setBackground(new java.awt.Color(22, 88, 123));
        pnlNav.setPreferredSize(new java.awt.Dimension(230, 230));
        pnlNav.setLayout(new java.awt.GridLayout(0, 1, 0, 2));

        btnDashboard.setText("Dashboard");
        btnDashboard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDashboard.setBackground(new java.awt.Color(22, 88, 123));
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        btnDashboard.setFocusPainted(false);
        btnDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);
        pnlNav.add(btnDashboard);

        btnProfil.setText("Profil & Jadwal");
        btnProfil.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnProfil.setBackground(new java.awt.Color(22, 88, 123));
        btnProfil.setForeground(new java.awt.Color(255, 255, 255));
        btnProfil.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        btnProfil.setFocusPainted(false);
        btnProfil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProfil.addActionListener(this::btnProfilActionPerformed);
        pnlNav.add(btnProfil);

        btnPasien.setText("Daftar Pasien");
        btnPasien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPasien.setBackground(new java.awt.Color(22, 88, 123));
        btnPasien.setForeground(new java.awt.Color(255, 255, 255));
        btnPasien.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        btnPasien.setFocusPainted(false);
        btnPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPasien.addActionListener(this::btnPasienActionPerformed);
        pnlNav.add(btnPasien);

        btnPeriksa.setText("Pemeriksaan");
        btnPeriksa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPeriksa.setBackground(new java.awt.Color(22, 88, 123));
        btnPeriksa.setForeground(new java.awt.Color(255, 255, 255));
        btnPeriksa.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        btnPeriksa.setFocusPainted(false);
        btnPeriksa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPeriksa.addActionListener(this::btnPeriksaActionPerformed);
        pnlNav.add(btnPeriksa);

        btnRiwayat.setText("Riwayat Pelayanan");
        btnRiwayat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRiwayat.setBackground(new java.awt.Color(22, 88, 123));
        btnRiwayat.setForeground(new java.awt.Color(255, 255, 255));
        btnRiwayat.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        btnRiwayat.setFocusPainted(false);
        btnRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRiwayat.addActionListener(this::btnRiwayatActionPerformed);
        pnlNav.add(btnRiwayat);

        pnlSideTop.add(pnlNav, java.awt.BorderLayout.CENTER);

        pnlSidebar.add(pnlSideTop, java.awt.BorderLayout.NORTH);

        pnlSideBottom.setBackground(new java.awt.Color(22, 88, 123));
        pnlSideBottom.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pnlSideBottom.setPreferredSize(new java.awt.Dimension(230, 105));
        pnlSideBottom.setLayout(new java.awt.GridLayout(0, 1, 0, 2));

        btnBeranda.setText("Menu Utama");
        btnBeranda.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBeranda.setBackground(new java.awt.Color(22, 88, 123));
        btnBeranda.setForeground(new java.awt.Color(255, 255, 255));
        btnBeranda.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        btnBeranda.setFocusPainted(false);
        btnBeranda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBeranda.addActionListener(this::btnBerandaActionPerformed);
        pnlSideBottom.add(btnBeranda);

        btnLogout.setText("Logout");
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogout.setBackground(new java.awt.Color(22, 88, 123));
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        btnLogout.setFocusPainted(false);
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLogout.addActionListener(this::btnLogoutActionPerformed);
        pnlSideBottom.add(btnLogout);

        pnlSidebar.add(pnlSideBottom, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pnlSidebar, java.awt.BorderLayout.WEST);

        pnlContent.setBackground(new java.awt.Color(245, 238, 221));
        pnlContent.setPreferredSize(new java.awt.Dimension(920, 560));
        pnlContent.setLayout(new java.awt.CardLayout());

        pnlDashboard.setBackground(new java.awt.Color(245, 238, 221));
        pnlDashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));
        pnlDashboard.setLayout(new java.awt.BorderLayout());

        pnlDashTop.setBackground(new java.awt.Color(245, 238, 221));
        pnlDashTop.setLayout(new java.awt.BorderLayout());

        lblJudulDash.setText("Dashboard");
        lblJudulDash.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJudulDash.setForeground(new java.awt.Color(22, 88, 123));
        lblJudulDash.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pnlDashTop.add(lblJudulDash, java.awt.BorderLayout.NORTH);

        pnlStat.setBackground(new java.awt.Color(245, 238, 221));
        pnlStat.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 20, 0));
        pnlStat.setPreferredSize(new java.awt.Dimension(0, 140));
        pnlStat.setLayout(new java.awt.GridLayout(1, 3, 20, 0));

        cardTotal.setBackground(new java.awt.Color(255, 255, 255));
        cardTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(132, 179, 206)));
        cardTotal.setLayout(new java.awt.GridLayout(0, 1, 0, 0));

        lblTotalNilai.setText("0");
        lblTotalNilai.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTotalNilai.setForeground(new java.awt.Color(22, 88, 123));
        lblTotalNilai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cardTotal.add(lblTotalNilai);

        lblTotalNama.setText("Total Pendaftaran");
        lblTotalNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTotalNama.setForeground(new java.awt.Color(22, 88, 123));
        lblTotalNama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cardTotal.add(lblTotalNama);

        pnlStat.add(cardTotal);

        cardMenunggu.setBackground(new java.awt.Color(255, 255, 255));
        cardMenunggu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(132, 179, 206)));
        cardMenunggu.setLayout(new java.awt.GridLayout(0, 1, 0, 0));

        lblMenungguNilai.setText("0");
        lblMenungguNilai.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblMenungguNilai.setForeground(new java.awt.Color(22, 88, 123));
        lblMenungguNilai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cardMenunggu.add(lblMenungguNilai);

        lblMenungguNama.setText("Menunggu Konfirmasi");
        lblMenungguNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMenungguNama.setForeground(new java.awt.Color(22, 88, 123));
        lblMenungguNama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cardMenunggu.add(lblMenungguNama);

        pnlStat.add(cardMenunggu);

        cardSelesai.setBackground(new java.awt.Color(255, 255, 255));
        cardSelesai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(132, 179, 206)));
        cardSelesai.setLayout(new java.awt.GridLayout(0, 1, 0, 0));

        lblSelesaiNilai.setText("0");
        lblSelesaiNilai.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblSelesaiNilai.setForeground(new java.awt.Color(22, 88, 123));
        lblSelesaiNilai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cardSelesai.add(lblSelesaiNilai);

        lblSelesaiNama.setText("Pemeriksaan Selesai");
        lblSelesaiNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSelesaiNama.setForeground(new java.awt.Color(22, 88, 123));
        lblSelesaiNama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cardSelesai.add(lblSelesaiNama);

        pnlStat.add(cardSelesai);

        pnlDashTop.add(pnlStat, java.awt.BorderLayout.CENTER);

        pnlDashboard.add(pnlDashTop, java.awt.BorderLayout.NORTH);

        pnlWelcome.setBackground(new java.awt.Color(255, 255, 255));
        pnlWelcome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(132, 179, 206)));
        pnlWelcome.setLayout(new java.awt.BorderLayout());

        lblWelcome.setText("<html>Selamat datang di Portal Dokter.<br><br>Gunakan menu di sebelah kiri untuk mengelola profil dan jadwal praktik, memproses pendaftaran pasien, melakukan pemeriksaan, dan melihat riwayat pelayanan.");
        lblWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(22, 88, 123));
        lblWelcome.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        lblWelcome.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pnlWelcome.add(lblWelcome, java.awt.BorderLayout.CENTER);

        pnlDashboard.add(pnlWelcome, java.awt.BorderLayout.CENTER);

        pnlContent.add(pnlDashboard, "dashboard");

        pnlProfil.setBackground(new java.awt.Color(245, 238, 221));
        pnlProfil.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));
        pnlProfil.setLayout(new java.awt.BorderLayout());

        pnlProfilTop.setBackground(new java.awt.Color(245, 238, 221));
        pnlProfilTop.setLayout(new java.awt.BorderLayout());

        lblJudulProfil.setText("Profil & Jadwal Praktik");
        lblJudulProfil.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJudulProfil.setForeground(new java.awt.Color(22, 88, 123));
        lblJudulProfil.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pnlProfilTop.add(lblJudulProfil, java.awt.BorderLayout.NORTH);

        pnlProfilForm.setBackground(new java.awt.Color(255, 255, 255));
        pnlProfilForm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(132, 179, 206)));
        pnlProfilForm.setPreferredSize(new java.awt.Dimension(0, 340));
        pnlProfilForm.setLayout(new java.awt.BorderLayout());

        pnlProfilIsi.setBackground(new java.awt.Color(255, 255, 255));
        pnlProfilIsi.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnlProfilIsi.setLayout(new java.awt.GridLayout(0, 2, 15, 12));

        lblNama.setText("Nama Lengkap");
        lblNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNama.setForeground(new java.awt.Color(22, 88, 123));
        pnlProfilIsi.add(lblNama);

        txtNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNama.setText("dr. Andi Pratama, Sp.PD");
        pnlProfilIsi.add(txtNama);

        lblSpesialis.setText("Spesialisasi");
        lblSpesialis.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSpesialis.setForeground(new java.awt.Color(22, 88, 123));
        pnlProfilIsi.add(lblSpesialis);

        txtSpesialis.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSpesialis.setText("Penyakit Dalam");
        pnlProfilIsi.add(txtSpesialis);

        lblStr.setText("No. STR");
        lblStr.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStr.setForeground(new java.awt.Color(22, 88, 123));
        pnlProfilIsi.add(lblStr);

        txtStr.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtStr.setText("STR-0012345");
        pnlProfilIsi.add(txtStr);

        lblTelp.setText("No. Telepon");
        lblTelp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTelp.setForeground(new java.awt.Color(22, 88, 123));
        pnlProfilIsi.add(lblTelp);

        txtTelp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTelp.setText("08123456789");
        pnlProfilIsi.add(txtTelp);

        lblHari.setText("Hari Praktik");
        lblHari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHari.setForeground(new java.awt.Color(22, 88, 123));
        pnlProfilIsi.add(lblHari);

        cbHari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbHari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Senin - Jumat", "Senin - Sabtu", "Selasa, Kamis, Sabtu" }));
        pnlProfilIsi.add(cbHari);

        lblJam.setText("Jam Praktik");
        lblJam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblJam.setForeground(new java.awt.Color(22, 88, 123));
        pnlProfilIsi.add(lblJam);

        txtJam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtJam.setText("08.00 - 14.00");
        pnlProfilIsi.add(txtJam);

        lblKosong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKosong.setForeground(new java.awt.Color(22, 88, 123));
        pnlProfilIsi.add(lblKosong);

        btnSimpanProfil.setText("Simpan Perubahan");
        btnSimpanProfil.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSimpanProfil.setBackground(new java.awt.Color(22, 88, 123));
        btnSimpanProfil.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanProfil.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        btnSimpanProfil.setFocusPainted(false);
        btnSimpanProfil.addActionListener(this::btnSimpanProfilActionPerformed);
        pnlProfilIsi.add(btnSimpanProfil);

        pnlProfilForm.add(pnlProfilIsi, java.awt.BorderLayout.CENTER);

        pnlProfilTop.add(pnlProfilForm, java.awt.BorderLayout.CENTER);

        pnlProfil.add(pnlProfilTop, java.awt.BorderLayout.NORTH);

        pnlContent.add(pnlProfil, "profil");

        pnlPasien.setBackground(new java.awt.Color(245, 238, 221));
        pnlPasien.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));
        pnlPasien.setLayout(new java.awt.BorderLayout());

        lblJudulPasien.setText("Daftar Pasien Terdaftar");
        lblJudulPasien.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJudulPasien.setForeground(new java.awt.Color(22, 88, 123));
        lblJudulPasien.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pnlPasien.add(lblJudulPasien, java.awt.BorderLayout.NORTH);

        pnlPasienBody.setBackground(new java.awt.Color(245, 238, 221));
        pnlPasienBody.setLayout(new java.awt.BorderLayout());

        tblPasien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Daftar", "Nama Pasien", "Tanggal", "Keluhan Awal", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spPasien.setViewportView(tblPasien);

        pnlPasienBody.add(spPasien, java.awt.BorderLayout.CENTER);

        pnlPasienAksi.setBackground(new java.awt.Color(245, 238, 221));
        pnlPasienAksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        pnlPasienAksi.setPreferredSize(new java.awt.Dimension(0, 50));
        pnlPasienAksi.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        btnDetail.setText("Lihat Detail");
        btnDetail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDetail.setBackground(new java.awt.Color(132, 179, 206));
        btnDetail.setForeground(new java.awt.Color(22, 88, 123));
        btnDetail.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        btnDetail.setFocusPainted(false);
        btnDetail.addActionListener(this::btnDetailActionPerformed);
        pnlPasienAksi.add(btnDetail);

        btnTerima.setText("Terima");
        btnTerima.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTerima.setBackground(new java.awt.Color(22, 88, 123));
        btnTerima.setForeground(new java.awt.Color(255, 255, 255));
        btnTerima.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        btnTerima.setFocusPainted(false);
        btnTerima.addActionListener(this::btnTerimaActionPerformed);
        pnlPasienAksi.add(btnTerima);

        btnTolak.setText("Tolak");
        btnTolak.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTolak.setForeground(new java.awt.Color(22, 88, 123));
        btnTolak.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(132, 179, 206)));
        btnTolak.setFocusPainted(false);
        btnTolak.addActionListener(this::btnTolakActionPerformed);
        pnlPasienAksi.add(btnTolak);

        pnlPasienBody.add(pnlPasienAksi, java.awt.BorderLayout.SOUTH);

        pnlPasien.add(pnlPasienBody, java.awt.BorderLayout.CENTER);

        pnlContent.add(pnlPasien, "pasien");

        pnlPeriksa.setBackground(new java.awt.Color(245, 238, 221));
        pnlPeriksa.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));
        pnlPeriksa.setLayout(new java.awt.BorderLayout());

        lblJudulPeriksa.setText("Pemeriksaan Pasien");
        lblJudulPeriksa.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJudulPeriksa.setForeground(new java.awt.Color(22, 88, 123));
        lblJudulPeriksa.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pnlPeriksa.add(lblJudulPeriksa, java.awt.BorderLayout.NORTH);

        pnlPemBody.setBackground(new java.awt.Color(245, 238, 221));
        pnlPemBody.setLayout(new java.awt.BorderLayout());

        pnlPilih.setBackground(new java.awt.Color(245, 238, 221));
        pnlPilih.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        pnlPilih.setPreferredSize(new java.awt.Dimension(0, 46));
        pnlPilih.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        lblPilih.setText("Pilih Pasien (Diterima / Sedang Diperiksa)");
        lblPilih.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPilih.setForeground(new java.awt.Color(22, 88, 123));
        pnlPilih.add(lblPilih);

        cbPasien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbPasien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        cbPasien.addActionListener(this::cbPasienActionPerformed);
        pnlPilih.add(cbPasien);

        pnlPemBody.add(pnlPilih, java.awt.BorderLayout.NORTH);

        pnlInput.setBackground(new java.awt.Color(245, 238, 221));
        pnlInput.setLayout(new java.awt.GridLayout(1, 3, 12, 0));

        pnlKeluhan.setBackground(new java.awt.Color(245, 238, 221));
        pnlKeluhan.setLayout(new java.awt.BorderLayout());

        lblKeluhan.setText("Keluhan");
        lblKeluhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKeluhan.setForeground(new java.awt.Color(22, 88, 123));
        lblKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0));
        pnlKeluhan.add(lblKeluhan, java.awt.BorderLayout.NORTH);

        txtKeluhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKeluhan.setColumns(20);
        txtKeluhan.setLineWrap(true);
        txtKeluhan.setRows(5);
        txtKeluhan.setWrapStyleWord(true);
        spKeluhan.setViewportView(txtKeluhan);

        pnlKeluhan.add(spKeluhan, java.awt.BorderLayout.CENTER);

        pnlInput.add(pnlKeluhan);

        pnlDiagnosis.setBackground(new java.awt.Color(245, 238, 221));
        pnlDiagnosis.setLayout(new java.awt.BorderLayout());

        lblDiagnosis.setText("Diagnosis");
        lblDiagnosis.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDiagnosis.setForeground(new java.awt.Color(22, 88, 123));
        lblDiagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0));
        pnlDiagnosis.add(lblDiagnosis, java.awt.BorderLayout.NORTH);

        txtDiagnosis.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiagnosis.setColumns(20);
        txtDiagnosis.setLineWrap(true);
        txtDiagnosis.setRows(5);
        txtDiagnosis.setWrapStyleWord(true);
        spDiagnosis.setViewportView(txtDiagnosis);

        pnlDiagnosis.add(spDiagnosis, java.awt.BorderLayout.CENTER);

        pnlInput.add(pnlDiagnosis);

        pnlHasil.setBackground(new java.awt.Color(245, 238, 221));
        pnlHasil.setLayout(new java.awt.BorderLayout());

        lblHasil.setText("Hasil Pemeriksaan");
        lblHasil.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHasil.setForeground(new java.awt.Color(22, 88, 123));
        lblHasil.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0));
        pnlHasil.add(lblHasil, java.awt.BorderLayout.NORTH);

        txtHasil.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtHasil.setColumns(20);
        txtHasil.setLineWrap(true);
        txtHasil.setRows(5);
        txtHasil.setWrapStyleWord(true);
        spHasil.setViewportView(txtHasil);

        pnlHasil.add(spHasil, java.awt.BorderLayout.CENTER);

        pnlInput.add(pnlHasil);

        pnlPemBody.add(pnlInput, java.awt.BorderLayout.CENTER);

        pnlPemAksi.setBackground(new java.awt.Color(245, 238, 221));
        pnlPemAksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        pnlPemAksi.setPreferredSize(new java.awt.Dimension(0, 50));
        pnlPemAksi.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        btnMulai.setText("Mulai Periksa");
        btnMulai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMulai.setBackground(new java.awt.Color(132, 179, 206));
        btnMulai.setForeground(new java.awt.Color(22, 88, 123));
        btnMulai.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        btnMulai.setFocusPainted(false);
        btnMulai.addActionListener(this::btnMulaiActionPerformed);
        pnlPemAksi.add(btnMulai);

        btnSelesai.setText("Selesai & Simpan");
        btnSelesai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSelesai.setBackground(new java.awt.Color(22, 88, 123));
        btnSelesai.setForeground(new java.awt.Color(255, 255, 255));
        btnSelesai.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        btnSelesai.setFocusPainted(false);
        btnSelesai.addActionListener(this::btnSelesaiActionPerformed);
        pnlPemAksi.add(btnSelesai);

        pnlPemBody.add(pnlPemAksi, java.awt.BorderLayout.SOUTH);

        pnlPeriksa.add(pnlPemBody, java.awt.BorderLayout.CENTER);

        pnlContent.add(pnlPeriksa, "pemeriksaan");

        pnlRiwayat.setBackground(new java.awt.Color(245, 238, 221));
        pnlRiwayat.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));
        pnlRiwayat.setLayout(new java.awt.BorderLayout());

        lblJudulRiwayat.setText("Riwayat Pelayanan");
        lblJudulRiwayat.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJudulRiwayat.setForeground(new java.awt.Color(22, 88, 123));
        lblJudulRiwayat.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pnlRiwayat.add(lblJudulRiwayat, java.awt.BorderLayout.NORTH);

        tblRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Daftar", "Nama Pasien", "Tanggal", "Keluhan", "Diagnosis", "Hasil Pemeriksaan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spRiwayat.setViewportView(tblRiwayat);

        pnlRiwayat.add(spRiwayat, java.awt.BorderLayout.CENTER);

        pnlContent.add(pnlRiwayat, "riwayat");

        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        tampil("dashboard", btnDashboard);
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnProfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfilActionPerformed
        tampil("profil", btnProfil);
    }//GEN-LAST:event_btnProfilActionPerformed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        tampil("pasien", btnPasien);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeriksaActionPerformed
        int r = tblPasien.getSelectedRow();
        String idReg = (r >= 0) ? String.valueOf(mp().getValueAt(r, 0)) : null;
        new FormDetailPendaftaran(idReg, FormDetailPendaftaran.HALAMAN_PEMERIKSAAN).setVisible(true);
    }//GEN-LAST:event_btnPeriksaActionPerformed

    private void btnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatActionPerformed
        tampil("riwayat", btnRiwayat);
    }//GEN-LAST:event_btnRiwayatActionPerformed

    private void btnBerandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBerandaActionPerformed
        new FormMenuDokter().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBerandaActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        if (javax.swing.JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION) {
            FormLoginDokter.dokterAktif = null;
            sistempendaftarandanpelayananpasien.auth.SessionManager.logout();
            new FormLoginDokter().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnSimpanProfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanProfilActionPerformed
        String nama = txtNama.getText().trim();
        String jam = txtJam.getText().trim();
        if (nama.isEmpty() || jam.isEmpty()) {
            pesan("Nama dan jam praktik wajib diisi.");
            return;
        }
        lblNamaDokter.setText(nama);
        if (dokterSaatIni != null) {
            dokterSaatIni.setNamaLengkap(nama);
            dokterSaatIni.setSpesialisasi(txtSpesialis.getText().trim());
            dokterSaatIni.setNoTelepon(txtTelp.getText().trim());
            dokterService.perbaruiProfil(dokterSaatIni);

            String hari = cbHari.getSelectedItem() != null ? cbHari.getSelectedItem().toString() : "Senin";
            String[] splitJam = jam.split("-");
            String mulai = splitJam.length > 0 ? splitJam[0].trim() : "08:00";
            String selesai = splitJam.length > 1 ? splitJam[1].trim() : "12:00";
            jadwalService.tambahJadwal(dokterSaatIni.getIdDokter(), nama, hari, mulai, selesai, 20);
        }
        pesan("Profil dan jadwal praktik berhasil disimpan.");
    }//GEN-LAST:event_btnSimpanProfilActionPerformed

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        int r = tblPasien.getSelectedRow();
        String idReg = (r >= 0) ? String.valueOf(mp().getValueAt(r, 0)) : null;
        new FormDetailPendaftaran(idReg, FormDetailPendaftaran.HALAMAN_DETAIL).setVisible(true);
    }//GEN-LAST:event_btnDetailActionPerformed

    private void btnTerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerimaActionPerformed
        int r = baris();
        if (r < 0) {
            return;
        }
        if (!"Menunggu".equalsIgnoreCase(String.valueOf(mp().getValueAt(r, 4)))) {
            pesan("Hanya pendaftaran berstatus Menunggu yang dapat diproses.");
            return;
        }
        String idReg = String.valueOf(mp().getValueAt(r, 0));
        pendaftaranService.terimaPendaftaran(idReg);
        mp().setValueAt("Diterima", r, 4);
        refresh();
    }//GEN-LAST:event_btnTerimaActionPerformed

    private void btnTolakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTolakActionPerformed
        int r = baris();
        if (r < 0) {
            return;
        }
        if (!"Menunggu".equalsIgnoreCase(String.valueOf(mp().getValueAt(r, 4)))) {
            pesan("Hanya pendaftaran berstatus Menunggu yang dapat diproses.");
            return;
        }
        if (javax.swing.JOptionPane.showConfirmDialog(this, "Tolak pendaftaran ini?", "Konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION) {
            String idReg = String.valueOf(mp().getValueAt(r, 0));
            pendaftaranService.tolakPendaftaran(idReg, "Ditolak oleh dokter");
            mp().setValueAt("Ditolak", r, 4);
            refresh();
        }
    }//GEN-LAST:event_btnTolakActionPerformed

    private void cbPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPasienActionPerformed
        if (!loading) {
            isiKeluhan();
        }
    }//GEN-LAST:event_cbPasienActionPerformed

    private void btnMulaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMulaiActionPerformed
        int r = barisPeriksa();
        if (r < 0) {
            pesan("Tidak ada pasien yang dipilih.");
            return;
        }
        if (!"Diterima".equalsIgnoreCase(String.valueOf(mp().getValueAt(r, 4)))) {
            pesan("Pemeriksaan hanya dapat dimulai untuk pasien berstatus Diterima.");
            return;
        }
        String idReg = String.valueOf(mp().getValueAt(r, 0));
        pendaftaranService.ubahStatus(idReg, "Sedang Diperiksa");
        mp().setValueAt("Sedang Diperiksa", r, 4);
        refresh();
    }//GEN-LAST:event_btnMulaiActionPerformed

    private void btnSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelesaiActionPerformed
        int r = barisPeriksa();
        if (r < 0) {
            pesan("Tidak ada pasien yang dipilih.");
            return;
        }
        if (!"Sedang Diperiksa".equalsIgnoreCase(String.valueOf(mp().getValueAt(r, 4)))) {
            pesan("Ubah status menjadi Sedang Diperiksa terlebih dahulu.");
            return;
        }
        String keluhan = txtKeluhan.getText().trim(), dx = txtDiagnosis.getText().trim(), hasil = txtHasil.getText().trim();
        if (dx.isEmpty() || hasil.isEmpty()) {
            pesan("Diagnosis dan hasil pemeriksaan wajib diisi.");
            return;
        }
        String idReg = String.valueOf(mp().getValueAt(r, 0));
        String namaPas = String.valueOf(mp().getValueAt(r, 1));
        var optP = pendaftaranService.getPendaftaranById(idReg);
        String idPas = optP.isPresent() ? optP.get().getIdPasien() : "PAS001";
        String idDok = dokterSaatIni != null ? dokterSaatIni.getIdDokter() : "DOK001";
        String namaDok = dokterSaatIni != null ? dokterSaatIni.getNamaLengkap() : lblNamaDokter.getText();

        pendaftaranService.ubahStatus(idReg, "Selesai");
        pelayananService.simpanHasilPemeriksaan(idReg, idPas, namaPas, idDok, namaDok, dx, keluhan, hasil, "-", "-");

        mp().setValueAt(keluhan, r, 3);
        mr().addRow(new Object[]{idReg, namaPas, String.valueOf(mp().getValueAt(r, 2)), keluhan, dx, hasil});
        mp().setValueAt("Selesai", r, 4);
        txtDiagnosis.setText("");
        txtHasil.setText("");
        refresh();
        pesan("Pemeriksaan selesai dan masuk ke riwayat pelayanan.");
    }//GEN-LAST:event_btnSelesaiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Look and Feel: Metal (default), no changes needed. */
        java.awt.EventQueue.invokeLater(() -> {
            if (FormLoginDokter.dokterAktif != null) {
                new MainDokter().setVisible(true);
            } else {
                new FormLoginDokter().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBeranda;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMulai;
    private javax.swing.JButton btnPasien;
    private javax.swing.JButton btnPeriksa;
    private javax.swing.JButton btnProfil;
    private javax.swing.JButton btnRiwayat;
    private javax.swing.JButton btnSelesai;
    private javax.swing.JButton btnSimpanProfil;
    private javax.swing.JButton btnTerima;
    private javax.swing.JButton btnTolak;
    private javax.swing.JPanel cardMenunggu;
    private javax.swing.JPanel cardSelesai;
    private javax.swing.JPanel cardTotal;
    private javax.swing.JComboBox<String> cbHari;
    private javax.swing.JComboBox<String> cbPasien;
    private javax.swing.JLabel lblApp;
    private javax.swing.JLabel lblDiagnosis;
    private javax.swing.JLabel lblHari;
    private javax.swing.JLabel lblHasil;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblJudulDash;
    private javax.swing.JLabel lblJudulPasien;
    private javax.swing.JLabel lblJudulPeriksa;
    private javax.swing.JLabel lblJudulProfil;
    private javax.swing.JLabel lblJudulRiwayat;
    private javax.swing.JLabel lblKeluhan;
    private javax.swing.JLabel lblKosong;
    private javax.swing.JLabel lblMenungguNama;
    private javax.swing.JLabel lblMenungguNilai;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNamaDokter;
    private javax.swing.JLabel lblPilih;
    private javax.swing.JLabel lblSelesaiNama;
    private javax.swing.JLabel lblSelesaiNilai;
    private javax.swing.JLabel lblSpesialis;
    private javax.swing.JLabel lblStr;
    private javax.swing.JLabel lblTelp;
    private javax.swing.JLabel lblTotalNama;
    private javax.swing.JLabel lblTotalNilai;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel pnlBrand;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlDashTop;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlDiagnosis;
    private javax.swing.JPanel pnlHasil;
    private javax.swing.JPanel pnlInput;
    private javax.swing.JPanel pnlKeluhan;
    private javax.swing.JPanel pnlNav;
    private javax.swing.JPanel pnlPasien;
    private javax.swing.JPanel pnlPasienAksi;
    private javax.swing.JPanel pnlPasienBody;
    private javax.swing.JPanel pnlPemAksi;
    private javax.swing.JPanel pnlPemBody;
    private javax.swing.JPanel pnlPeriksa;
    private javax.swing.JPanel pnlPilih;
    private javax.swing.JPanel pnlProfil;
    private javax.swing.JPanel pnlProfilForm;
    private javax.swing.JPanel pnlProfilIsi;
    private javax.swing.JPanel pnlProfilTop;
    private javax.swing.JPanel pnlRiwayat;
    private javax.swing.JPanel pnlSideBottom;
    private javax.swing.JPanel pnlSideTop;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlStat;
    private javax.swing.JPanel pnlWelcome;
    private javax.swing.JScrollPane spDiagnosis;
    private javax.swing.JScrollPane spHasil;
    private javax.swing.JScrollPane spKeluhan;
    private javax.swing.JScrollPane spPasien;
    private javax.swing.JScrollPane spRiwayat;
    private javax.swing.JTable tblPasien;
    private javax.swing.JTable tblRiwayat;
    private javax.swing.JTextArea txtDiagnosis;
    private javax.swing.JTextArea txtHasil;
    private javax.swing.JTextField txtJam;
    private javax.swing.JTextArea txtKeluhan;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSpesialis;
    private javax.swing.JTextField txtStr;
    private javax.swing.JTextField txtTelp;
    // End of variables declaration//GEN-END:variables
}
