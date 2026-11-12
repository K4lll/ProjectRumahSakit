package sistempendaftarandanpelayananpasien.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pembangkit nomor identifikasi unik otomatis dengan awalan (prefix).
 */
public class IDGenerator {

    private static final AtomicInteger COUNTER = new AtomicInteger(100);

    private IDGenerator() {
    }

    public static String generateId(String prefix, int totalExisting) {
        return String.format("%s-%03d", prefix, totalExisting + 1);
    }

    public static String generateIdRandom(String prefix) {
        return String.format("%s-%03d", prefix, COUNTER.incrementAndGet());
    }
}
