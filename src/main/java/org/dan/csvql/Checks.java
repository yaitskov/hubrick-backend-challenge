package org.dan.csvql;

import java.util.function.Supplier;

public interface Checks {
    static void ensure(boolean a, Supplier<String> message) {
        if (a) {
            return;
        }
        throw new RuntimeException(message.get());
    }
}
