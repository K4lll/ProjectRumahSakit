package sistempendaftarandanpelayananpasien.pasien;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class FormDaftarDokter extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FormDaftarDokter.class.getName());

    private final Color ROCK_BLUE = new Color(132, 179, 206);
    private final Color VENICE_BLUE = new Color(22, 88, 123);

    private TableRowSorter<DefaultTableModel> sorter;

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

    public FormDaftarDokter() {
        initComponents();
        bulatkanKartu();
        aturKursor();
        setLocationRelativeTo(null);
        isiData();
        aturTabel();
        aturKartu();
        aturPencarian();
    }

    /** Memasang border bulat saat runtime (di Design kartu tampil dengan padding saja). */
    private void bulatkanKartu() {
        javax.swing.JPanel[] kartu = { kartuTotal, kartuSpesialis, kartuPoli, panelDaftar };
        for (javax.swing.JPanel p : kartu) {
            int pad = p.getBorder().getBorderInsets(p).top;
            p.setBorder(new BorderBulat(new Color(132, 179, 206), 20, pad));
        }
    }

    private void aturKursor() {
        javax.swing.JButton[] tombol = { btnProfil, btnDaftarDokter, btnJadwal, btnReset };
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
        panelKartu = new javax.swing.JPanel();
        kartuTotal = new javax.swing.JPanel();
        lblJudulTotal = new javax.swing.JLabel();
        lblNilaiTotal = new javax.swing.JLabel();
        kartuSpesialis = new javax.swing.JPanel();
        lblJudulSpesialis = new javax.swing.JLabel();
        lblNilaiSpesialis = new javax.swing.JLabel();
        kartuPoli = new javax.swing.JPanel();
        lblJudulPoli = new javax.swing.JLabel();
        lblNilaiPoli = new javax.swing.JLabel();
        panelDaftar = new javax.swing.JPanel();
        panelAtasDaftar = new javax.swing.JPanel();
        lblJudulDaftar = new javax.swing.JLabel();
        panelPencarian = new javax.swing.JPanel();
        lblCari = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        cmbSpesialis = new javax.swing.JComboBox<>();
        btnReset = new javax.swing.JButton();
        scrollDokter = new javax.swing.JScrollPane();
        tblDokter = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Daftar Dokter");

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

        btnProfil.setBackground(new java.awt.Color(22, 88, 123));
        btnProfil.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnProfil.setForeground(new java.awt.Color(255, 255, 255));
        btnProfil.setText("Profil Pasien");
        btnProfil.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 10));
        btnProfil.setBorderPainted(false);
        btnProfil.setFocusPainted(false);
        btnProfil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProfil.addActionListener(this::btnProfilActionPerformed);

        btnDaftarDokter.setBackground(new java.awt.Color(132, 179, 206));
        btnDaftarDokter.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnDaftarDokter.setForeground(new java.awt.Color(22, 88, 123));
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
        lblJudul.setText("Daftar Dokter");

        lblDeskripsi.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblDeskripsi.setForeground(new java.awt.Color(128, 128, 128));
        lblDeskripsi.setText("Lihat dokter yang tersedia di rumah sakit");

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

        panelKartu.setBackground(new java.awt.Color(245, 238, 221));
        panelKartu.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));

        kartuTotal.setBackground(new java.awt.Color(255, 255, 255));
        kartuTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        kartuTotal.setLayout(new java.awt.BorderLayout());

        lblJudulTotal.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblJudulTotal.setForeground(new java.awt.Color(128, 128, 128));
        lblJudulTotal.setText("Total Dokter");
        kartuTotal.add(lblJudulTotal, java.awt.BorderLayout.NORTH);

        lblNilaiTotal.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        lblNilaiTotal.setForeground(new java.awt.Color(22, 88, 123));
        lblNilaiTotal.setText("0");
        kartuTotal.add(lblNilaiTotal, java.awt.BorderLayout.CENTER);

        kartuSpesialis.setBackground(new java.awt.Color(255, 255, 255));
        kartuSpesialis.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        kartuSpesialis.setLayout(new java.awt.BorderLayout());

        lblJudulSpesialis.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblJudulSpesialis.setForeground(new java.awt.Color(128, 128, 128));
        lblJudulSpesialis.setText("Jumlah Spesialis");
        kartuSpesialis.add(lblJudulSpesialis, java.awt.BorderLayout.NORTH);

        lblNilaiSpesialis.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        lblNilaiSpesialis.setForeground(new java.awt.Color(22, 88, 123));
        lblNilaiSpesialis.setText("0");
        kartuSpesialis.add(lblNilaiSpesialis, java.awt.BorderLayout.CENTER);

        kartuPoli.setBackground(new java.awt.Color(255, 255, 255));
        kartuPoli.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        kartuPoli.setLayout(new java.awt.BorderLayout());

        lblJudulPoli.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblJudulPoli.setForeground(new java.awt.Color(128, 128, 128));
        lblJudulPoli.setText("Poli Aktif");
        kartuPoli.add(lblJudulPoli, java.awt.BorderLayout.NORTH);

        lblNilaiPoli.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        lblNilaiPoli.setForeground(new java.awt.Color(22, 88, 123));
        lblNilaiPoli.setText("0");
        kartuPoli.add(lblNilaiPoli, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout panelKartuLayout = new javax.swing.GroupLayout(panelKartu);
        panelKartu.setLayout(panelKartuLayout);
        panelKartuLayout.setHorizontalGroup(
            panelKartuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKartuLayout.createSequentialGroup()
                .addComponent(kartuTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(kartuSpesialis, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(kartuPoli, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        panelKartuLayout.setVerticalGroup(
            panelKartuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kartuTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(kartuSpesialis, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(kartuPoli, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelIsi.add(panelKartu, java.awt.BorderLayout.NORTH);

        panelDaftar.setBackground(new java.awt.Color(255, 255, 255));
        panelDaftar.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelDaftar.setLayout(new java.awt.BorderLayout());

        panelAtasDaftar.setBackground(new java.awt.Color(255, 255, 255));
        panelAtasDaftar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelAtasDaftar.setLayout(new java.awt.BorderLayout());

        lblJudulDaftar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblJudulDaftar.setForeground(new java.awt.Color(22, 88, 123));
        lblJudulDaftar.setText("Informasi Dokter");
        panelAtasDaftar.add(lblJudulDaftar, java.awt.BorderLayout.WEST);

        panelPencarian.setBackground(new java.awt.Color(255, 255, 255));
        panelPencarian.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblCari.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblCari.setForeground(new java.awt.Color(22, 88, 123));
        lblCari.setText("Cari:");
        panelPencarian.add(lblCari);

        txtCari.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        txtCari.setPreferredSize(new java.awt.Dimension(200, 35));
        panelPencarian.add(txtCari);

        cmbSpesialis.setBackground(new java.awt.Color(255, 255, 255));
        cmbSpesialis.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        cmbSpesialis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua Spesialis", "Umum", "Anak", "Gigi", "Penyakit Dalam", "Mata" }));
        cmbSpesialis.setPreferredSize(new java.awt.Dimension(170, 35));
        cmbSpesialis.addActionListener(this::cmbSpesialisActionPerformed);
        panelPencarian.add(cmbSpesialis);

        btnReset.setBackground(new java.awt.Color(22, 88, 123));
        btnReset.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset");
        btnReset.setFocusPainted(false);
        btnReset.setPreferredSize(new java.awt.Dimension(80, 35));
        btnReset.addActionListener(this::btnResetActionPerformed);
        panelPencarian.add(btnReset);

        panelAtasDaftar.add(panelPencarian, java.awt.BorderLayout.EAST);

        panelDaftar.add(panelAtasDaftar, java.awt.BorderLayout.NORTH);

        tblDokter.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        tblDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nama Dokter", "Spesialis", "Poli"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDokter.setGridColor(new java.awt.Color(230, 230, 230));
        tblDokter.setRowHeight(38);
        tblDokter.setSelectionBackground(new java.awt.Color(132, 179, 206));
        tblDokter.setSelectionForeground(new java.awt.Color(22, 88, 123));
        scrollDokter.setViewportView(tblDokter);

        panelDaftar.add(scrollDokter, java.awt.BorderLayout.CENTER);

        panelIsi.add(panelDaftar, java.awt.BorderLayout.CENTER);

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
        new FormProfilPasien().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnProfilActionPerformed

    private void btnDaftarDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarDokterActionPerformed
        // halaman ini sedang aktif
    }//GEN-LAST:event_btnDaftarDokterActionPerformed

    private void btnJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJadwalActionPerformed
        new FormJadwalDokter().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnJadwalActionPerformed

    private void cmbSpesialisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSpesialisActionPerformed
        terapkanFilter();
    }//GEN-LAST:event_cmbSpesialisActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtCari.setText("");
        cmbSpesialis.setSelectedIndex(0);
    }//GEN-LAST:event_btnResetActionPerformed

    private void isiData() {
        DefaultTableModel model = (DefaultTableModel) tblDokter.getModel();
        model.setRowCount(0);
        model.addRow(new Object[] {"1", "dr. Andi Pratama", "Umum", "Poli Umum"});
        model.addRow(new Object[] {"2", "dr. Siti Rahma, Sp.A", "Anak", "Poli Anak"});
        model.addRow(new Object[] {"3", "drg. Budi Santoso", "Gigi", "Poli Gigi"});
        model.addRow(new Object[] {"4", "dr. Rina Wijaya, Sp.PD", "Penyakit Dalam", "Poli Penyakit Dalam"});
        model.addRow(new Object[] {"5", "dr. Hadi Kusuma, Sp.M", "Mata", "Poli Mata"});
    }

    private void aturKartu() {
        DefaultTableModel model = (DefaultTableModel) tblDokter.getModel();
        HashSet<Object> spesialis = new HashSet<>();
        HashSet<Object> poli = new HashSet<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            spesialis.add(model.getValueAt(i, 2));
            poli.add(model.getValueAt(i, 3));
        }
        lblNilaiTotal.setText(String.valueOf(model.getRowCount()));
        lblNilaiSpesialis.setText(String.valueOf(spesialis.size()));
        lblNilaiPoli.setText(String.valueOf(poli.size()));
    }

    private void aturTabel() {
        tblDokter.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tblDokter.getTableHeader().setBackground(VENICE_BLUE);
        tblDokter.getTableHeader().setForeground(Color.WHITE);
        tblDokter.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        tblDokter.getTableHeader().setReorderingAllowed(false);
        scrollDokter.getViewport().setBackground(Color.WHITE);

        DefaultTableCellRenderer tengah = new DefaultTableCellRenderer();
        tengah.setHorizontalAlignment(SwingConstants.CENTER);
        tblDokter.getColumnModel().getColumn(0).setCellRenderer(tengah);
        tblDokter.getColumnModel().getColumn(0).setMaxWidth(70);
    }

    private void aturPencarian() {
        sorter = new TableRowSorter<>((DefaultTableModel) tblDokter.getModel());
        tblDokter.setRowSorter(sorter);

        txtCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                terapkanFilter();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                terapkanFilter();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                terapkanFilter();
            }
        });
    }

    private void terapkanFilter() {
        if (sorter == null) {
            return;
        }
        List<RowFilter<Object, Object>> filter = new ArrayList<>();

        String teks = txtCari.getText().trim();
        if (!teks.isEmpty()) {
            filter.add(RowFilter.regexFilter("(?i)" + Pattern.quote(teks)));
        }
        if (cmbSpesialis.getSelectedIndex() > 0) {
            String pilihan = String.valueOf(cmbSpesialis.getSelectedItem());
            filter.add(RowFilter.regexFilter("^" + Pattern.quote(pilihan) + "$", 2));
        }

        if (filter.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            RowFilter<Object, Object> gabungan = RowFilter.andFilter(filter);
            sorter.setRowFilter(gabungan);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FormDaftarDokter().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDaftarDokter;
    private javax.swing.JButton btnJadwal;
    private javax.swing.JButton btnProfil;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cmbSpesialis;
    private javax.swing.JPanel kartuPoli;
    private javax.swing.JPanel kartuSpesialis;
    private javax.swing.JPanel kartuTotal;
    private javax.swing.JLabel lblCari;
    private javax.swing.JLabel lblDeskripsi;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblJudulDaftar;
    private javax.swing.JLabel lblJudulPoli;
    private javax.swing.JLabel lblJudulSpesialis;
    private javax.swing.JLabel lblJudulTotal;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNilaiPoli;
    private javax.swing.JLabel lblNilaiSpesialis;
    private javax.swing.JLabel lblNilaiTotal;
    private javax.swing.JLabel lblSubLogo;
    private javax.swing.JPanel panelAtasDaftar;
    private javax.swing.JPanel panelDaftar;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelIsi;
    private javax.swing.JPanel panelKartu;
    private javax.swing.JPanel panelKonten;
    private javax.swing.JPanel panelPencarian;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JScrollPane scrollDokter;
    private javax.swing.JTable tblDokter;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
