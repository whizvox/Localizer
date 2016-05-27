package me.whizvox.localizer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MultiSourceLocalizer extends AbstractLocalizer {

    private IOGenerator genOut, genIn;

    public MultiSourceLocalizer(IOGenerator genOut, IOGenerator genIn) {
        setSources(genOut, genIn);
        setParser(StandardParsers.PARSER_PLAINTEXT_MULTI);
    }

    public MultiSourceLocalizer() {
        this(null, null);
    }

    public MultiSourceLocalizer setSources(IOGenerator out, IOGenerator in) {
        this.genOut = out;
        this.genIn = in;
        return this;
    }

    public MultiSourceLocalizer setOut(IOGenerator out) {
        this.genOut = out;
        return this;
    }

    public MultiSourceLocalizer setIn(IOGenerator in) {
        this.genIn = in;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void save(Object out) throws IOException {
        try (OutputStream outs = genOut.setSource(out).generateOut()) {
            getParser().write(this, outs, null);
        }
    }

    @Override
    public void save() throws IOException {
        try (OutputStream outs = genOut.generateOut()) {
            getParser().write(this, outs, null);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load(Object in) throws IOException {
        try (InputStream ins = genIn.setSource(in).generateIn()) {
            getParser().read(this, ins, null);
        }
    }

    @Override
    public void load() throws IOException {
        try (InputStream ins = genIn.generateIn()) {
            getParser().read(this, ins, null);
        }
    }

}
