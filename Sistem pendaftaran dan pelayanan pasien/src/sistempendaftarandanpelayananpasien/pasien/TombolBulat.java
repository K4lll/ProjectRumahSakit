package sistempendaftarandanpelayananpasien.pasien;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

/**
 * JButton dengan sudut membulat. Warna isi mengikuti properti background,
 * warna teks mengikuti properti foreground (default putih).
 */
public class TombolBulat extends JButton {

    private int radius = 14;

    public TombolBulat() {
        super();
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setForeground(Tema.PUTIH);
        setBackground(Tema.BIRU_TUA);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color warna = getBackground();
        if (!isEnabled()) {
            warna = Tema.MERINO;
        } else if (getModel().isPressed()) {
            warna = warna.darker();
        } else if (getModel().isRollover()) {
            warna = warna.brighter();
        }
        g2.setColor(warna);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}
