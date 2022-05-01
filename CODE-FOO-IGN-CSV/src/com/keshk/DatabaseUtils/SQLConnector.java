package com.keshk.DatabaseUtils;

import java.util.Collection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnector {
    private final String connectionURL = "jdbc:postgresql://localhost:5432/ign_records";

    private Connection conn;
    private String records_columns = "id\n" +
            "media_type\n" +
            "name\n" +
            "short_name\n" +
            "long_description\n" +
            "short_description\n" +
            "created_at\n" +
            "updated_at\n" +
            "review_url\n" +
            "review_score\n" +
            "slug\n" +
            "genres\n" +
            "created_by\n" +
            "published_by\n" +
            "franchises\n" +
            "regions";


    public SQLConnector() throws SQLException {
        conn = DriverManager.getConnection(connectionURL);
        records_columns = records_columns.replaceAll("\n", ", ");
    }

    public boolean insertIGNRecord(Collection<String> values) {
        // Wrap values in parenthesis and split by commas
        String clause = "(" + String.join(",", values) + ")";

        // Try to create and run SQL Insert Statement
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO records(" + records_columns + ") VALUES " + clause + ";";
            System.out.println("\nEXECUTING QUERY:\n\t" + sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.toString() + " failed to insert values: " + clause);
            return false;
        }

        return true;
    }

}
