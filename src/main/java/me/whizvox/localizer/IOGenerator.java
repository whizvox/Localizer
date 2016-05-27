package me.whizvox.localizer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class IOGenerator<T> {

    private T source;

    public IOGenerator(T source) {
        setSource(source);
    }

    public IOGenerator() {
        this(null);
    }

    public IOGenerator<T> setSource(T source) {
        this.source = source;
        return this;
    }

    protected final T getSource() {
        return source;
    }

    public abstract OutputStream generateOut() throws IOException;



    public abstract InputStream generateIn() throws IOException;

}
