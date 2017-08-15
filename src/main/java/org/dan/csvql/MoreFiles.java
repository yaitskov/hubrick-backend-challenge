package org.dan.csvql;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Files.newBufferedWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;

public interface MoreFiles {
    static <T> T readFrom(Path path, Charset charset, Function<BufferedReader, T> consumer) {
        try (BufferedReader reader = newBufferedReader(path, charset)) {
            return consumer.apply(reader);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed on file ["  + path + "]", e);
        } catch (IOException e) {
            throw Exceptions.asUnchecked(e);
        }
    }
    static void writeTo(Path path, Charset charset,
            Consumer<PrintWriter> consumer) {
        try (BufferedWriter writer = newBufferedWriter(path, charset)) {
            consumer.accept(new PrintWriter(writer));
        } catch (IOException e) {
            throw Exceptions.asUnchecked(e);
        }
    }
}
