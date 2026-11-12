package sistempendaftarandanpelayananpasien.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitas pemformatan tanggal dan waktu standar untuk seluruh aplikasi.
 */
public class FormatTanggal {

    public static final DateTimeFormatter FORMAT_TANGGAL = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMAT_WAKTU = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter FORMAT_LENGKAP = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private FormatTanggal() {
    }

    public static String hariIni() {
        return LocalDate.now().format(FORMAT_TANGGAL);
    }

    public static String sekarang() {
        return LocalDateTime.now().format(FORMAT_LENGKAP);
    }

    public static boolean isValidTanggal(String tgl) {
        if (tgl == null || tgl.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(tgl.trim(), FORMAT_TANGGAL);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
