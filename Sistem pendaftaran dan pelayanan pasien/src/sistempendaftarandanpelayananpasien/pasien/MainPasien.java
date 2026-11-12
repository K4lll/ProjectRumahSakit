package sistempendaftarandanpelayananpasien.pasien;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/** Halaman autentikasi (login & registrasi) + entry point aplikasi pasien. */
public class MainPasien extends JFrame {
    static final Color KRIM = new Color(0xF5EEDD), MUDA = new Color(0x84B3CE), TUA = new Color(0x16587B);
    static final Font F = new Font("SansSerif", Font.PLAIN, 14), FB = new Font("SansSerif", Font.BOLD, 14);
    // data demo (in-memory): username -> {user, pass, nama, nik, telp, alamat}
    static final Map<String, String[]> users = new HashMap<>();
    // username -> daftar pendaftaran {id, dokter, jadwal, keluhan, status, diagnosis, anjuran}
    static final Map<String, java.util.List<String[]>> regs = new HashMap<>();
    static String[] cur;

    static {
        users.put("demo", new String[]{"demo", "demo123", "Budi Santoso", "1471010101900001", "081234567890", "Jl. Sudirman No. 10, Pekanbaru"});
        java.util.List<String[]> l = new ArrayList<>();
        l.add(new String[]{"REG-001", "dr. Andi Pratama, Sp.PD", "Senin 08.00-12.00", "Demam 3 hari", "Selesai", "Infeksi virus ringan", "Istirahat, paracetamol 3x1, banyak minum"});
        l.add(new String[]{"REG-002", "dr. Sari Wulandari, Sp.A", "Selasa 09.00-13.00", "Batuk pilek", "Ditolak", "", ""});
        l.add(new String[]{"REG-003", "dr. Rudi Hartono, Sp.JP", "Rabu 13.00-17.00", "Nyeri dada ringan", "Diterima", "", ""});
        l.add(new String[]{"REG-004", "dr. Andi Pratama, Sp.PD", "Rabu 08.00-12.00", "Kontrol lambung", "Menunggu", "", ""});
        regs.put("demo", l);
    }

    /** Panel berkartu dengan sudut membulat. */
    static class Round extends JPanel {
        Color c; int r;
        Round(Color c, int r) { this.c = c; this.r = r; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c); g2.fillRoundRect(0, 0, getWidth(), getHeight(), r, r); g2.dispose();
            super.paintComponent(g);
        }
    }

    static JButton btn(String t, Color bg, Color fg) {
        JButton b = new JButton(t);
        b.setFont(FB); b.setBackground(bg); b.setForeground(fg); b.setOpaque(true);
        b.setFocusPainted(false); b.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    static void row(JPanel p, int y, String label, JComponent c) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridy = y; g.anchor = GridBagConstraints.WEST; g.insets = new Insets(5, 0, 2, 0);
        g.fill = GridBagConstraints.HORIZONTAL; g.gridx = 0; g.weightx = 1;
        JLabel l = new JLabel(label); l.setFont(F); l.setForeground(TUA);
        p.add(l, g);
        g.gridy = y + 1; g.insets = new Insets(0, 0, 4, 0); c.setFont(F); c.setPreferredSize(new Dimension(300, 32));
        p.add(c, g);
    }

    static JLabel title(String t) {
        JLabel l = new JLabel(t); l.setFont(new Font("SansSerif", Font.BOLD, 24)); l.setForeground(TUA);
        return l;
    }

    public MainPasien() {
        setTitle("RS Sehat - Aplikasi Pasien");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 740); setLocationRelativeTo(null);
        JPanel left = new JPanel(new GridBagLayout()); left.setBackground(TUA);
        JLabel t = new JLabel("<html><div style='text-align:center'><span style='font-size:30pt'>RS Sehat</span>"
                + "<br><br>Daftar &amp; dapatkan pelayanan<br>rumah sakit dengan mudah</div></html>");
        t.setForeground(Color.WHITE); t.setFont(F); left.add(t);
        JPanel right = new JPanel(new CardLayout());
        right.add(loginCard(right), "login");
        right.add(regCard(right), "reg");
        setLayout(new GridLayout(1, 2)); add(left); add(right);
    }

    private JPanel shell(JPanel card) {
        JPanel o = new JPanel(new GridBagLayout()); o.setBackground(KRIM);
        card.setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));
        o.add(card); return o;
    }

    private JPanel loginCard(JPanel host) {
        Round c = new Round(Color.WHITE, 30); c.setLayout(new GridBagLayout());
        JTextField u = new JTextField(18); JPasswordField p = new JPasswordField(18);
        JCheckBox show = new JCheckBox("Tampilkan password"); show.setOpaque(false); show.setFont(F);
        char echo = p.getEchoChar();
        show.addActionListener(e -> p.setEchoChar(show.isSelected() ? (char) 0 : echo));
        JButton in = btn("Masuk", TUA, Color.WHITE), reg = btn("Buat akun baru", MUDA, Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.gridy = 0; g.insets = new Insets(0, 0, 4, 0); c.add(title("Masuk"), g);
        JLabel s = new JLabel("Demo: demo / demo123"); s.setFont(F); s.setForeground(MUDA);
        g.gridy = 1; g.insets = new Insets(0, 0, 14, 0); c.add(s, g);
        row(c, 2, "Username", u); row(c, 4, "Password", p);
        g.gridy = 6; g.anchor = GridBagConstraints.WEST; c.add(show, g);
        g.gridy = 7; g.fill = GridBagConstraints.HORIZONTAL; g.insets = new Insets(14, 0, 6, 0); c.add(in, g);
        g.gridy = 8; g.insets = new Insets(0, 0, 0, 0); c.add(reg, g);
        in.addActionListener(e -> {
            String[] d = users.get(u.getText().trim());
            if (d != null && d[1].equals(new String(p.getPassword()))) {
                cur = d;
                sistempendaftarandanpelayananpasien.auth.SessionManager.setSesi(d[0], d[2], "pasien");
                new FormMenuPasien().setVisible(true);
                dispose();
            } else JOptionPane.showMessageDialog(this, "Username atau password salah.", "Login gagal", JOptionPane.ERROR_MESSAGE);
        });
        reg.addActionListener(e -> ((CardLayout) host.getLayout()).show(host, "reg"));
        getRootPane().setDefaultButton(in);
        return shell(c);
    }

    private JPanel regCard(JPanel host) {
        Round c = new Round(Color.WHITE, 30); c.setLayout(new GridBagLayout());
        JTextField nama = new JTextField(18), nik = new JTextField(), telp = new JTextField(), alm = new JTextField(), usr = new JTextField();
        JPasswordField p1 = new JPasswordField(), p2 = new JPasswordField();
        JCheckBox ok = new JCheckBox("Data yang saya isi sudah benar"); ok.setOpaque(false); ok.setFont(F);
        JButton b = btn("Daftar", TUA, Color.WHITE), back = btn("Kembali ke login", MUDA, Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.gridy = 0; g.insets = new Insets(0, 0, 8, 0); c.add(title("Registrasi Akun"), g);
        String[] lb = {"Nama lengkap", "NIK", "No. telepon", "Alamat", "Username", "Password", "Konfirmasi password"};
        JComponent[] cs = {nama, nik, telp, alm, usr, p1, p2};
        for (int i = 0; i < cs.length; i++) row(c, 1 + i * 2, lb[i], cs[i]);
        g.gridy = 15; g.anchor = GridBagConstraints.WEST; g.insets = new Insets(4, 0, 4, 0); c.add(ok, g);
        g.gridy = 16; g.fill = GridBagConstraints.HORIZONTAL; c.add(b, g);
        g.gridy = 17; g.insets = new Insets(0, 0, 0, 0); c.add(back, g);
        back.addActionListener(e -> ((CardLayout) host.getLayout()).show(host, "login"));
        b.addActionListener(e -> {
            String pw = new String(p1.getPassword()), us = usr.getText().trim();
            String m = null;
            if (nama.getText().isBlank() || nik.getText().isBlank() || telp.getText().isBlank() || alm.getText().isBlank() || us.isEmpty() || pw.isEmpty()) m = "Semua kolom wajib diisi.";
            else if (!pw.equals(new String(p2.getPassword()))) m = "Konfirmasi password tidak sama.";
            else if (users.containsKey(us)) m = "Username sudah dipakai.";
            else if (!ok.isSelected()) m = "Centang pernyataan data benar.";
            if (m != null) { JOptionPane.showMessageDialog(this, m, "Registrasi", JOptionPane.WARNING_MESSAGE); return; }
            users.put(us, new String[]{us, pw, nama.getText().trim(), nik.getText().trim(), telp.getText().trim(), alm.getText().trim()});
            regs.put(us, new ArrayList<>());
            JOptionPane.showMessageDialog(this, "Akun berhasil dibuat. Silakan login.");
            for (JComponent x : cs) ((javax.swing.text.JTextComponent) x).setText("");
            ok.setSelected(false);
            ((CardLayout) host.getLayout()).show(host, "login");
        });
        return shell(c);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel()); } catch (Exception ex) { }
        EventQueue.invokeLater(() -> new MainPasien().setVisible(true));
    }
}
