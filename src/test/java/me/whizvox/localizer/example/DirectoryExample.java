package me.whizvox.localizer.example;

import me.whizvox.localizer.DirectoryBasedLocalizer;
import me.whizvox.localizer.Language;
import me.whizvox.localizer.AbstractLocalizer;

import java.io.File;

public class DirectoryExample {

    public static void main(String[] args) throws Exception {

        File directory = new File("RUNDIR/lang");
        directory.mkdirs();
        AbstractLocalizer localizer = new DirectoryBasedLocalizer(directory)
                .setCurrent("jp").setDefault("en");

        Language lang_en = new Language();
        lang_en.add(0, "Enter");
        lang_en.add(1, "Exit");
        lang_en.add(2, "Options");
        lang_en.add(3, "DEBUG");
        Language lang_jp = new Language();
        lang_jp.add(0, "入る");
        lang_jp.add(1, "出口");
        lang_jp.add(2, "オプション");

        localizer.add("en", lang_en);
        localizer.add("jp", lang_jp);

        localizer.save();

        System.out.println(localizer.localize(0));
        System.out.println(localizer.localize(3));
        System.out.println(localizer.localize(9000));

    }

}
