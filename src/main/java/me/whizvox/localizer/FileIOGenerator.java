package me.whizvox.localizer;

import java.io.*;

public class FileIOGenerator extends IOGenerator<File> {

    public FileIOGenerator(File source) {
        super(source);
    }

    @Override
    public OutputStream generateOut() throws IOException {
        return new FileOutputStream(getSource());
    }

    @Override
    public InputStream generateIn() throws IOException {
        return new FileInputStream(getSource());
    }

}
