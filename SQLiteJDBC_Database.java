package javaapplication1;

import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class SQLiteJDBC_Database {
    // Database URL
    private static final String DATABASE_URL = "jdbc:sqlite:WHAT.db";
    // Connection object
    private static Connection connection;

    // Method to connect to the database
    public static Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DATABASE_URL);
            } catch (ClassNotFoundException e) {
                System.err.println("Failed to load SQLite JDBC driver");
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Method to create the Players table
    public static void createPlayerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Players (" +
                     "Name TEXT PRIMARY KEY, " +
                     "Height REAL, " +
                     "Weight REAL, " +
                     "Position TEXT, " +
                     "PPG REAL, " +
                     "RPG REAL, " +
                     "APG REAL, " +
                     "PIE REAL, " +
                     "Salary REAL)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating player table: " + e.getMessage());
        }
    }

    // Method to insert a player into the Players table
    public static void insertPlayerIntoDatabase(Player player) {
        String sql = "INSERT INTO Players (Name, Height, Weight, Position, PPG, RPG, APG, PIE, Salary) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setDouble(2, player.getHeight());
            pstmt.setDouble(3, player.getWeight());
            pstmt.setString(4, player.getPosition());
            pstmt.setDouble(5, player.getPpg());
            pstmt.setDouble(6, player.getRpg());
            pstmt.setDouble(7, player.getApg());
            pstmt.setDouble(8, player.getPie());
            pstmt.setDouble(9, player.getSalary());
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Player " + player.getName() + " added to the database.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding player to the database: " + e.getMessage());
        }
    }

    // Method to remove a player from the Players table
    public static void removePlayer(String playerName) {
        String sql = "DELETE FROM Players WHERE Name = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Player " + playerName + " removed from the database.");
            } else {
                JOptionPane.showMessageDialog(null, "Player " + playerName + " not found in the database.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error removing player: " + e.getMessage());
        }
    }

    // Method to search players from the database based on certain criteria
    public List<List<String>> searchPlayersFromDatabase(double height, String heightOperator, double weight, String weightOperator, String position, double ppg, String ppgOperator) {
        List<List<String>> searchResult = new ArrayList<>();
        String sql = "SELECT * FROM Players WHERE ";

        // Constructing SQL query based on user inputs
        sql += "Height " + heightOperator + " " + height + " AND ";
        sql += "Weight " + weightOperator + " " + weight + " AND ";
        sql += "Position = '" + position + "' AND ";
        sql += "PPG " + ppgOperator + " " + ppg;

        try (Connection conn = SQLiteJDBC_Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Iterating through the result set and adding player data to the search result list
            while (rs.next()) {
                List<String> player = new ArrayList<>();
                player.add(rs.getString("Name"));
                player.add(rs.getString("Height"));
                player.add(rs.getString("Weight"));
                player.add(rs.getString("Position"));
                player.add(rs.getString("PPG"));
                player.add(rs.getString("RPG"));
                player.add(rs.getString("APG"));
                player.add(rs.getString("PIE"));
                player.add(rs.getString("Salary"));
                searchResult.add(player);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching players: " + e.getMessage());
        }
        return searchResult;
    }

    // Method to create the InjuredPlayers table
    public static void createInjuredPlayersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS InjuredPlayers (" +
                     "Name TEXT PRIMARY KEY, " +
                     "Injury TEXT)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating injured players table: " + e.getMessage());
        }
    }

    // Method to add a player to the injury reserve
    public static void addPlayerToInjuryReserve(Player p, String problem) {
        String sql = "INSERT INTO InjuredPlayers (Name, Injury) VALUES (?, ?)";
    
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.setString(2, problem);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Player " + p.getName() + " added to the injury reserve.");
            
            // Insert the player into the Players table if not already present
            if (!isPlayerInDatabase(p.getName())) {
                insertPlayerIntoDatabase(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding player to the injury reserve: " + e.getMessage());
        }
    }

    // Method to check if a player is already in the database
    public static boolean isPlayerInDatabase(String playerName) {
        String sql = "SELECT COUNT(*) AS count FROM Players WHERE Name = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();
            int count = rs.getInt("count");
            return count > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error checking player existence: " + e.getMessage());
            return false;
        }
    }

    // Method to remove a player from the injury reserve in the database
    public void removePlayerFromInjuryReserveDatabase(String playerName) {
        String sql = "DELETE FROM InjuredPlayers WHERE Name = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Player " + playerName + " removed from the injury reserve in the database.");
            } else {
                JOptionPane.showMessageDialog(null, "Player " + playerName + " not found in the injury reserve in the database.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error removing player from the injury reserve in the database: " + e.getMessage());
        }
    }

    // Method to get player statistics from the database
    public static List<Player> getPlayerStatsFromDatabase(String playerName) {
        List<Player> playerData = new ArrayList<>();
        String sql = "SELECT * FROM Players WHERE Name = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Player player = new Player();
                player.setName(playerName);
                player.setPosition(rs.getString("Position"));
                player.setPpg(rs.getDouble("PPG"));
                player.setRpg(rs.getDouble("RPG"));
                player.setApg(rs.getDouble("APG"));
                playerData.add(player);
            } else {
                JOptionPane.showMessageDialog(null, "Player " + playerName + " is not in the database.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving player stats: " + e.getMessage());
        }
        return playerData;
    }

    // Method to get all player names from the database
    public static List<String> getAllPlayerNamesFromDatabase() {
        List<String> allPlayerNames = new ArrayList<>();
        String sql = "SELECT Name FROM Players";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                allPlayerNames.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving player names from database: " + e.getMessage());
        }
        return allPlayerNames;
    }

    // Method to create the ContractExtension table
    public static void createContractExtensionTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ContractExtension (" +
                     "PlayerName TEXT PRIMARY KEY)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating contract extension table: " + e.getMessage());
        }
    }

    // Method to insert a player into the contract extension table
    public static void insertPlayerIntoContractExtensionTable(String playerName) {
        String sql = "INSERT INTO ContractExtension (PlayerName) VALUES (?)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Player " + playerName + " added to the contract extension queue.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding player to the contract extension queue: " + e.getMessage());
        }
    }

    // Method to remove a player from the contract extension table
    public static void removePlayerFromContractExtensionTable(String playerName) {
        String sql = "DELETE FROM ContractExtension WHERE PlayerName = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Player " + playerName + " removed from the contract extension queue.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error removing player from the contract extension queue: " + e.getMessage());
        }
    }

    // Method to retrieve all players from the contract extension table
    public static List<String> getAllPlayersFromContractExtensionTable() {
        List<String> playerNames = new ArrayList<>();
        String sql = "SELECT PlayerName FROM ContractExtension";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                playerNames.add(rs.getString("PlayerName"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving players from the contract extension queue: " + e.getMessage());
        }
        return playerNames;
    }

    // Method to create the Schedule table
    public static void createScheduleTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Schedule (" +
                     "Date TEXT, " +
                     "City TEXT )";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating schedule table: " + e.getMessage());
        }
    }

    // Method to insert a schedule entry
    public static void insertScheduleEntry(String date, String city) {
        String sql = "INSERT INTO Schedule (Date, City) VALUES (?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setString(2, city);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Schedule entry added: " + date + ", " + city);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding schedule entry: " + e.getMessage());
        }
    }

    // Method to view all schedule entries
    public static void viewAllScheduleEntries() {
        String sql = "SELECT * FROM Schedule";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            StringBuilder schedule = new StringBuilder("Schedule :- \n");
            while (rs.next()) {
                schedule.append(rs.getString("Date")).append(" : ").append(rs.getString("City")).append("\n");
            }
            JOptionPane.showMessageDialog(null, schedule.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving schedule entries: " + e.getMessage());
        }
    }

    // Method to delete a schedule entry
    public static void deleteScheduleEntry(String date, String city) {
        String sql = "DELETE FROM Schedule WHERE Date = ? AND City = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setString(2, city);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Schedule entry deleted: " + date + ", " + city);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting schedule entry: " + e.getMessage());
        }
    }
}
