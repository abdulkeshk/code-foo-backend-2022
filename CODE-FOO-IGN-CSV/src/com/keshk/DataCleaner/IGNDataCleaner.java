package com.keshk.DataCleaner;

import java.util.Map;

public class IGNDataCleaner {

    /**
     * Removes single quotes, HTML tags, curly braces, and new line chars from the string
     * @param data to have tags filtered/cleaned
     * @return new string with no HTML tags
     */
    public static String cleanString(String data) {
        // Remove all single quotes from within description
        data = data.replaceAll("'", "");

        // Remove all new lines
        data = data.replaceAll("\n", "");

        // Remove all HTML tags
        data = data.replaceAll("<[^>]*>", " ");

        // Remove curly braces
        data = data.replaceAll("\\{", "");
        data = data.replaceAll("}", "");

        // Wrap in single quotes
        return "'" + data + "'";
    }

    public static void cleanRow(Map<String, String> csvEntry) {
        // For each value, clean it and update in map
        for (String key : csvEntry.keySet()) {
            String cleanedValue = cleanString(csvEntry.get(key));
            csvEntry.put(key, cleanedValue);
        }
    }
}
