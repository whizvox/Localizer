package me.whizvox.localizer.example;

import me.whizvox.localizer.AbstractLocalizer;
import me.whizvox.localizer.FileIOGenerator;
import me.whizvox.localizer.IOGenerator;
import me.whizvox.localizer.MultiSourceLocalizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class URLToFileExample {

    public static void main(String[] args) throws Exception {

        IOGenerator<URL> ioUrl = new IOGenerator<URL>(new URL("http://pastebin.com/raw/MfzYKvFN")) {
            @Override
            public OutputStream generateOut() throws IOException {
                throw new UnsupportedOperationException();
            }
            @Override
            public InputStream generateIn() throws IOException {
                return getSource().openStream();
            }
        };
        IOGenerator<File> ioFile = new FileIOGenerator(new File("RUNDIR/localizations.lang"));

        AbstractLocalizer localizer = new MultiSourceLocalizer(ioFile, ioUrl)
                .setDefault("en").setCurrent("jp");
        localizer.load();

        System.out.println(localizer.localize(0));
        System.out.println(localizer.localize(1));
        System.out.println(localizer.localize(2));
        System.out.println(localizer.localize(3));
        System.out.println(localizer.localize(600));

    }

}
