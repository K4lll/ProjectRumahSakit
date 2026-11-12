package sistempendaftarandanpelayananpasien.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilitas untuk membaca dan memformat baris CSV sesuai standar RFC 4180.
 * Menangani karakter pembatas koma, tanda kutip ganda, dan spasi secara aman.
 */
public class CsvUtil {

    private CsvUtil() {
    }

    /**
     * Memecah satu baris CSV menjadi daftar kolom nilai.
     */
    public static List<String> parseLine(String line) {
        List<String> result = new ArrayList<>();
        if (line == null) {
            return result;
        }

        StringBuilder curVal = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (inQuotes) {
                if (ch == '\"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                        curVal.append('\"');
                        i++; // Lewati tanda kutip ganda pasangan escape
                    } else {
                        inQuotes = false;
                    }
                } else {
                    curVal.append(ch);
                }
            } else {
                if (ch == '\"') {
                    inQuotes = true;
                } else if (ch == ',') {
                    result.add(curVal.toString().trim());
                    curVal.setLength(0);
                } else {
                    curVal.append(ch);
                }
            }
        }
        result.add(curVal.toString().trim());
        return result;
    }

    /**
     * Menggabungkan daftar nilai menjadi satu baris teks CSV yang valid.
     */
    public static String toCsvLine(List<String> values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(escapeValue(values.get(i)));
        }
        return sb.toString();
    }

    /**
     * Menggabungkan argumen nilai menjadi satu baris teks CSV yang valid.
     */
    public static String toCsvLine(String... values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(escapeValue(values[i]));
        }
        return sb.toString();
    }

    private static String escapeValue(String val) {
        if (val == null) {
            return "";
        }
        String s = val.trim();
        if (s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }
}
