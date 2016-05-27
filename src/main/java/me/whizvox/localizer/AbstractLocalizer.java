package me.whizvox.localizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractLocalizer {

    private Map<String, Language> languages;
    private Map<Integer, String> errorTranslations;
    private String defLangKey, currentLangKey;
    private Language defLang, currentLang;
    private LocalizerParser parser;

    public AbstractLocalizer() {
        languages = new HashMap<>();
        errorTranslations = new HashMap<>();
        defLangKey = null;
        defLang = null;
        currentLangKey = null;
        currentLang = null;
    }

    public AbstractLocalizer setParser(LocalizerParser parser) {
        this.parser = parser;
        return this;
    }

    protected final LocalizerParser getParser() {
        return parser;
    }

    public abstract void save(Object out) throws IOException;

    public abstract void save() throws IOException, UnsupportedOperationException;

    public abstract void load(Object in) throws IOException;

    public abstract void load() throws IOException, UnsupportedOperationException;

    public void add(String key, Language lang) {
        languages.put(key, lang);
        if (key.equals(defLangKey)) {
            defLang = lang;
        }
        if (key.equals(currentLangKey)) {
            currentLang = lang;
        }
    }

    public void add(Map<String, Language> map) {
        languages.putAll(map);
    }

    public AbstractLocalizer setDefault(String key) {
        defLangKey = key;
        if (languages.size() > 0 && languages.containsKey(key)) {
            defLang = languages.get(key);
        }
        return this;
    }

    public AbstractLocalizer setCurrent(String key) {
        currentLangKey = key;
        if (languages.size() > 0 && languages.containsKey(key)) {
            currentLang = languages.get(key);
        }
        return this;
    }

    public boolean isDefaultSet() {
        return defLang != null;
    }

    public boolean isCurrentSet() {
        return currentLang != null;
    }

    public String getString(int key) {
        String result = null;
        if (isCurrentSet()) {
            result = currentLang.get(key);
        }
        if (result == null && isDefaultSet()) {
            result = defLang.get(key);
        }
        return result;
    }

    public String getStringUnsafe(int key) throws LocalizerException {
        String result = getString(key);
        if (result == null) {
            throw new LocalizerException("No string exists for [" + buildErrorString(key) + "]");
        }
        return result;
    }

    public String localize(int key) {
        String result = getString(key);
        if (result == null) {
            if (errorTranslations.containsKey(key)) {
                result = errorTranslations.get(key);
            } else {
                result = buildErrorString(key);
                errorTranslations.put(key, result);
            }
        }
        return result;
    }

    public Iterable<String> getAllLanguages() {
        return languages.keySet();
    }

    public Iterable<Map.Entry<Integer, String>> getAllStrings(String langName) {
        if (languages.containsKey(langName)) {
            return languages.get(langName).getStrings();
        }
        return null;
    }

    public Iterable<Map.Entry<Integer, String>> getAllStrings() {
        return currentLang == null ? null : currentLang.getStrings();
    }

    protected String buildErrorString(int key) {
        return "str#" + Integer.toString(key);
    }

}
