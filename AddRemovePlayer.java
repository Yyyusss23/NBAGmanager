package javaapplication1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AddRemovePlayer {
    // Define constants for NBA team composition rules
    
    private static JFrame frame;
    
    private static final int MIN_PLAYERS = 10;
    private static final int MAX_PLAYERS = 15;
    private static final int TOTAL_SALARY_CAP = 20000;
    private static final int MIN_GUARDS = 2;
    private static final int MIN_FORWARDS = 2;
    private static final int MIN_CENTERS = 2;
    private static final double SUPERSTAR_PPG_THRESHOLD = 20.0;
    private static final double SUPERSTAR_MIN_SALARY = 3000;
    private static final double NON_SUPERSTAR_MIN_SALARY = 1000;

    private ArrayList<Player> roster;

    public AddRemovePlayer() {
        roster = new ArrayList<>();
    }
    
    // Check if a player can be added to the team based on NBA rules and team constraints
    public boolean canAddPlayer(Player player) {
        // Check if maximum team size is reached
        if (roster.size() >= MAX_PLAYERS) {
            JOptionPane.showMessageDialog(null, "Cannot add player to team. Maximum players reached.");
            return false;
        }

        // Check player salary based on performance (superstar or non-superstar)
        if (player.getPpg() >= SUPERSTAR_PPG_THRESHOLD && player.getSalary() < SUPERSTAR_MIN_SALARY) {
            JOptionPane.showMessageDialog(null, "Cannot add player to team. Superstar player salary too low.");
            return false;
        }
        if (player.getPpg() < SUPERSTAR_PPG_THRESHOLD && player.getSalary() < NON_SUPERSTAR_MIN_SALARY) {
            JOptionPane.showMessageDialog(null, "Cannot add player to team. Non-superstar player salary too low.");
            return false;
        }

        // Check team salary cap
        double totalTeamSalary = 0;
        for (Player p : roster) {
            totalTeamSalary += p.getSalary();
        }
        if (totalTeamSalary + player.getSalary() > TOTAL_SALARY_CAP) {
            JOptionPane.showMessageDialog(null, "Cannot add player. Team salary cap exceeded.");
            return false;
        }

        // All rules passed, can add the player
        return true;
    }

    // Add a player to the roster if all conditions are met
    public void addPlayer(Player player) {
        SQLiteJDBC_Database one = new SQLiteJDBC_Database();
        if (canAddPlayer(player)) {
            roster.add(player);
            JOptionPane.showMessageDialog(null, "Player " + player.getName() + " added to the team.");
            one.insertPlayerIntoDatabase(player);
        }
    }

    // Print the current roster details
    public void printRoster() {
        StringBuilder rosterDetails = new StringBuilder("Current Roster:");
        for (Player player : roster) {
            rosterDetails.append("\nName: ").append(player.getName())
                         .append(" Height: ").append(player.getHeight())
                         .append(" Weight: ").append(player.getWeight())
                         .append(" Position: ").append(player.getPosition())
                         .append(" PPG: ").append(player.getPpg())
                         .append(" RPG: ").append(player.getRpg())
                         .append(" APG: ").append(player.getApg())
                         .append(" PIE: ").append(player.getPie())
                         .append(" Salary: $").append(player.getSalary());
        }
        JOptionPane.showMessageDialog(null, rosterDetails.toString());
    }

    // Add players from a CSV file to the team
    public static void addPlayersFromCSV(AddRemovePlayer team) {
        List<List<String>> data = new ArrayList<>();
        String csvFile = "PlayerListLatest.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Read each line from the CSV file
            while((line = br.readLine()) != null) {
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

    // Ensure the ArrayList has dimensions 583 x 13
    while (data.size() < 583) {
        List<String> emptyRow = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            emptyRow.add("");
        }
        data.add(emptyRow);
    }

    Scanner scanner = new Scanner(System.in);

    boolean continueAdding = true;
    while (continueAdding) {
        String playerName = JOptionPane.showInputDialog(frame, "Enter the name of the player you want to add (or type 'quit' to abort):");

        if (playerName.equalsIgnoreCase("quit")) {
            continueAdding = false;
            break;
        }

        // Rule 1: Minimum players check after addition
        if (team.roster.size() < MIN_PLAYERS) {
            JOptionPane.showMessageDialog(null, "Minimum roster size not met. Team must consist of at least ten players.");
            continueAdding = true; // Prompt user to add more players
            continue;
        }

        // Check if any position has less than two players
        int guardsCount = 0, forwardsCount = 0, centersCount = 0;
        for (Player p : team.roster) {
            if (p.getPosition().equalsIgnoreCase("G") || p.getPosition().equalsIgnoreCase("F-G") || p.getPosition().equalsIgnoreCase("C-G")) guardsCount++;
            if (p.getPosition().equalsIgnoreCase("F") || p.getPosition().equalsIgnoreCase("F-G") || p.getPosition().equalsIgnoreCase("F-C")) forwardsCount++;
            if (p.getPosition().equalsIgnoreCase("C") || p.getPosition().equalsIgnoreCase("F-C") || p.getPosition().equalsIgnoreCase("C-G")) centersCount++;
        }

        if (guardsCount < MIN_GUARDS) {
            JOptionPane.showMessageDialog(null, "Minimum of " + MIN_GUARDS + " guards required. Current count: " + guardsCount);
            continueAdding = true; // Prompt user to add more players
            continue;
        }

        if (forwardsCount < MIN_FORWARDS) {
            JOptionPane.showMessageDialog(null, "Minimum of " + MIN_FORWARDS + " forwards required. Current count: " + forwardsCount);
            continueAdding = true; // Prompt user to add more players
            continue;
        }

        if (centersCount < MIN_CENTERS) {
            JOptionPane.showMessageDialog(null, "Minimum of " + MIN_CENTERS + " centers required. Current count: " + centersCount);
            continueAdding = true; // Prompt user to add more players
            continue;
        }

        boolean playerFound = false;
        for (List<String> playerData : data) {
            if (playerData.get(0).equalsIgnoreCase(playerName)) {
                String name = playerData.get(0);
                double height = parseDoubleOrDefault(playerData.get(2), 0.0);
                double weight = parseDoubleOrDefault(playerData.get(3), 0.0);
                String position = playerData.get(4);
                double ppg = parseDoubleOrDefault(playerData.get(5), 0.0);
                double rpg = parseDoubleOrDefault(playerData.get(6), 0.0);
                double apg = parseDoubleOrDefault(playerData.get(7), 0.0);
                double pie = parseDoubleOrDefault(playerData.get(8), 0.0);
                int salary = parseIntOrDefault(playerData.get(9), 0);

                Player player = new Player(name, height, weight, position, ppg, rpg, apg, pie, salary);
                team.addPlayer(player);
                playerFound = true;
                break;
            }
        }

        if (!playerFound) {
            JOptionPane.showMessageDialog(null, "Player not found.");
        }
    }

    team.printRoster();
}

// Remove a player from the roster
public void removePlayer(Player playerToRemove) {
    if (roster.contains(playerToRemove)) {
        roster.remove(playerToRemove);
        JOptionPane.showMessageDialog(null, "Player " + playerToRemove.getName() + " removed from the team.");
    } else {
        JOptionPane.showMessageDialog(null, "Player not found in the roster.");
    }
}

// Remove a player by prompting for their name
public void removePlayerONE() {
    Scanner scanner = new Scanner(System.in);
    String playerNameToRemove = JOptionPane.showInputDialog(frame, "Enter the name of the player you want to remove (or type 'cancel' to abort):");
    removePlayerByName(playerNameToRemove);
}

// Remove a player by name, ensuring all team rules are met
public boolean removePlayerByName(String playerName) {
    for (Player player : roster) {
        if (player.getName().equalsIgnoreCase(playerName)) {
            if (roster.size() <= MIN_PLAYERS) {
                JOptionPane.showMessageDialog(null, "Cannot remove player. Minimum roster size must be maintained.");
                return false;
            }

            if ((player.getPosition().equalsIgnoreCase("G") || player.getPosition().equalsIgnoreCase("G-F")) && countPlayersByPosition("G") <= MIN_GUARDS) {
                JOptionPane.showMessageDialog(null, "Cannot remove player. Minimum guards requirement not met.");
                return false;
            }

            if ((player.getPosition().equalsIgnoreCase("F") || player.getPosition().equalsIgnoreCase("F-G") || player.getPosition().equalsIgnoreCase("F-C")) && countPlayersByPosition("F") <= MIN_FORWARDS) {
                JOptionPane.showMessageDialog(null, "Cannot remove player. Minimum forwards requirement not met.");
                return false;
            }

            if ((player.getPosition().equalsIgnoreCase("C") || player.getPosition().equalsIgnoreCase("C-F")) && countPlayersByPosition("C") <= MIN_CENTERS) {
                JOptionPane.showMessageDialog(null, "Cannot remove player. Minimum centers requirement not met.");
                return false;
            }

            roster.remove(player);
            JOptionPane.showMessageDialog(null, "Player " + playerName + " removed from the team.");
            SQLiteJDBC_Database two = new SQLiteJDBC_Database();
            two.removePlayer(playerName);
            return true;
        }
    }
    return false;
}

// Count players by position in the roster
private int countPlayersByPosition(String position) {
    int count = 0;
    for (Player player : roster) {
        if (player.getPosition().equalsIgnoreCase(position)) {
            count++;
        }
    }
    return count;
}

// Utility method to parse double values safely
private static double parseDoubleOrDefault(String value, double defaultValue) {
    try {
        return Double.parseDouble(value);
    } catch (NumberFormatException e) {
        return defaultValue;
    }
}

// Utility method to parse int values safely
private static int parseIntOrDefault(String value, int defaultValue) {
    try {
        return Integer.parseInt(value);
    } catch (NumberFormatException e) {
        return defaultValue;
    }
}

// Retrieve a player from the roster who is injured
public Player playerInjured(String playerName) {
    Player playerInjure = null;
    for (int i = 0; i < roster.size(); i++) {
        if (playerName.equalsIgnoreCase(roster.get(i).getName())) {
            playerInjure = roster.get(i);
        }
    }
    return playerInjure;
}

// Retrieve a player from the roster for contract extension
public Player playerContractExtension(String playerName) {
    Player playerContractExtension = null;
    JOptionPane.showMessageDialog(null, roster.size());
    for (int i = 0; i < roster.size(); i++) {
        if (playerName.equalsIgnoreCase(roster.get(i).getName())) {
            playerContractExtension = roster.get(i);
        }
    }
    return playerContractExtension;
}
}
