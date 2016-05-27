package me.whizvox.localizer;

import java.io.*;

/**
 * Used for parsing a LANG format.
 */
public interface LocalizerParser {

    void read(AbstractLocalizer localizer, InputStream in, String name) throws IOException;

    void write(AbstractLocalizer localizer, OutputStream out, String name) throws IOException;

}
