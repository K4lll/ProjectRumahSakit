package sistempendaftarandanpelayananpasien.pasien;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * JPanel dengan sudut membulat untuk tampilan berbasis kartu.
 * Warna kartu diatur lewat properti background (di Form Editor).
 */
public class PanelBulat extends JPanel {

    private int radius = 20;

    public PanelBulat() {
        setOpaque(false);
        setBackground(Tema.PUTIH);
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
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}
