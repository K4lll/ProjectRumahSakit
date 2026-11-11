package sistempendaftarandanpelayananpasien.pasien;

import java.awt.Color;

public class FormProfilPasien extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FormProfilPasien.class.getName());

    /** Border kartu bersudut membulat (dipakai lewat custom code di Design). */
    private static class BorderBulat extends javax.swing.border.AbstractBorder {
        private final Color warna;
        private final int radius;
        private final int pad;

        public BorderBulat(Color warna, int radius, int pad) {
            this.warna = warna;
            this.radius = radius;
            this.pad = pad;
        }

        @Override
        public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int w, int h) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            java.awt.geom.RoundRectangle2D.Float bentuk =
                    new java.awt.geom.RoundRectangle2D.Float(x, y, w - 1, h - 1, radius, radius);
            java.awt.geom.Area sudut = new java.awt.geom.Area(new java.awt.Rectangle(x, y, w, h));
            sudut.subtract(new java.awt.geom.Area(bentuk));
            java.awt.Container induk = c.getParent();
            g2.setColor(induk != null ? induk.getBackground() : Color.WHITE);
            g2.fill(sudut);
            g2.setColor(warna);
            g2.draw(bentuk);
            g2.dispose();
        }

        @Override
        public java.awt.Insets getBorderInsets(java.awt.Component c) {
            return new java.awt.Insets(pad, pad, pad, pad);
        }

        @Override
        public java.awt.Insets getBorderInsets(java.awt.Component c, java.awt.Insets insets) {
            insets.set(pad, pad, pad, pad);
            return insets;
        }
    }

    public FormProfilPasien() {
        initComponents();
        bulatkanKartu();
        aturKursor();
        setLocationRelativeTo(null);
    }

    /** Memasang border bulat saat runtime (di Design kartu tampil dengan padding saja). */
    private void bulatkanKartu() {
        javax.swing.JPanel[] kartu = { panelForm };
        for (javax.swing.JPanel p : kartu) {
            int pad = p.getBorder().getBorderInsets(p).top;
            p.setBorder(new BorderBulat(new Color(132, 179, 206), 20, pad));
        }
    }

    private void aturKursor() {
        javax.swing.JButton[] tombol = { btnProfil, btnDaftarDokter, btnJadwal, btnSimpan, btnReset };
        for (javax.swing.JButton b : tombol) {
            b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelUtama = new javax.swing.JPanel();
        panelSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblSubLogo = new javax.swing.JLabel();
        btnProfil = new javax.swing.JButton();
        btnDaftarDokter = new javax.swing.JButton();
        btnJadwal = new javax.swing.JButton();
        panelKonten = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        lblDeskripsi = new javax.swing.JLabel();
        panelIsi = new javax.swing.JPanel();
        panelForm = new javax.swing.JPanel();
        lblNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        lblNik = new javax.swing.JLabel();
        txtNik = new javax.swing.JTextField();
        lblTglLahir = new javax.swing.JLabel();
        txtTglLahir = new javax.swing.JTextField();
        lblJk = new javax.swing.JLabel();
        cmbJk = new javax.swing.JComboBox<>();
        lblHp = new javax.swing.JLabel();
        txtHp = new javax.swing.JTextField();
        lblGolDarah = new javax.swing.JLabel();
        cmbGolDarah = new javax.swing.JComboBox<>();
        lblAlamat = new javax.swing.JLabel();
        scrollAlamat = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        chkSetuju = new javax.swing.JCheckBox();
        btnSimpan = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Profil Pasien");

        panelUtama.setBackground(new java.awt.Color(245, 238, 221));
        panelUtama.setLayout(new java.awt.BorderLayout());

        panelSidebar.setBackground(new java.awt.Color(22, 88, 123));
        panelSidebar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panelSidebar.setPreferredSize(new java.awt.Dimension(220, 600));

        lblLogo.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setText("HOSPITAL CARE");

        lblSubLogo.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblSubLogo.setForeground(new java.awt.Color(132, 179, 206));
        lblSubLogo.setText("Sistem Pelayanan Pasien");

        btnProfil.setBackground(new java.awt.Color(132, 179, 206));
        btnProfil.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnProfil.setForeground(new java.awt.Color(22, 88, 123));
        btnProfil.setText("Profil Pasien");
        btnProfil.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 10));
        btnProfil.setBorderPainted(false);
        btnProfil.setFocusPainted(false);
        btnProfil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProfil.addActionListener(this::btnProfilActionPerformed);

        btnDaftarDokter.setBackground(new java.awt.Color(22, 88, 123));
        btnDaftarDokter.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnDaftarDokter.setForeground(new java.awt.Color(255, 255, 255));
        btnDaftarDokter.setText("Daftar Dokter");
        btnDaftarDokter.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 10));
        btnDaftarDokter.setBorderPainted(false);
        btnDaftarDokter.setFocusPainted(false);
        btnDaftarDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDaftarDokter.addActionListener(this::btnDaftarDokterActionPerformed);

        btnJadwal.setBackground(new java.awt.Color(22, 88, 123));
        btnJadwal.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnJadwal.setForeground(new java.awt.Color(255, 255, 255));
        btnJadwal.setText("Jadwal Dokter");
        btnJadwal.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 10));
        btnJadwal.setBorderPainted(false);
        btnJadwal.setFocusPainted(false);
        btnJadwal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnJadwal.addActionListener(this::btnJadwalActionPerformed);

        javax.swing.GroupLayout panelSidebarLayout = new javax.swing.GroupLayout(panelSidebar);
        panelSidebar.setLayout(panelSidebarLayout);
        panelSidebarLayout.setHorizontalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo)
                    .addComponent(lblSubLogo)
                    .addComponent(btnProfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDaftarDokter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnJadwal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        panelSidebarLayout.setVerticalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblLogo)
                .addGap(5, 5, 5)
                .addComponent(lblSubLogo)
                .addGap(45, 45, 45)
                .addComponent(btnProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnDaftarDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelUtama.add(panelSidebar, java.awt.BorderLayout.WEST);

        panelKonten.setBackground(new java.awt.Color(245, 238, 221));
        panelKonten.setLayout(new java.awt.BorderLayout());

        panelHeader.setBackground(new java.awt.Color(255, 255, 255));
        panelHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 30, 20, 30));

        lblJudul.setFont(new java.awt.Font("SansSerif", 1, 25)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(22, 88, 123));
        lblJudul.setText("Profil Pasien");

        lblDeskripsi.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblDeskripsi.setForeground(new java.awt.Color(128, 128, 128));
        lblDeskripsi.setText("Lengkapi dan perbarui data diri pasien");

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblJudul)
            .addComponent(lblDeskripsi)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addComponent(lblJudul)
                .addGap(7, 7, 7)
                .addComponent(lblDeskripsi))
        );

        panelKonten.add(panelHeader, java.awt.BorderLayout.NORTH);

        panelIsi.setBackground(new java.awt.Color(245, 238, 221));
        panelIsi.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 30, 25, 30));
        panelIsi.setLayout(new java.awt.BorderLayout());

        panelForm.setBackground(new java.awt.Color(255, 255, 255));
        panelForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));

        lblNama.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblNama.setForeground(new java.awt.Color(22, 88, 123));
        lblNama.setText("Nama Lengkap");

        txtNama.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N

        lblNik.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblNik.setForeground(new java.awt.Color(22, 88, 123));
        lblNik.setText("NIK");

        txtNik.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N

        lblTglLahir.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTglLahir.setForeground(new java.awt.Color(22, 88, 123));
        lblTglLahir.setText("Tanggal Lahir (dd-mm-yyyy)");

        txtTglLahir.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N

        lblJk.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblJk.setForeground(new java.awt.Color(22, 88, 123));
        lblJk.setText("Jenis Kelamin");

        cmbJk.setBackground(new java.awt.Color(255, 255, 255));
        cmbJk.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        cmbJk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Laki-laki", "Perempuan" }));

        lblHp.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblHp.setForeground(new java.awt.Color(22, 88, 123));
        lblHp.setText("No. HP");

        txtHp.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N

        lblGolDarah.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblGolDarah.setForeground(new java.awt.Color(22, 88, 123));
        lblGolDarah.setText("Golongan Darah");

        cmbGolDarah.setBackground(new java.awt.Color(255, 255, 255));
        cmbGolDarah.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        cmbGolDarah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "A", "B", "AB", "O" }));

        lblAlamat.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblAlamat.setForeground(new java.awt.Color(22, 88, 123));
        lblAlamat.setText("Alamat");

        txtAlamat.setColumns(20);
        txtAlamat.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        txtAlamat.setLineWrap(true);
        txtAlamat.setRows(4);
        txtAlamat.setWrapStyleWord(true);
        scrollAlamat.setViewportView(txtAlamat);

        chkSetuju.setBackground(new java.awt.Color(255, 255, 255));
        chkSetuju.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        chkSetuju.setForeground(new java.awt.Color(22, 88, 123));
        chkSetuju.setText("Saya menyatakan data yang diisi sudah benar");
        chkSetuju.setFocusPainted(false);

        btnSimpan.setBackground(new java.awt.Color(22, 88, 123));
        btnSimpan.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.setFocusPainted(false);
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);

        btnReset.setBackground(new java.awt.Color(132, 179, 206));
        btnReset.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnReset.setForeground(new java.awt.Color(22, 88, 123));
        btnReset.setText("Reset");
        btnReset.setFocusPainted(false);
        btnReset.addActionListener(this::btnResetActionPerformed);

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNama)
                    .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(lblTglLahir)
                    .addComponent(txtTglLahir, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(lblHp)
                    .addComponent(txtHp, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNik)
                    .addComponent(txtNik, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(lblJk)
                    .addComponent(cmbJk, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(lblGolDarah)
                    .addComponent(cmbGolDarah, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
            .addComponent(lblAlamat)
            .addComponent(scrollAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
            .addComponent(chkSetuju)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblNama).addComponent(lblNik))
                .addGap(5, 5, 5)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtNik, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblTglLahir).addComponent(lblJk))
                .addGap(5, 5, 5)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(txtTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cmbJk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblHp).addComponent(lblGolDarah))
                .addGap(5, 5, 5)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(txtHp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cmbGolDarah, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(lblAlamat)
                .addGap(5, 5, 5)
                .addComponent(scrollAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(chkSetuju)
                .addGap(20, 20, 20)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelIsi.add(panelForm, java.awt.BorderLayout.CENTER);

        panelKonten.add(panelIsi, java.awt.BorderLayout.CENTER);

        panelUtama.add(panelKonten, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfilActionPerformed
        // halaman ini sedang aktif
    }//GEN-LAST:event_btnProfilActionPerformed

    private void btnDaftarDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarDokterActionPerformed
        new FormDaftarDokter().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnDaftarDokterActionPerformed

    private void btnJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJadwalActionPerformed
        new FormJadwalDokter().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnJadwalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (txtNama.getText().trim().isEmpty() || txtNik.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Nama lengkap dan NIK wajib diisi.",
                    "Validasi", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!chkSetuju.isSelected()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Centang pernyataan kebenaran data terlebih dahulu.",
                    "Validasi", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        javax.swing.JOptionPane.showMessageDialog(this, "Profil pasien berhasil disimpan.",
                "Berhasil", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtNama.setText("");
        txtNik.setText("");
        txtTglLahir.setText("");
        txtHp.setText("");
        txtAlamat.setText("");
        cmbJk.setSelectedIndex(0);
        cmbGolDarah.setSelectedIndex(0);
        chkSetuju.setSelected(false);
    }//GEN-LAST:event_btnResetActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FormProfilPasien().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDaftarDokter;
    private javax.swing.JButton btnJadwal;
    private javax.swing.JButton btnProfil;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JCheckBox chkSetuju;
    private javax.swing.JComboBox<String> cmbGolDarah;
    private javax.swing.JComboBox<String> cmbJk;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblDeskripsi;
    private javax.swing.JLabel lblGolDarah;
    private javax.swing.JLabel lblHp;
    private javax.swing.JLabel lblJk;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNik;
    private javax.swing.JLabel lblSubLogo;
    private javax.swing.JLabel lblTglLahir;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelIsi;
    private javax.swing.JPanel panelKonten;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JScrollPane scrollAlamat;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtHp;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNik;
    private javax.swing.JTextField txtTglLahir;
    // End of variables declaration//GEN-END:variables
}
