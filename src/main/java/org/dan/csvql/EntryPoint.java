package org.dan.csvql;

import static java.util.Arrays.asList;

import org.dan.csvql.CommandLineParser.ParserException;

public class EntryPoint {
    public static void main(String[] args) {
        final CommandLineParser commandLineParser = new CommandLineParser(
                asList(args).iterator());
        try {
            final AppParams appParams = commandLineParser.parse();
            buildReports(appParams);
        } catch (ParserException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    static void buildReports(AppParams appParams) {
        Reports reports = new Reports(appParams);
        reports.build();
    }
}
