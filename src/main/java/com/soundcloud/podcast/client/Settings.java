package com.soundcloud.podcast.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public final class Settings {

    private final Map<Integer, String> codesToNames;
    private final List<Match> matches;


    private Settings(Map<Integer, String> codesToNames,
                     List<Match> matches) {
        this.codesToNames = codesToNames;
        this.matches = matches;
    }

    public String getName(int code) {
        return codesToNames.get(code);
    }

    public List<Match> getMatches() {
        return matches;
    }

    public static Settings read() {
        try {
            InputStream in = Settings.class.getResourceAsStream("/settings.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                                                   .withCommentMarker('#')
                                                   .parse(reader);

            Map<Integer, String> codesToNames = new HashMap<Integer, String>();
            List<Match> matches = new LinkedList<Match>();

            for (CSVRecord record : records) {
                int code = Integer.parseInt(record.get(0));
                String name = record.get(1);

                if (codesToNames.keySet().contains(code)) {
                  throw new RuntimeException("Codes must be unique, but repeated " + code);
                }
                codesToNames.put(code, name);

                for (int i=2; i<record.size(); i++) {
                  matches.add(new Match(Pattern.compile(record.get(i)), code));
                }
            }

            return new Settings( Collections.unmodifiableMap(codesToNames)
                               , Collections.unmodifiableList(matches));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read settings", e);
        }
    }

    public final static class Match {

        private final Pattern pattern;
        private final int code;

        public Match(Pattern pattern,
                     int code) {
            this.pattern = pattern;
            this.code = code;
        }

        public boolean matches(String value) {
            return pattern.matcher(value).matches();
        }

        public int getCode() {
            return code;
        }
    }
}
