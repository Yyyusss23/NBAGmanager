package javaapplication1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading data from a CSV file.
 * This class provides methods to read data from a CSV file and return it as a list of lists.
 */
public class Data {
    
    private static final int NAME_INDEX = 0; // Index for the name column in the CSV file
    
    /**
     * Reads data from a CSV file and returns it as a list of lists.
     * Each inner list represents a row from the CSV file.
     * The method ensures that the returned list has a size of 583 rows, 
     * with each row containing 12 fields.
     * 
     * @param csvFile the path to the CSV file
     * @return a list of lists containing the CSV data
     */
    public static List<List<String>> readDataFromFile(String csvFile) {
        List<List<String>> data = new ArrayList<>(); // List to store the CSV data
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Read each line from the CSV file
            while ((line = br.readLine()) != null && data.size() < 583) {
                // Split the line into fields using comma as delimiter
                String[] fields = line.split(",");

                // Create a list to store the fields of this row
                List<String> rowData = new ArrayList<>();

                // Add each field to the row data list
                for (String field : fields) {
                    rowData.add(field);
                }

                // Add the row data to the data list
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ensure the ArrayList has dimensions 583 x 12
        while (data.size() < 583) {
            List<String> emptyRow = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                emptyRow.add("");
            }
            data.add(emptyRow); // Add the empty row to the data list
        }

        return data; // Return the data list
    }
}
