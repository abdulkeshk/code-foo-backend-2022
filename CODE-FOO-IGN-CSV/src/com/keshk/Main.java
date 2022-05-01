package com.keshk;

import com.keshk.CSVUtils.CSVReader;
import com.keshk.DataCleaner.IGNDataCleaner;
import com.keshk.DatabaseUtils.SQLConnector;

import java.util.Map;
import java.util.List;

public class Main {

    private static final String csvFileName = "src/com/keshk/codefoobackend_cfgames.csv";

    public static void main(String[] args) {
        // Step 1) Read contents of CSV file
        List<Map<String, String>> CSVEntries = CSVReader.readFile(csvFileName);

        // Step 2) Clean long + short description data
        for (Map<String, String> entry : CSVEntries) {
            IGNDataCleaner.cleanRow(entry);
            System.out.println("\n Cleaned Row: \n\t" + entry.toString());
        }

        // Step 3) Connect to DB
        SQLConnector sqlConnector;
        try {
            sqlConnector = new SQLConnector();
        } catch (Exception e) {
            System.out.println(e.toString());
            return;
        }

        // Step 4) Insert each entry into SQL Database
        for (Map<String, String> entry : CSVEntries) {
            // Check if insertion of record succeeded
            boolean didInsert = sqlConnector.insertIGNRecord(entry.values());
            if (!didInsert) {
                System.out.println("Status: Failed");
            } else {
                System.out.println("Status: Success");
            }
        }
    }
}
