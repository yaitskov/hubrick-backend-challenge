package org.dan.csvql;


import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.charset.Charset;
import java.nio.file.Path;

public class AppParams {
    private Charset inputCharset = UTF_8;
    private Charset outputCharset = UTF_8;
    private Path dataFolder;

    public Path getDataFolder() {
        return dataFolder;
    }

    public void setDataFolder(Path dataFolder) {
        this.dataFolder = dataFolder;
    }

    public Charset getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(Charset inputCharset) {
        this.inputCharset = inputCharset;
    }

    public Charset getOutputCharset() {
        return outputCharset;
    }

    public void setOutputCharset(Charset outputCharset) {
        this.outputCharset = outputCharset;
    }
}
