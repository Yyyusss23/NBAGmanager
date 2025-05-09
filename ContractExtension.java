package javaapplication1;

import java.util.LinkedList;
import javax.swing.JOptionPane;

public class ContractExtension {
    private LinkedList<Player> celine;  // Queue to hold players awaiting contract extension
    private AddRemovePlayer team;
    private int capacity;  // Maximum capacity of the contract extension queue

    // Constructor to initialize the queue and its capacity
    public ContractExtension(int capacity) {
        celine = new LinkedList<>();
        this.capacity = capacity;
    }

    // Method to add a player to the contract extension queue
    public void addPlayerToContractExtension(Player foster) {
        // Check if the queue is not full
        if (!isQueueFull()) {
            // Check if the player is not null
            if (foster != null) {
                // Check if the player is not already in the queue
                if (!isPlayerInQueue(foster)) {
                    celine.add(foster);  // Add player to the queue
                    SQLiteJDBC_Database.insertPlayerIntoContractExtensionTable(foster.getName());
                    JOptionPane.showMessageDialog(null, "Name : " + foster.getName());
                    JOptionPane.showMessageDialog(null, "Status : Added to Contract Extension Queue");
                } else {
                    // Notify that the player is already in the queue
                    JOptionPane.showMessageDialog(null, "Player " + foster.getName() + " is already in the contract extension queue.");
                }
            } else {
                // Notify that null players cannot be added
                JOptionPane.showMessageDialog(null, "Cannot add null player to Contract Extension.");
            }
        } else {
            // Notify that the queue is full
            JOptionPane.showMessageDialog(null, "Contract extension queue line is full");
        }
    }

    // Method to remove the next player from the contract extension queue
    public String removePlayerFromContractExtension() {
        // Check if the queue is not empty
        if (!isQueueEmpty()) {
            String removedPlayer = celine.removeFirst().getName();  // Remove player from the queue
            SQLiteJDBC_Database.removePlayerFromContractExtensionTable(removedPlayer);
            JOptionPane.showMessageDialog(null, "Player " + removedPlayer + " removed from contract extension queue." + "\nStatus : Contract renewed");
            return removedPlayer;
        } else {
            // Notify that the queue is empty
            JOptionPane.showMessageDialog(null, "Contract extension queue is empty");
            return null;
        }
    }

    // Method to view the next player in the contract extension queue
    public void viewNextPlayerInContractExtension() {
        // Check if the queue is not empty
        if (!isQueueEmpty()) {
            JOptionPane.showMessageDialog(null, "Next player in the queue is " + celine.getFirst().getName());
        } else {
            // Notify that the queue is empty
            JOptionPane.showMessageDialog(null, "Contract extension queue is empty");
        }
    }

    // Check if the queue is empty
    public boolean isQueueEmpty() {
        return celine.isEmpty();
    }

    // Check if the queue is full
    public boolean isQueueFull() {
        return celine.size() >= capacity;
    }

    // Check if a player is already in the queue
    public boolean isPlayerInQueue(Player player) {
        return celine.contains(player);
    }

    // Get all players currently in the queue
    public LinkedList<Player> getAllPlayersInQueue() {
        return new LinkedList<>(celine);
    }
}
