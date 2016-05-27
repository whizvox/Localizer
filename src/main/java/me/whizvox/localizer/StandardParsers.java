package me.whizvox.localizer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class StandardParsers {

    private StandardParsers() {}

    private static final Charset PLAINTEXT_CHARSET = StandardCharsets.UTF_8;
    public static final String PLAINTEXT_NAME_TOKEN = "@NAME";
    public static final char PLAINTEXT_COMMENT = '#', PLAINTEXT_SEPARATOR = ':';

    public static final LocalizerParser PARSER_PLAINTEXT_SINGLE = new LocalizerParser() {
        @Override
        public void read(AbstractLocalizer localizer, InputStream in, String name) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, PLAINTEXT_CHARSET));
            Map<Integer, String> strings = new HashMap<>();
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.length() > 0 && line.charAt(0) != PLAINTEXT_COMMENT && !StringUtils.isWhitespace(line)) {
                    int separator = line.indexOf(PLAINTEXT_SEPARATOR);
                    if (separator != -1) {
                        String token1 = line.substring(0, separator);
                        String token2 = line.substring(separator + 1);
                        try {
                            int id = Integer.parseUnsignedInt(token1);
                            strings.put(id, token2);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid line ( " + lineNumber + "). Expected a positive integer: " + token1);
                        }
                    } else {
                        System.err.println("Invalid line (" + lineNumber + "). Expected a format of \"INT:STRING\": " + line);
                    }
                }
            }
            localizer.add(name, Language.fromMap(strings));
            reader.close();
        }
        @Override
        public void write(AbstractLocalizer localizer, OutputStream out, String name) throws IOException {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, PLAINTEXT_CHARSET));
            for (Map.Entry<Integer, String> entry : localizer.getAllStrings(name)) {
                writer.write(Integer.toString(entry.getKey()));
                writer.write(PLAINTEXT_SEPARATOR);
                writer.write(entry.getValue());
                writer.newLine();
            }
            writer.close();
        }
    };

    public static final LocalizerParser PARSER_PLAINTEXT_MULTI = new LocalizerParser() {
        @Override
        public void read(AbstractLocalizer localizer, InputStream in, String name) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, PLAINTEXT_CHARSET));
            Map<Integer, String> strings = new HashMap<>();
            String currentLang = null;
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.length() > 0 && line.charAt(0) != PLAINTEXT_COMMENT && !StringUtils.isWhitespace(line)) {
                    int separator = line.indexOf(PLAINTEXT_SEPARATOR);
                    if (separator != -1) {
                        String token1 = line.substring(0, separator);
                        String token2 = line.substring(separator + 1);
                        if (PLAINTEXT_NAME_TOKEN.equals(token1)) {
                            if (currentLang != null) {
                                localizer.add(currentLang, Language.fromMap(strings));
                                strings.clear();
                            }
                            currentLang = token2;
                        } else {
                            try {
                                int id = Integer.parseUnsignedInt(token1);
                                strings.put(id, token2);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid line (" + lineNumber + "). Expected a positive integer: " + token1);
                            }
                        }
                    } else {
                        System.err.println("Invalid line (" + lineNumber + "). Expected a format of \"INT:STRING\": " + line);
                    }
                }
            }
            // Should be true unless the NAME token never came up
            if (currentLang != null) {
                localizer.add(currentLang, Language.fromMap(strings));
            }
            reader.close();
        }
        @Override
        public void write(AbstractLocalizer localizer, OutputStream out, String name) throws IOException {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, PLAINTEXT_CHARSET));
            for (String langName : localizer.getAllLanguages()) {
                writer.write(PLAINTEXT_NAME_TOKEN);
                writer.write(PLAINTEXT_SEPARATOR);
                writer.write(langName);
                writer.newLine();
                for (Map.Entry<Integer, String> entry : localizer.getAllStrings(langName)) {
                    writer.write(Integer.toString(entry.getKey()));
                    writer.write(PLAINTEXT_SEPARATOR);
                    writer.write(entry.getValue());
                    writer.newLine();
                }
            }
            writer.close();
        }
    };

}
