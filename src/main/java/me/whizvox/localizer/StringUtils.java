package me.whizvox.localizer;

import java.io.File;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern PATT_REMOVE_FILE_EXTENSION = Pattern.compile("[.][^.]+$");

    public static final String EMPTY = "";

    public static boolean isWhitespace(String s) {
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (c != ' ' && c != '\t') {
                return false;
            }
        }
        return true;
    }

    public static String getFilenameWithoutExtension(File file) {
        return PATT_REMOVE_FILE_EXTENSION.matcher(file.getName()).replaceFirst(EMPTY);
    }

}
