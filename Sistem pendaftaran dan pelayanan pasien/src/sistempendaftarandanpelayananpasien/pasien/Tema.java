package sistempendaftarandanpelayananpasien.pasien;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Konstanta warna dan gaya bersama agar tampilan setiap halaman konsisten.
 * Palet: Merino (krem), Rock Blue (biru muda), Venice Blue (biru tua), dan putih.
 */
public final class Tema {

    public static final Color MERINO = new Color(0xF5EEDD);
    public static final Color BIRU_MUDA = new Color(0x84B3CE);
    public static final Color BIRU_TUA = new Color(0x16587B);
    public static final Color PUTIH = Color.WHITE;

    public static final String NAMA_FONT = "Segoe UI";

    private Tema() {
    }

    public static Font font(int gaya, int ukuran) {
        return new Font(NAMA_FONT, gaya, ukuran);
    }

    /** Merapikan tampilan JTable: header biru tua, baris selang-seling putih/krem. */
    public static void gayaTabel(JTable tabel) {
        tabel.setRowHeight(30);
        tabel.setFont(font(Font.PLAIN, 13));
        tabel.setForeground(BIRU_TUA);
        tabel.setBackground(PUTIH);
        tabel.setShowGrid(false);
        tabel.setIntercellSpacing(new Dimension(0, 0));
        tabel.setSelectionBackground(BIRU_MUDA);
        tabel.setSelectionForeground(BIRU_TUA);
        tabel.setFillsViewportHeight(true);
        tabel.getTableHeader().setReorderingAllowed(false);
        tabel.getTableHeader().setPreferredSize(new Dimension(0, 34));

        tabel.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object nilai, boolean dipilih,
                    boolean fokus, int baris, int kolom) {
                super.getTableCellRendererComponent(t, nilai, dipilih, false, baris, kolom);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                setForeground(BIRU_TUA);
                setBackground(dipilih ? BIRU_MUDA : (baris % 2 == 0 ? PUTIH : MERINO));
                return this;
            }
        });

        tabel.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object nilai, boolean dipilih,
                    boolean fokus, int baris, int kolom) {
                super.getTableCellRendererComponent(t, nilai, false, false, baris, kolom);
                setOpaque(true);
                setBackground(BIRU_TUA);
                setForeground(PUTIH);
                setFont(font(Font.BOLD, 13));
                setHorizontalAlignment(SwingConstants.LEFT);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                return this;
            }
        });
    }
}
