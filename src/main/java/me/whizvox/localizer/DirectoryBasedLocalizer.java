package me.whizvox.localizer;

import java.io.*;

public class DirectoryBasedLocalizer extends AbstractLocalizer {

    public static final String LANG_FILENAME_EXTENSION = ".lang";
    public static final FilenameFilter FILENAME_FILTER_LANG = (dir, name) -> name.endsWith(LANG_FILENAME_EXTENSION);

    private File directory;

    public DirectoryBasedLocalizer(File directory) {
        super();
        this.directory = directory;
        setParser(StandardParsers.PARSER_PLAINTEXT_SINGLE);
    }

    public DirectoryBasedLocalizer() {
        this(null);
    }

    public DirectoryBasedLocalizer setDirectory(File directory) {
        this.directory = directory;
        return this;
    }

    @Override
    public void save(Object out) throws IOException {
        if (out instanceof File) {
            File dir = (File) out;
            if (dir.isDirectory()) {
                for (String name : getAllLanguages()) {
                    File langFile = new File(dir, name + LANG_FILENAME_EXTENSION);
                    langFile.createNewFile();
                    OutputStream fos = new FileOutputStream(langFile);
                    getParser().write(this, fos, name);
                    fos.close();
                }
                return;
            }
        }
        throw new IllegalArgumentException("Output must be a file directory");
    }

    @Override
    public void save() throws IOException {
        save(directory);
    }

    @Override
    public void load(Object in) throws IOException {
        if (in instanceof File) {
            File dir = (File) in;
            if (dir.isDirectory()) {
                File[] langFiles = dir.listFiles(FILENAME_FILTER_LANG);
                for (File langFile : langFiles) {
                    String name = StringUtils.getFilenameWithoutExtension(langFile);
                    InputStream fis = new FileInputStream(langFile);
                    getParser().read(this, fis, name);
                    fis.close();
                }
            }
        }
        throw new IllegalArgumentException("Input must be a file directory");
    }

    @Override
    public void load() throws IOException {
        load(directory);
    }

}
