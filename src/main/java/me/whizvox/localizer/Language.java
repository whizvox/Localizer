package me.whizvox.localizer;

import java.util.HashMap;
import java.util.Map;

public class Language {

    private Map<Integer, String> translations;

    public Language() {
        translations = new HashMap<>();
    }

    public void add(int key, String string) {
        translations.put(key, string);
    }

    public String get(int key) {
        return translations.get(key);
    }

    public Iterable<Map.Entry<Integer, String>> getStrings() {
        return translations.entrySet();
    }

    public static Language fromMap(Map<Integer, String> strings) {
        Language result = new Language();
        strings.forEach(result::add);
        return result;
    }

}
