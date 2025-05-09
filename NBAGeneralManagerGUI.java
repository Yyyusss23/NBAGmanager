package javaapplication1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

public class NBAGeneralManagerGUI extends JFrame {
    // GUI components and related objects
    private JFrame frame;
    private JButton addBtn;
    private JButton remBtn;
    private JButton searchPlayerBtn;
    private JButton injuryManagementBtn;
    private JButton mapBtn;
    private JButton contractExtensionBtn;
    private JButton playerRankingBtn;
    private JButton quitBtn;
    
    // Custom classes for database management and team management
    private SQLiteJDBC_Database database;
    private AddRemovePlayer team;
    private InjuryManagement injuryStack;
    private ContractExtension celine;

    public NBAGeneralManagerGUI() {
        // Initialize database and management objects
        this.database = new SQLiteJDBC_Database();
        this.team = new AddRemovePlayer();
        this.injuryStack = new InjuryManagement();
        this.celine = new ContractExtension(5);
        
        // Set frame properties
        setTitle("NBA General Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null); // Center the frame

        // Title label
        JLabel titleLabel = new JLabel("NBA General Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Manager labels
        JLabel managerLabel1 = new JLabel("1. Yusrina 23004912");
        JLabel managerLabel2 = new JLabel("2. Auni 22001708");
        JLabel managerLabel3 = new JLabel("3. Ainna 22001687");

        // Buttons for various actions
        addBtn = new JButton("Add Player");
        remBtn = new JButton("Remove Player");
        searchPlayerBtn = new JButton("Search Player");
        injuryManagementBtn = new JButton("Injury Management");
        mapBtn = new JButton("Map");
        contractExtensionBtn = new JButton("Contract Extension");
        playerRankingBtn = new JButton("Player Ranking");
        quitBtn = new JButton("Quit");
        
        // Button action listeners
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });

        remBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remPlayer();
            }
        });
        
        searchPlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPlayer();
            }
        });
        
        contractExtensionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contractExtensionManagement();
            }
        });
        
        injuryManagementBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                injuryManagement();
            }
        });
        
        mapBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map();
            }
        });
        
        playerRankingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RankingPlayer();
            }
        });
        
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Main panel layout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add title label to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        mainPanel.add(titleLabel, gbc);

        // Add manager labels to panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(managerLabel1, gbc);

        gbc.gridx = 1;
        mainPanel.add(managerLabel2, gbc);

        gbc.gridx = 2;
        mainPanel.add(managerLabel3, gbc);

        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(searchPlayerBtn, gbc);

        gbc.gridx = 1;
        mainPanel.add(injuryManagementBtn, gbc);

        gbc.gridx = 2;
        mainPanel.add(mapBtn, gbc);

        gbc.gridx = 3;
        mainPanel.add(contractExtensionBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(playerRankingBtn, gbc);

        gbc.gridx = 1;
        mainPanel.add(addBtn, gbc);
        
        gbc.gridx = 2;
        mainPanel.add(remBtn, gbc);
        
        gbc.gridx = 3;
        mainPanel.add(quitBtn, gbc);

        // Add the main panel to the frame
        add(mainPanel);
    }
    
    // Add player from CSV
    private void addPlayer() {
        team.addPlayersFromCSV(team);
    }
    
    // Remove player by name
    private void remPlayer() {
        String playerNameToRemove = JOptionPane.showInputDialog(null, "Enter the name of the player you want to remove (or type 'cancel' to abort):");
        if (playerNameToRemove != null && !playerNameToRemove.isEmpty()) {
            boolean playerRemoved = team.removePlayerByName(playerNameToRemove);
            if (!playerRemoved) {
                JOptionPane.showMessageDialog(null, "Player cannot be removed");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operation cancelled.");
        }
    }
    
    // Method to handle conversion from String to double with error handling
    private static double getDoubleInput(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message);
            if (input == null) {
                return -1; // Indicates cancellation or invalid input
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
    }

    // Method to handle string input for operators with validation
    private static String getOperatorInput(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message);
            if (input == null) {
                return ""; // Indicates cancellation or invalid input
            }
            if (input.equals("=") || input.equals(">=") || input.equals("<=")) {
                return input;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid operator. Please enter one of the following: =, >=, <=");
            }
        }
    }
    
    // Method to handle integer input with validation
    private static int getInput(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message);
            if (input == null) {
                return -1; // Indicates cancellation or invalid input
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
    }

    // Main search method
    private void searchPlayer() {
        double height = getDoubleInput("Please enter height: ");
        if (height == -1) return; // Exit if input is cancelled or invalid
        
        String heightOpt = getOperatorInput("Please enter height operator (=, >=, <=): ");
        if (heightOpt.isEmpty()) return; // Exit if input is cancelled or invalid
        
        double weight = getDoubleInput("Please enter weight: ");
        if (weight == -1) return; // Exit if input is cancelled or invalid
        
        String weightOpt = getOperatorInput("Please enter weight operator (=, >=, <=): ");
        if (weightOpt.isEmpty()) return; // Exit if input is cancelled or invalid
        
        String position = JOptionPane.showInputDialog(null, "Please enter position: ");
        if (position == null || position.isEmpty()) return; // Exit if input is cancelled or invalid
        
        double ppg = getDoubleInput("Please enter PPG: ");
        if (ppg == -1) return; // Exit if input is cancelled or invalid
        
        String ppgOpt = getOperatorInput("Please enter PPG operator (=, >=, <=): ");
        if (ppgOpt.isEmpty()) return; // Exit if input is cancelled or invalid
        
        // Search for players in the database matching the criteria
        List<List<String>> searchResult = database.searchPlayersFromDatabase(height, heightOpt, weight, weightOpt, position, ppg, ppgOpt);
        
        if (searchResult.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No players found matching the search criteria.");
        } else {
            for (List<String> player : searchResult) {
                JOptionPane.showMessageDialog(null, "Player found: " + player.get(0)); // Display player name
            }
        }
    }
    
    // Manage contract extensions
    private void contractExtensionManagement() {
        int choice = getInput("Please enter your choice: \n1 - Add Player to Contract Extension Queue\n2 - Remove Player from Contract Extension Queue\n3 - Display Next Player in Queue\n4 - Display All Player in Queue\n5 - Exit");
        switch (choice) {
            case 1:
                String playerNameAdd = JOptionPane.showInputDialog(frame, "Enter player's name:");
                Player a = team.playerContractExtension(playerNameAdd);
                celine.addPlayerToContractExtension(a);
                break;
                
            case 2:
                celine.removePlayerFromContractExtension();
                break;
                
            case 3:
                celine.viewNextPlayerInContractExtension();
                break;
                
            case 4:
                LinkedList<Player> players = celine.getAllPlayersInQueue();
                if (players.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "The contract extension queue is empty.");
                } else {
                    StringBuilder rosterincontractextensionqueue = new StringBuilder("Players in the contract extension queue:");
                    int i = 1;
                    for (Player player : players) {
                         rosterincontractextensionqueue.append("\n").append(i).append(". ").append(player.getName());
                         i++;
                    }
                    JOptionPane.showMessageDialog(null, rosterincontractextensionqueue.toString());
                }
                break;
                
            case 5:
                JOptionPane.showMessageDialog(null, "Exiting program.");
                return;
                        
            default:
                JOptionPane.showMessageDialog(null, "No such option.");
        }
    }
    
    // Manage injured players
    private void injuryManagement() {
        int choice = getInput("Please enter your choice: \n1 - Add Player to Injury Management\n2 - Remove Player From Injury Management\n3 - Display the Injury Management list\n4 - Exit");
        switch (choice) {
            case 1:
                String playerNameAdd = JOptionPane.showInputDialog(frame, "Enter player's name:");
                Player p = team.playerInjured(playerNameAdd);
                String playerInjury = JOptionPane.showInputDialog(frame, "Enter player's injury:");
                injuryStack.addPlayerToInjuryReserve(p, playerInjury);
                break;
                
            case 2:
                String playerNameRemove = JOptionPane.showInputDialog(frame, "Enter player's name:");
                injuryStack.removePlayerFromInjuryReserve(playerNameRemove);
                break;
                
            case 3:
                injuryStack.displayInjuredPlayers();
                break;
                
            case 4:
                JOptionPane.showMessageDialog(null, "Exiting program.");
                return;
                        
            default:
                JOptionPane.showMessageDialog(null, "No such option.");
        }
    }
    
    // Rank players based on performance
    public void RankingPlayer() {
        SQLiteJDBC_Database one = new SQLiteJDBC_Database();
        
        List<String> allPlayerNames = one.getAllPlayerNamesFromDatabase();
        
        if (allPlayerNames.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No players found in the database.");
            return;
        }
        
        List<Player> allPlayers = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(System.in)) {
            for (String playerName : allPlayerNames) {
                List<Player> playerData = one.getPlayerStatsFromDatabase(playerName);
                if (!playerData.isEmpty()) {
                    double steals = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter steals for player " + playerName + ":"));
                    double blocks = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter blocks for player " + playerName + ":"));
                    PlayerPerformanceRanking r = new PlayerPerformanceRanking(0.0, 0.0);

                    Player player = playerData.get(0);
                    r.setSteals(steals);
                    r.setBlocks(blocks);
                    
                    allPlayers.add(player);
                }
            }

            PlayerPerformanceRanking.rankPlayers(allPlayers, new PlayerPerformanceRanking(0, 0));

        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(null, "Error: No input found. Exiting ranking.");
        }
    }

    // Helper method to read double from scanner with validation
    public static double read2Double(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number:");
            scanner.next();
        }
        return scanner.nextDouble();
    }
    
    public void map() {
        // Create all locations (teams and their respective cities)
        Location spurs = new Location("Spurs", "San Antonio");  // root
        Location warriors = new Location("Warriors", "Golden State");
        Location celtics = new Location("Celtics", "Boston");
        Location heat = new Location("Heat", "Miami");
        Location lakers = new Location("Lakers", "Los Angeles");
        Location suns = new Location("Suns", "Phoenix");
        Location magic = new Location("Magic", "Orlando");
        Location nuggets = new Location("Nuggets", "Denver");
        Location thunder = new Location("Thunder", "Oklahoma City");
        Location rockets = new Location("Rockets", "Houston");

        // Define connections (distances) between locations
        spurs.addLocation(suns, 500);
        spurs.addLocation(thunder, 678);
        spurs.addLocation(rockets, 983);
        spurs.addLocation(magic, 1137);
        suns.addLocation(lakers, 577);
        suns.addLocation(spurs, 500);
        lakers.addLocation(suns, 577);
        lakers.addLocation(thunder, 1901);
        lakers.addLocation(warriors, 554);
        warriors.addLocation(lakers, 554);
        warriors.addLocation(thunder, 2214);
        warriors.addLocation(nuggets, 1507);
        nuggets.addLocation(warriors, 1507);
        nuggets.addLocation(thunder, 942);
        nuggets.addLocation(celtics, 2845);
        celtics.addLocation(nuggets, 2845);
        celtics.addLocation(rockets, 2584);
        celtics.addLocation(heat, 3045);
        rockets.addLocation(celtics, 2584);
        rockets.addLocation(thunder, 778);
        rockets.addLocation(magic, 458);
        rockets.addLocation(spurs, 983);
        thunder.addLocation(spurs, 678);
        thunder.addLocation(lakers, 1901);
        thunder.addLocation(warriors, 2214);
        thunder.addLocation(nuggets, 942);
        thunder.addLocation(rockets, 778);
        magic.addLocation(spurs, 1137);
        magic.addLocation(rockets, 458);
        magic.addLocation(heat, 268);
        heat.addLocation(magic, 268);
        heat.addLocation(celtics, 3045);

        // Array of all locations
        Location[] allLocation = {spurs, suns, lakers, warriors, nuggets, celtics, rockets, thunder, magic, heat};
        Location[] flow = new Location[10]; // Array to store the journey plan

        // Display digital information about all locations before the loop
        digitalInformation(allLocation);
        boolean chooseAlgo = false; // Flag to ensure an algorithm is chosen before inserting a schedule entry

        // Main loop to handle user choices
        while (true) {
            int choice = getInput("1 - Show Digital Info\n2 - Choose Algorithm Search\n3 - Insert Schedule Entry\n4 - View All Schedule Entries\n5 - Delete Schedule Entry\n6 - Quit\n\n Enter a number : ");
            int minDistance; // Variable to store the minimum distance for the shortest path
            switch (choice) {
                case 1:
                    digitalInformation(allLocation); // Show digital information about all locations
                    break;
                case 2:
                    // Construct the journey plan list using different algorithms
                    StringBuilder showD = new StringBuilder("Algorithm Search Journey Plan List : \n");

                    // Breadth-First Search
                    int totalDBreadth = breadthFirstSearch(spurs, allLocation, false, flow);
                    showD.append("\n1 - Breadth First Search :- \n");
                    for (int i = 0; i < flow.length; i++) {
                        showD.append(flow[i].getPlace());
                        if(i < 9) {
                            showD.append(" -> ");
                        }
                    }
                    showD.append("\nTotal Distance : " + totalDBreadth + "\n");

                    // Depth-First Search
                    int totalDDepth = depthFirstSearch(spurs, allLocation, false, flow);
                    showD.append("\n2 - Depth First Search :- \n");
                    for (int i = 0; i < flow.length; i++) {
                        showD.append(flow[i].getPlace());
                        if(i < 9) {
                            showD.append(" -> ");
                        }
                    }
                    showD.append("\nTotal Distance : " + totalDDepth + "\n");

                    // Nearest Neighbor Algorithm
                    int totalDNear = nearestNeighborAlgorithm(spurs, allLocation, false, flow);
                    showD.append("\n3 - Nearest Neighbor Algorithm Search :- \n");
                    for (int i = 0; i < flow.length; i++) {
                        showD.append(flow[i].getPlace());
                        if(i < 9) {
                            showD.append(" -> ");
                        }
                    }
                    showD.append("\nTotal Distance : " + totalDNear + "\n");

                    // Exhaustive Search
                    int totalDEx = exhaustiveSearch(spurs, allLocation, false, flow);
                    showD.append("\n4 - Exhaustive Search :- \n");
                    for (int i = 0; i < flow.length; i++) {
                        showD.append(flow[i].getPlace());
                        if(i < 9) {
                            showD.append(" -> ");
                        }
                    }
                    showD.append("\nTotal Distance : " + totalDEx + "\n");

                    // Prompt to choose the shortest way
                    showD.append("\n5 - Choose the Shortest Way\n\nChoose one algorithm to make a journey : ");

                    StringBuilder journey = new StringBuilder("Journey Plan : \n\n");

                    int algoChoose = getInput(showD.toString()); // Get the user's algorithm choice
                    if(algoChoose == 1) {
                        journey.append("Using Breadth First Search : \n");
                        breadthFirstSearch(spurs, allLocation, false, flow);
                        for (int i = 0; i < flow.length; i++) {
                            journey.append(flow[i].getPlace());
                            if(i < 9) {
                                journey.append(" -> ");
                            }
                        }
                        journey.append("\nTotal Distance : " + totalDBreadth + " km\n");
                        chooseAlgo = true; // Set flag to indicate an algorithm has been chosen
                    }
                    else if(algoChoose == 2) {
                        journey.append("Using Depth First Search : \n");
                        depthFirstSearch(spurs, allLocation, false, flow);
                        for (int i = 0; i < flow.length; i++) {
                            journey.append(flow[i].getPlace());
                            if(i < 9) {
                                journey.append(" -> ");
                            }
                        }
                        journey.append("\nTotal Distance : " + totalDDepth + " km\n");
                        chooseAlgo = true; // Set flag to indicate an algorithm has been chosen
                    }
                    else if(algoChoose == 3) {
                        journey.append("Using Nearest Neighbor Algorithm Search : \n");
                        nearestNeighborAlgorithm(spurs, allLocation, false, flow);
                        for (int i = 0; i < flow.length; i++) {
                            journey.append(flow[i].getPlace());
                            if(i < 9) {
                                journey.append(" -> ");
                            }
                        }
                        journey.append("\nTotal Distance : " + totalDNear + " km\n");
                        chooseAlgo = true; // Set flag to indicate an algorithm has been chosen
                    }
                    else if(algoChoose == 4) {
                        journey.append("Using Exhaustive Search : \n");
                        exhaustiveSearch(spurs, allLocation, false, flow);
                        for (int i = 0; i < flow.length; i++) {
                            journey.append(flow[i].getPlace());
                            if(i < 9) {
                                journey.append(" -> ");
                            }
                        }
                        journey.append("\nTotal Distance : " + totalDEx + " km\n");
                        chooseAlgo = true; // Set flag to indicate an algorithm has been chosen
                    }
                    else if(algoChoose == 5) {
                        chooseAlgo = true;
                        int status = 1;
                        minDistance = totalDBreadth;
                        if(totalDDepth < minDistance) {
                            status = 2;
                            minDistance = totalDDepth;
                        }
                        else if(totalDNear < minDistance) {
                            status = 3;
                            minDistance = totalDNear;
                        }
                        else if(totalDEx < minDistance) {
                            status = 4;
                            minDistance = totalDEx;
                        }

                        journey.append("Using Shortest Total Distance :\n\nThe minimum distance is " + minDistance + " km\n");

                        if (status == 1) {
                            breadthFirstSearch(spurs, allLocation, false, flow);
                            for (int i = 0; i < flow.length; i++) {
                                journey.append(flow[i].getPlace());
                                if(i < 9) {
                                    journey.append(" -> ");
                                }
                            }
                        }

                        if (status == 2) {
                            depthFirstSearch(spurs, allLocation, false, flow);
                            for (int i = 0; i < flow.length; i++) {
                                journey.append(flow[i].getPlace());
                                if(i < 9) {
                                    journey.append(" -> ");
                                }
                            }
                        }

                        if (status == 3) {
                            nearestNeighborAlgorithm(spurs, allLocation, false, flow);
                            for (int i = 0; i < flow.length; i++) {
                                journey.append(flow[i].getPlace());
                                if(i < 9) {
                                    journey.append(" -> ");
                                }
                            }
                        }

                        if (status == 4) {
                            exhaustiveSearch(spurs, allLocation, false, flow);
                            for (int i = 0; i < flow.length; i++) {
                                journey.append(flow[i].getPlace());
                                if(i < 9) {
                                    journey.append(" -> ");
                                }
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, journey.toString()); // Display the journey plan
                    break;
                case 3:
                    if(chooseAlgo) {
                        insertScheduleEntry(flow); // Insert a schedule entry based on the chosen algorithm
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please choose one algorithm journey first");
                    }
                    break;
                case 4:
                    database.viewAllScheduleEntries(); // View all schedule entries from the database
                    break;
                case 5:
                    deleteScheduleEntry(); // Delete a specific schedule entry
                    break;
                case 6:
                    return; // Exit the program
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again."); // Handle invalid choice
            }
        }
    }

    // Displays the digital information of all locations and their connections
    private static void digitalInformation(Location[] allLocation) {
        // Create a string to store information about places and their teams
        StringBuilder listplace = new StringBuilder("List of all the place and team name :-");
        for (int i = 0; i < allLocation.length; i++) {
            listplace.append("\n").append(i + 1).append(". ").append(allLocation[i].getPlace()).append(" [Team : ").append(allLocation[i].getTeam()).append("]");
        }

        listplace.append("\n\n");

        // Add information about connections and distances
        listplace.append("The list of place that you can go from one place to another connected place with its distance :");
        for (int i = 0; i < allLocation.length; i++) {
            listplace.append("\n").append(i + 1).append(". ").append(allLocation[i].getPlace());
            ArrayList<Distance> nextPlace = allLocation[i].getList();
            listplace.append(" >> ");

            // List all connections for the current location
            for (int j = 0; j < nextPlace.size(); j++) {
                listplace.append(" [").append(nextPlace.get(j).getConnectLocation().getPlace()).append(" , Distance = ").append(nextPlace.get(j).getDistance()).append("]");
            }
        }

        // Show the information in a dialog box
        JOptionPane.showMessageDialog(null, listplace.toString());
    }

    // Implements the Breadth-First Search algorithm to find the shortest path in terms of connections
    public static int breadthFirstSearch(Location firstLocation, Location[] allLocation, boolean printLocationOrder, Location[] flow) {
        int totalDistance = 0;
        ArrayList<Location> locationOrder = new ArrayList<>(); // To store the order of locations visited
        locationOrder.add(firstLocation);

        Queue<Location> queue = new LinkedList<>();
        queue.offer(firstLocation); // Add the first location to the queue

        // List of locations excluding the first location
        ArrayList<Location> locationList = new ArrayList<>();
        for (int i = 1; i < allLocation.length; i++) {
            locationList.add(allLocation[i]);
        }

        // BFS loop
        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            ArrayList<Distance> listNextLocation = currentLocation.getList();

            // Iterate through the list of next locations
            for (Distance distance : listNextLocation) {
                Location nextLocation = distance.getConnectLocation();

                // Check if the next location is connected and not already visited
                if (!locationOrder.contains(nextLocation) && currentLocation.isConnected(nextLocation)) {
                    queue.offer(nextLocation); // Add the next location to the queue
                    locationOrder.add(nextLocation); // Mark it as visited
                    totalDistance += distance.getDistance(); // Update the total distance
                }
            }
        }

        // Optionally print the location order and total distance
        if (printLocationOrder) {
            StringBuilder locationorder = new StringBuilder("Using Breadth First Search\nLocation Order : ");
            for (int i = 0; i < locationOrder.size(); i++) {
                locationorder.append(locationOrder.get(i).getTeam());
                if (i < 9) {
                    locationorder.append(" -> ");
                }
            }
            locationorder.append("\n Total Distance : ").append(totalDistance);
            JOptionPane.showMessageDialog(null, locationorder.toString());
        }

        // Update the flow array with the order of locations
        for (int i = 0; i < 10; i++) {
            flow[i] = locationOrder.get(i);
        }

        return totalDistance;
    }


    // Implements the Depth-First Search algorithm to find a path through the locations
    public static int depthFirstSearch(Location firstLocation, Location[] allLocations, boolean printLocationOrder, Location[] flow) {
        int totalDistance = 0;
        ArrayList<Location> locationOrder = new ArrayList<>(); // To store the order of locations visited
        locationOrder.add(firstLocation);

        // List of locations excluding the first location
        ArrayList<Location> locationList = new ArrayList<>();
        for (int i = 1; i < allLocations.length; i++) {
            locationList.add(allLocations[i]);
        }

        Location currentLocation = firstLocation;

        // DFS loop
        while (!locationList.isEmpty()) {
            Location nextLocation = findNextLocation(currentLocation, locationList);
            if (nextLocation != null) {
                totalDistance += nextLocation.getDistanceWith(currentLocation); // Update the total distance
                locationOrder.add(nextLocation); // Mark it as visited
                locationList.remove(nextLocation); // Remove from the list of remaining locations
                currentLocation = nextLocation; // Move to the next location
            } else {
                break; // No more connected locations
            }
        }

        // Optionally print the location order and total distance
        if (printLocationOrder) {
            StringBuilder locationorder = new StringBuilder("Using Depth First Search\nLocation Order : ");
            for (int i = 0; i < locationOrder.size() - 1; i++) {
                locationorder.append(locationOrder.get(i).getTeam());
                if (i < 9) {
                    locationorder.append(" -> ");
                }
            }
            locationorder.append("\n Total Distance : ").append(totalDistance);
            JOptionPane.showMessageDialog(null, locationorder.toString());
        }

        // Update the flow array with the order of locations
        for (int i = 0; i < 10; i++) {
            flow[i] = locationOrder.get(i);
        }

        return totalDistance;
    }

    // Helper method to find the next location for DFS
    private static Location findNextLocation(Location currentLocation, ArrayList<Location> locationList) {
        ArrayList<Distance> listNextLocation = currentLocation.getList();
        for (Distance distance : listNextLocation) {
            Location nextLocation = distance.getConnectLocation();
            if (locationList.contains(nextLocation)) {
                return nextLocation;
            }
        }
        return null; // No connected location found
    }

    // Implements the Nearest Neighbor algorithm to find a path through the locations
    public static int nearestNeighborAlgorithm(Location firstLocation, Location[] allLocation, boolean printLocationOrder, Location[] flow) {
        int totalDistance = 0;
        ArrayList<Location> locationOrder = new ArrayList<>(); // To store the order of locations visited
        locationOrder.add(firstLocation);

        // List of locations excluding the first location
        ArrayList<Location> locationList = new ArrayList<>();
        for (int i = 1; i < allLocation.length; i++) {
            locationList.add(allLocation[i]);
        }

        Location currentLocation = firstLocation;

        // Nearest Neighbor loop
        while (!locationList.isEmpty()) {
            ArrayList<Distance> ListNextLocation = currentLocation.getList();
            Location nearestLocation = null;
            int minDistance = Integer.MAX_VALUE;

            // Find the nearest location
            for (Distance distance : ListNextLocation) {
                int d = distance.getDistance();
                if (d < minDistance && locationList.contains(distance.getConnectLocation())) {
                    minDistance = d;
                    nearestLocation = distance.getConnectLocation();
                }
            }

            if (nearestLocation != null) {
                totalDistance += minDistance; // Update the total distance
                locationOrder.add(nearestLocation); // Mark it as visited
                locationList.remove(nearestLocation); // Remove from the list of remaining locations
                currentLocation = nearestLocation; // Move to the next location
            }
        }

        // Optionally print the location order and total distance
        if (printLocationOrder) {
            StringBuilder locationorder = new StringBuilder("Using Nearest Neighbor Algorithm\nLocation Order : ");
            for (int i = 0; i < locationOrder.size() - 1; i++) {
                locationorder.append(locationOrder.get(i).getTeam());
                if (i < 9) {
                    locationorder.append(" -> ");
                }
            }
            locationorder.append("\n Total Distance : ").append(totalDistance);
            JOptionPane.showMessageDialog(null, locationorder.toString());
        }

        // Update the flow array with the order of locations
        for (int i = 0; i < 10; i++) {
            flow[i] = locationOrder.get(i);
        }

        return totalDistance;
    }

    // Implements the Exhaustive Search algorithm to find the optimal path through the locations
    public static int exhaustiveSearch(Location firstLocation, Location[] allLocation, boolean print, Location[] flow) {
        int totalDistance = Integer.MAX_VALUE;
        ArrayList<Location> bestPath = new ArrayList<>();

        // List of remaining locations excluding the first location
        Location[] remainingLocations = new Location[allLocation.length - 1];
        for (int i = 1; i < allLocation.length; i++) {
            remainingLocations[i - 1] = allLocation[i];
        }

        // Generate all permutations of remaining locations
        ArrayList<ArrayList<Location>> permutations = new ArrayList<>();
        generatePermutations(new ArrayList<>(), remainingLocations, permutations);

        // Calculate total distance for each permutation
        for (ArrayList<Location> permutation : permutations) {
            permutation.add(0, firstLocation); // Add the first location to the beginning

            if (isValidPath(permutation)) {
                int distance = calculateTotalDistance(firstLocation, permutation);
                if (distance < totalDistance) {
                    totalDistance = distance;
                    bestPath = permutation;
                }
            }
        }

        // Optionally print the location order and total distance
        if (print) {
            StringBuilder locationorder = new StringBuilder("Using Exhaustive Search\nLocation Order : ");
            for (int i = 0; i < bestPath.size() - 1; i++) {
                locationorder.append(bestPath.get(i).getTeam());
                if (i < 9) {
                    locationorder.append(" -> ");
                }
            }
            locationorder.append("\n Total Distance : ").append(totalDistance);
            JOptionPane.showMessageDialog(null, locationorder.toString());
        }

        // Update the flow array with the order of locations
        for (int i = 0; i < 10; i++) {
            flow[i] = bestPath.get(i);
        }

        return totalDistance;
    }

    // Calculates the total distance for a given path
    private static int calculateTotalDistance(Location firstLocation, ArrayList<Location> permutation) {
        int totalDistance = 0;
        Location currentLocation = firstLocation;
        for (int i = 0; i < permutation.size(); i++) {
            Location nextLocation = permutation.get(i);
            totalDistance += currentLocation.getDistanceWith(nextLocation);
            currentLocation = nextLocation;
        }
        totalDistance += currentLocation.getDistanceWith(firstLocation); // Return to the starting location
        return totalDistance;
    }

    // Generates all permutations of the remaining locations
    private static void generatePermutations(ArrayList<Location> current, Location[] remaining, ArrayList<ArrayList<Location>> permutations) {
        if (remaining.length == 0) {
            permutations.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < remaining.length; i++) {
            Location[] newRemaining = new Location[remaining.length - 1];
            int newIndex = 0;
            for (int j = 0; j < remaining.length; j++) {
                if (j != i) {
                    newRemaining[newIndex++] = remaining[j];
                }
            }
            ArrayList<Location> newCurrent = new ArrayList<>(current);
            newCurrent.add(remaining[i]);
            generatePermutations(newCurrent, newRemaining, permutations);
        }
    }

    // Checks if the given path is valid (i.e., all locations are connected)
    private static boolean isValidPath(ArrayList<Location> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            Location current = path.get(i);
            Location next = path.get(i + 1);
            if (!current.isConnected(next)) {
                return false;
            }
        }
        return true;
    }

    // Inserts a schedule entry for each location in the journey flow
    public void insertScheduleEntry(Location[] flow) {
        StringBuilder journeyFlow = new StringBuilder("The journey flow : ");
        for (int i = 0; i < flow.length; i++) {
            journeyFlow.append(flow[i].getPlace());
            if (i < 9) {
                journeyFlow.append(" -> ");
            }
        }
        JOptionPane.showMessageDialog(null, journeyFlow.toString());

        int n = 0;
        try {
            while (true) {
                StringBuilder flowInfo = new StringBuilder("City : " + flow[n].getPlace() + "[Team : " + flow[n].getTeam() + "]");
                String city = flow[n].getPlace();

                if (n > 0) {
                    flowInfo.append("\nPrevious city : ").append(flow[n - 1].getPlace()).append("[Team : ").append(flow[n].getTeam()).append("]");
                    flowInfo.append("\nDistance from ").append(flow[n - 1].getPlace()).append(" to ").append(flow[n].getPlace()).append(" : ").append(flow[n].getDistanceWith(flow[n - 1])).append(" km");
                }

                String date = JOptionPane.showInputDialog(frame, flowInfo.toString() + "\n\nEnter date for the game in " + flow[n].getPlace() + " (or = to exit) : ");

                database.insertScheduleEntry(date, city);

                if (date.equals("=") || n == 9) {
                    JOptionPane.showMessageDialog(null, "Exiting insertScheduleEntry...");
                    break;
                }
                n++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error adding schedule entry: " + e.getMessage());
        }
    }

    // Deletes a schedule entry based on the provided date and city
    private void deleteScheduleEntry() {
        String date = JOptionPane.showInputDialog(frame, "Enter Date (DD-MM-YYYY): ");
        String city = JOptionPane.showInputDialog(frame, "Enter City: ");
        database.deleteScheduleEntry(date, city);
    }

    public static void main(String[] args) {
    // Create an instance of the database handler
    SQLiteJDBC_Database database = new SQLiteJDBC_Database();

    // Create the necessary tables in the database
    database.createPlayerTable();           // Creates the player table in the database
    database.createContractExtensionTable(); // Creates the contract extension table in the database
    database.createScheduleTable();          // Creates the schedule table in the database
    database.createInjuredPlayersTable();    // Creates the injured players table in the database

    // Use SwingUtilities to ensure that the GUI creation is done on the Event Dispatch Thread
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            // Create an instance of the NBAGeneralManagerGUI and make it visible
            new NBAGeneralManagerGUI().setVisible(true);
        }
    });
    }


    // Reads a double value from the user through the console, ensuring the input is valid
    private static double readDouble(Scanner scanner) {
        double value = 0.0;
        boolean isValid = false;

        // Loop until a valid double value is entered
        while (!isValid) {
            try {
                // Attempt to parse the next line of input as a double
                value = Double.parseDouble(scanner.nextLine());
                isValid = true; // If parsing is successful, set isValid to true to exit the loop
            } catch (NumberFormatException e) {
                // If a NumberFormatException occurs, show an error message to the user
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid numeric value.");
                //System.out.println("Invalid input. Please enter a valid numeric value.");
            }
        }

        return value; // Return the valid double value
    }


    // Reads an integer value from the user through the console, ensuring the input is valid
    private static double readInt(Scanner scanner) {
        int value = 0;
        boolean isValid = false;

        // Loop until a valid integer value is entered
        while (!isValid) {
            try {
                // Attempt to parse the next line of input as an integer
                value = Integer.parseInt(scanner.nextLine());
                isValid = true; // If parsing is successful, set isValid to true to exit the loop
            } catch (NumberFormatException e) {
                // If a NumberFormatException occurs, show an error message to the user
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid numeric value.");
                //System.out.println("Invalid input. Please enter a valid numeric value.");
            }
        }

        return value; // Return the valid integer value
    }

}
