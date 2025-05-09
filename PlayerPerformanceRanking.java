package javaapplication1;

import java.util.List;
import javax.swing.JOptionPane;

public class PlayerPerformanceRanking {
    // Attributes for steals and blocks
    private double steals; // Number of steals
    private double blocks; // Number of blocks

    // Constructor to initialize steals and blocks
    public PlayerPerformanceRanking(double steals, double blocks) {
        this.steals = steals;
        this.blocks = blocks;
    }

    // Setter method for steals
    public void setSteals(double steals) {
        this.steals = steals;
    }

    // Setter method for blocks
    public void setBlocks(double blocks) {
        this.blocks = blocks;
    }

    // Getter method for steals
    public double getSteals() {
        return steals;
    }

    // Getter method for blocks
    public double getBlocks() {
        return blocks;
    }

    // Method to rank players based on their composite score
    public static void rankPlayers(List<Player> players, PlayerPerformanceRanking r) {
        // Sort players based on their composite score in descending order
        players.sort((p1, p2) -> {
            double compositeScore1 = calculateCompositeScore(p1, r);
            double compositeScore2 = calculateCompositeScore(p2, r);
            return Double.compare(compositeScore2, compositeScore1); // Descending order
        });

        // Display player name, composite score, and rank using JOptionPane
        StringBuilder playerDetails = new StringBuilder("Rank           Name            Composite Score ");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            double compositeScore = calculateCompositeScore(player, r);
            playerDetails.append("\n").append((i + 1)).append(".  ").append("      ").append(player.getName()).append("   ").append("      ").append(compositeScore);
        }
        JOptionPane.showMessageDialog(null, playerDetails.toString());
    }

    // Method to calculate the composite score of a player
    public static double calculateCompositeScore(Player p, PlayerPerformanceRanking r) {
        // Define default weights for different performance metrics
        double pointsWeight = 1.0;
        double reboundsWeight = 1.0;
        double stealsWeight = 1.0;
        double assistsWeight = 1.0;
        double blocksWeight = 1.0;

        // Adjust weights based on player's position
        String position = p.getPosition();
        if (position.equalsIgnoreCase("G") || position.equalsIgnoreCase("F-G") || position.equalsIgnoreCase("C-G")) {
            pointsWeight = 1.0;
            reboundsWeight = 1.0;
            stealsWeight = 2.0;
            assistsWeight = 2.0;
            blocksWeight = 1.0;
        } else if (position.equalsIgnoreCase("F") || position.equalsIgnoreCase("F-G") || position.equalsIgnoreCase("F-C")) {
            pointsWeight = 1.0;
            reboundsWeight = 2.0;
            stealsWeight = 1.0;
            assistsWeight = 1.0;
            blocksWeight = 1.0;
        } else if (position.equalsIgnoreCase("C") || position.equalsIgnoreCase("F-C") || position.equalsIgnoreCase("C-G")) {
            pointsWeight = 1.0;
            reboundsWeight = 2.0;
            stealsWeight = 1.0;
            assistsWeight = 1.0;
            blocksWeight = 2.0;
        }

        // Calculate composite score using weighted sum of performance metrics
        double compositeScore = (p.getPpg() * pointsWeight) +
                                (p.getRpg() * reboundsWeight) +
                                (r.getSteals() * stealsWeight) +
                                (p.getApg() * assistsWeight) +
                                (r.getBlocks() * blocksWeight);

        return compositeScore;
    }
}
