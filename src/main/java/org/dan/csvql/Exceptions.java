package org.dan.csvql;

public class Exceptions {
    public static RuntimeException asUnchecked(Exception e) {
        Exceptions.<RuntimeException>_asUnchecked(e);
        return null;
    }

    interface ClumsyFunction<T, E extends Exception> {
        T run() throws E;
    }

    interface ClumsyProcedure extends FunctionalInterface {
        void run();
    }

    public static void unchecked(ClumsyProcedure procedure) {
        try {
            procedure.run();
        } catch (Exception e) {
            throw asUnchecked(e);
        }
    }

    public static <T, E extends Exception> T unchecked(ClumsyFunction<T, E> function) {
        try {
            return function.run();
        } catch (Exception e) {
            throw asUnchecked(e);
        }
    }

    private static <E extends Exception> void _asUnchecked(Exception e) throws E {
        throw (E) e;
    }
}
