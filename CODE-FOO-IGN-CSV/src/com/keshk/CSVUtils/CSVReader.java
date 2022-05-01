package com.keshk.CSVUtils;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import java.io.FileReader;
import java.io.Reader;

/** 
 * CVSReader reads the the data from a CSV file
 * */

public class CSVReader {
    public static List<Map<String, String>> readFile(String filename) {
        Reader reader;
        Iterator<Map<String, String>> iterator;
        List<Map<String, String>> CSVData = new ArrayList<>();

        // Step 1) Open the file by name and create iterator
        try {
            reader = new FileReader(filename);
            iterator = new CsvMapper().readerFor(Map.class)
                    .with(CsvSchema.emptySchema().withHeader()
                    .withColumnSeparator(','))
                    .readValues(reader);
        } catch (Exception e) {
            System.out.println(e.toString());
            return CSVData;
        }

        // Step 2) Loop & Store each row
        while(iterator.hasNext()){
            Map<String, String> keyValPairs = iterator.next();
            System.out.println("\n Non-Clean CSV Row: \n\t" + keyValPairs.toString());
            CSVData.add(keyValPairs);
        }

        // Step 5) Return records
        return CSVData;
    }

}
