package org.dan.csvql;

import static java.lang.String.format;
import static java.nio.file.Files.isDirectory;
import static org.dan.csvql.Checks.ensure;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class CommandLineParser {
    static final String INPUT_CHAR_SET = "-input-char-set";
    private static final String OUTPUT_CHAR_SET = "-output-char-set";

    private final Consumer<String> output;
    private final Iterator<String> args;
    private final AppParams appParams = new AppParams();

    public CommandLineParser(Consumer<String> output, Iterator<String> args) {
        this.output = output;
        this.args = args;
    }

    public AppParams parse() {
        ensure(args.hasNext(),
                () -> "Program requires path folder with files");
        appParams.setDataFolder(Paths.get(args.next()));
        ensure(isDirectory(appParams.getDataFolder()),
                () -> "Path [" + appParams.getDataFolder()
                        + "] is not directory");
        while (args.hasNext()) {
            final String option = args.next();
            parseAnOption(option);
        }
        return appParams;
    }

    private <T> T apply(String optionName, Function<String, T> optionValueParser) {
        if (args.hasNext()) {
            final String optionValue = args.next();
            try {
                return optionValueParser.apply(optionValue);
            } catch (ParserException e) {
                throw error("Option [%s] has wrong value [%s] due: %s",
                        optionName, optionValue, e.getMessage());
            }
        } else {
            throw error("Option [%s] requires argument", optionName);
        }
    }

    private void parseAnOption(String option) {
        switch (option) {
            case INPUT_CHAR_SET:
                appParams.setInputCharset(apply(INPUT_CHAR_SET, this::charset));
                break;
            case OUTPUT_CHAR_SET:
                appParams.setOutputCharset(apply(OUTPUT_CHAR_SET, this::charset));
                break;
            default:
                throw error("Unknown command line option [%s]", option);
        }
    }

    private ParserException error(final String pattern, String... args) {
        return new ParserException(format(pattern, args));
    }

    private Charset charset(final String charset) {
        try {
            return Charset.forName(charset);
        } catch (UnsupportedCharsetException e) {
            throw error("Charset [%s] is not supported", charset);
        }
    }

    public static class ParserException extends RuntimeException {
        public ParserException(String error) {
            super(error);
        }
    }
}
