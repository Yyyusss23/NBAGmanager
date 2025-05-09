package javaapplication1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * DynamicSearcher class provides functionality to search for players based on various criteria.
 */
public class DynamicSearcher {

    private static JFrame frame; // JFrame for GUI components
    private SQLiteJDBC_Database database; // Database object to interact with SQLite database
    private static final int NAME_INDEX = 0; // Index for the player's name in the data list
    private static final int HEIGHT_INDEX = 2; // Index for the player's height in the data list
    private static final int WEIGHT_INDEX = 3; // Index for the player's weight in the data list
    private static final int POSITION_INDEX = 4; // Index for the player's position in the data list
    private static final int PPG_INDEX = 5; // Index for the player's points per game (PPG) in the data list
    private static final int REB_INDEX = 6; // Index for the player's rebounds in the data list
    private static final int AST_INDEX = 7; // Index for the player's assists in the data list
    private static final int DEF_INDEX = 8; // Index for the player's defensive stats in the data list

    /**
     * Searches for players in the given data list based on the specified criteria.
     * 
     * @param data The list of player data to search through.
     * @param height The height criteria to match.
     * @param heightOperator The operator for height comparison (e.g., "=", ">=", "<=").
     * @param weight The weight criteria to match.
     * @param weightOperator The operator for weight comparison (e.g., "=", ">=", "<=").
     * @param position The position criteria to match.
     * @param ppg The points per game (PPG) criteria to match.
     * @param ppgOperator The operator for PPG comparison (e.g., "=", ">=", "<=").
     * @return A list of players matching the search criteria.
     */
    public static List<List<String>> searchPlayers(List<List<String>> data, String height, String heightOperator, String weight, String weightOperator, String position, String ppg, String ppgOperator) {
        List<List<String>> searchResult = new ArrayList<>();

        // Iterate through each player in the data list
        for (List<String> player : data) {
            // Parse player attributes
            double playerHeight = Double.parseDouble(player.get(HEIGHT_INDEX));
            double playerWeight = Double.parseDouble(player.get(WEIGHT_INDEX));
            double playerPPG = Double.parseDouble(player.get(PPG_INDEX));

            // Check height criteria
            if ((heightOperator.equals("=") && playerHeight == Double.parseDouble(height)) ||
                (heightOperator.equals(">=") && playerHeight >= Double.parseDouble(height)) ||
                (heightOperator.equals("<=") && playerHeight <= Double.parseDouble(height))) {

                // Check weight criteria
                if ((weightOperator.equals("=") && playerWeight == Double.parseDouble(weight)) ||
                    (weightOperator.equals(">=") && playerWeight >= Double.parseDouble(weight)) ||
                    (weightOperator.equals("<=") && playerWeight <= Double.parseDouble(weight))) {

                    // Check position criteria
                    if (player.get(POSITION_INDEX).equalsIgnoreCase(position)) {

                        // Check PPG criteria
                        if ((ppgOperator.equals("=") && playerPPG == Double.parseDouble(ppg)) ||
                            (ppgOperator.equals(">=") && playerPPG >= Double.parseDouble(ppg)) ||
                            (ppgOperator.equals("<=") && playerPPG <= Double.parseDouble(ppg))) {

                            // Add player to search results if all criteria match
                            searchResult.add(player);
                        }
                    }
                }
            }
        }

        // Display message if no players found
        if (searchResult.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No players found matching the search criteria.");
        } else {
            // Display found players
            for (List<String> player : searchResult) {
                JOptionPane.showMessageDialog(null, "Player found: " + player.get(NAME_INDEX));
            }
        }

        return searchResult;
    }

    /**
     * Reads a double value from the scanner with input validation.
     * 
     * @param scanner The Scanner object to read input from.
     * @return The validated double value.
     */
    private static double readDouble(Scanner scanner) {
        double value = 0.0;
        boolean isValid = false;

        // Loop until a valid double is entered
        while (!isValid) {
            try {
                value = Double.parseDouble(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid numeric value.");
            }
        }

        return value;
    }
}
