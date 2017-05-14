package com.objectpartners.plummer.junit5;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;

public class Utilities {
    public static Collection<String[]> readFromCsv(String filename) {
        try {
            CSVParser parser = CSVParser.parse(StatesServiceImpl.class.getResource(filename), StandardCharsets.UTF_8, CSVFormat.DEFAULT);
            return parser.getRecords().stream()
                    .map(csvRecord -> new String[] {csvRecord.get(0), csvRecord.get(1) })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
