package sistempendaftarandanpelayananpasien.model;

/**
 * Representasi dan konstanta status pendaftaran dan alur pelayanan pasien.
 */
public class StatusPendaftaran {

    public static final String MENUNGGU = "Menunggu";
    public static final String DITERIMA = "Diterima";
    public static final String DITOLAK = "Ditolak";
    public static final String SEDANG_DIPERIKSA = "Sedang Diperiksa";
    public static final String SELESAI = "Selesai";
    public static final String DIBATALKAN = "Dibatalkan";

    private StatusPendaftaran() {
    }

    public static boolean canCancelOrEdit(String status) {
        return MENUNGGU.equalsIgnoreCase(status);
    }
}
