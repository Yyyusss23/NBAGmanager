package javaapplication1;

import java.util.Scanner;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Manages players in the injury reserve, including adding, removing, and displaying injured players.
 */
public class InjuryManagement {
    private Stack<Player> injuryReserve; // Stack to hold players in the injury reserve
    private SQLiteJDBC_Database database; // Database object to interact with the SQLite database
    private AddRemovePlayer team; // Object to manage the team
    private JFrame frame; // JFrame for GUI components

    /**
     * Constructor to initialize InjuryManagement, setting up the injury reserve stack, database, and team.
     */
    public InjuryManagement() {
        this.injuryReserve = new Stack<>();
        this.database = new SQLiteJDBC_Database(); // Initialize the database
        this.team = new AddRemovePlayer(); // Initialize the team
    }

    /**
     * Adds a player to the injury reserve with the specified injury problem.
     * 
     * @param p The player to be added to the injury reserve.
     * @param problem The injury problem of the player.
     */
    public void addPlayerToInjuryReserve(Player p, String problem) {
        if (p != null) {
            // Add player to injury reserve stack
            injuryReserve.push(p);
            
            // Add player to the database
            database.addPlayerToInjuryReserve(p, problem);
            
            // Show message to user
            JOptionPane.showMessageDialog(null, "--Adding Player To Injury Reserve--\nPlayer name : " + p.getName() + "\nInjury : " + problem + "\nStatus : Added to Injury Reserve");
        } else {
            JOptionPane.showMessageDialog(null, "Cannot add null player to injury reserve.");
        }
    }

    /**
     * Removes a player from the injury reserve by name.
     * 
     * @param playerName The name of the player to be removed from the injury reserve.
     */
    public void removePlayerFromInjuryReserve(String playerName) {
        if (!injuryReserve.isEmpty()) {
            // Remove player from the injury reserve stack
            Player removedPlayer = injuryReserve.pop();
            JOptionPane.showMessageDialog(null, "--Remove Player From Injury Reserve--\nPlayer name : " + removedPlayer.getName() + "\nStatus : Cleared To Play");
            
            // Remove player from the database
            database.removePlayerFromInjuryReserveDatabase(playerName);
        } else {
            JOptionPane.showMessageDialog(null, "No players are currently in the injury reserve.");
        }
    }

    /**
     * Displays the list of players currently in the injury reserve.
     */
    public void displayInjuredPlayers() {
        if (!injuryReserve.isEmpty()) {
            StringBuilder injuryDetails = new StringBuilder("Players in the injury reserve:");
            int i = 1;
            for (Player player : injuryReserve) {
                injuryDetails.append("\n").append(i).append(". ").append(player.getName());
                i++;
            }
            JOptionPane.showMessageDialog(null, injuryDetails.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No players are currently in the injury reserve.");
        }
    }

    /**
     * Prompts the user to input details of an injured player and adds the player to the injury reserve.
     */
    public void addPlayerToInjuryManagement() {
        // Get player details from the user
        String playerName = JOptionPane.showInputDialog(frame, "Injured player name : ");
        String injuryProblem = JOptionPane.showInputDialog(frame, "Injury : ");
        JOptionPane.showMessageDialog(null, "Add Player to Injury Management\nInjured player name : " + playerName + "\nInjury : " + injuryProblem);

        // Get the player object using the player's name
        Player p = team.playerInjured(playerName);

        // Add the player to the injury reserve
        addPlayerToInjuryReserve(p, injuryProblem);
        
        // Add the player to the injury reserve in the database
        database.addPlayerToInjuryReserve(p, injuryProblem);
    }
}
