package javaapplication1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class NBAMap {
    public static void main(String[] args) {
        //create all location
        Location spurs = new Location("Spurs", "San Antonio");  //root
        Location warriors = new Location("Warriors", "Golden State");
        Location celtics = new Location("Celtics", "Boston");
        Location heat = new Location("Heat", "Miami");
        Location lakers = new Location("Lakers", "Los Angeles");
        Location suns = new Location("Suns", "Phoenix");
        Location magic = new Location("Magic", "Orlando");
        Location nuggets = new Location("Nuggets", "Denver");
        Location thunder = new Location("Thunder", "Oklahoma City");
        Location rockets = new Location("Rockets", "Houston");
        
        //Location that spurs connect
        spurs.addLocation(suns, 500);
        spurs.addLocation(thunder, 678);
        spurs.addLocation(rockets, 983);
        spurs.addLocation(magic, 1137);
        
        //Location that suns connect
        suns.addLocation(lakers, 577);
        suns.addLocation(spurs, 500);
        
        //Location that lakers connect
        lakers.addLocation(suns, 577);
        lakers.addLocation(thunder, 1901);
        lakers.addLocation(warriors, 554);
        
        //Location that warriors connect
        warriors.addLocation(lakers, 554);
        warriors.addLocation(thunder, 2214);
        warriors.addLocation(nuggets, 1507);
        
        //Location that nuggets connect
        nuggets.addLocation(warriors, 1507);
        nuggets.addLocation(thunder, 942);
        nuggets.addLocation(celtics, 2845);
        
        //Location that celtics connect
        celtics.addLocation(nuggets, 2845);
        celtics.addLocation(rockets, 2584);
        celtics.addLocation(heat, 3045);
        
        //Location that rockets connect
        rockets.addLocation(celtics, 2584);
        rockets.addLocation(thunder, 778);
        rockets.addLocation(magic, 458);
        rockets.addLocation(spurs, 1137);
        
        //Location that thunder connect
        thunder.addLocation(spurs, 678);
        thunder.addLocation(lakers, 1901);
        thunder.addLocation(warriors, 2214);
        thunder.addLocation(nuggets, 942);
        thunder.addLocation(rockets, 778);
        
        //Location that magic connect
        magic.addLocation(spurs, 1137);
        magic.addLocation(rockets, 458);
        magic.addLocation(heat, 268);
        
        //Location that heat connect
        heat.addLocation(magic, 268);
        heat.addLocation(celtics, 3045);
        
        //the vertex
        Location[] allLocation = {spurs, suns, lakers, warriors, nuggets, celtics, rockets, thunder, magic, heat};
        Location[] flow = new Location[10];
        
        Scanner sc = new Scanner(System.in);
        
        int choice = getInput("1 - Show Digital Info\n2 - Breadth First Search\n3 - Depth First Search\n4 - Nearest Neighbor Algorithm"
        + "\n5 - Exhaustive Search\n6 - Show The Shortest Way\n7 - Insert Schedule Entry\n8 - View All Schedule Entries\n9 - Delete Schedule Entry]\n10 - quit\n Enter a number : ");

        int totalD;
        
        whole:
        while(true) {
            switch(choice) {
                case 1 :
                    digitalInformation(allLocation);
                    break;
                case 2 :
                    totalD = breadthFirstSearch(spurs, allLocation, true, flow);
                    JOptionPane.showMessageDialog(null, "Total Distance : " + totalD + "\n");
                
                    break;
                case 3 :
                    totalD = depthFirstSearch(spurs, allLocation, true, flow);
                    JOptionPane.showMessageDialog(null, "Total Distance : " + totalD + "\n");
                    break;
                case 4 :
                    totalD = nearestNeighborAlgorithm(spurs, allLocation, true, flow);
                    JOptionPane.showMessageDialog(null, "Total Distance : " + totalD + "\n");
                    break;
                case 5 :
                    totalD = exhaustiveSearch(spurs, allLocation, true, flow);
                    JOptionPane.showMessageDialog(null, "Total Distance : " + totalD + "\n");
                    break;
                case 6:
                    int status = 1;
                    int minDistance = breadthFirstSearch(spurs, allLocation, true, flow);
                    JOptionPane.showMessageDialog(null, "Total Distance : " + minDistance);                    
                    
                    if (depthFirstSearch(spurs, allLocation, false, flow)<minDistance) {
                        minDistance = depthFirstSearch(spurs, allLocation, false, flow);
                        status = 2;
                    }
                    minDistance = depthFirstSearch(spurs, allLocation, true, flow);
                    JOptionPane.showMessageDialog(null, "Total Distance : " + minDistance);                    
                    
                    if (nearestNeighborAlgorithm(spurs, allLocation, false, flow)<minDistance) {
                        minDistance = nearestNeighborAlgorithm(spurs, allLocation, false, flow);
                        status = 4;
                    }
                    minDistance = nearestNeighborAlgorithm(spurs, allLocation, true, flow);
                    JOptionPane.showMessageDialog(null, "Total Distance : " + minDistance);
                    
                    if (exhaustiveSearch(spurs, allLocation, false, flow)<minDistance) {
                        minDistance = exhaustiveSearch(spurs, allLocation, false, flow);
                        status = 3;
                    }
                    minDistance = exhaustiveSearch(spurs, allLocation, true, flow);
                    JOptionPane.showMessageDialog(null, "Total Distance : " + minDistance);
                    
                    String message = "\nThe minimum distance is " + minDistance + " km by using :-";
                    String optionPaneMessage = message;

                    JOptionPane.showMessageDialog(null, message);

                    if (status == 1 || minDistance == breadthFirstSearch(spurs, allLocation, false, flow)) {
                        totalD = breadthFirstSearch(spurs, allLocation, true, flow);
                        JOptionPane.showMessageDialog(null, "Total Distance : " + totalD);
                        optionPaneMessage += "\nBreadth First Search";
                    }

                    if (status == 2 || minDistance == depthFirstSearch(spurs, allLocation, false, flow)) {
                        totalD = depthFirstSearch(spurs, allLocation, true, flow);
                        JOptionPane.showMessageDialog(null, "Total Distance : " + totalD);
                        optionPaneMessage += "\nDepth First Search";
                    }

                    if (status == 3 || minDistance == exhaustiveSearch(spurs, allLocation, false, flow)) {
                        totalD = nearestNeighborAlgorithm(spurs, allLocation, true, flow);
                        JOptionPane.showMessageDialog(null, "Total Distance : " + totalD);
                        optionPaneMessage += "\nExhaustive Search";
                    }

                    if (status == 4 || minDistance == nearestNeighborAlgorithm(spurs, allLocation, false, flow)) {
                        totalD = exhaustiveSearch(spurs, allLocation, true, flow);
                        JOptionPane.showMessageDialog(null, "Total Distance : " + totalD);
                        optionPaneMessage += "\nNearest Neighbor Algorithm";
                    }

                    JOptionPane.showMessageDialog(null, optionPaneMessage);
    
                    break;
                case 7 :
                    break whole;
            }
        }
        
        StringBuilder quit = new StringBuilder("");
        for (int i = 0; i < flow.length - 1; i++) {
            quit.append(flow[i].getPlace()).append(" -> ");

        }
        JOptionPane.showMessageDialog(null, quit.toString());
        JOptionPane.showMessageDialog(null, flow[9].getPlace());
        
        //update to SQL - place and team
    }
    
    private static void digitalInformation(Location[] allLocation) {
        StringBuilder place = new StringBuilder("List of all the place and team name :-");
        for (int i = 0; i < allLocation.length; i++) {
            place.append("\n").append(i+1).append(". ").append(allLocation[i].getPlace()).append(" [Team : ").append(allLocation[i].getTeam()).append("]");  
        }
        
        JOptionPane.showMessageDialog(null, place.toString());
        
        StringBuilder listplace = new StringBuilder("The list of place that you can go from one place to another connected place with its distance :");
        for (int i = 0; i < allLocation.length; i++) {
            listplace.append("\n").append(i+1).append(". ").append(allLocation[i].getPlace()).append(" [Team : ").append(allLocation[i].getTeam()).append("]");
            ArrayList<Distance> nextPlace = allLocation[i].getList();
            
            StringBuilder distance = new StringBuilder("Distance : ");
            for (int j = 0; j < nextPlace.size(); j++) {
                distance.append("\n").append("(" + (j+1) + ") ").append(nextPlace.get(j).getConnectLocation().getPlace()).append(" , Distance = " + nextPlace.get(j).getDistance());
            }
            JOptionPane.showMessageDialog(null, distance.toString());
        }
        JOptionPane.showMessageDialog(null, listplace.toString());
    }
    
    public static int breadthFirstSearch(Location firstLocation, Location[] allLocation, boolean printLocationOrder, Location[] flow) {
        int totalDistance = 0;
        ArrayList<Location> locationOrder = new ArrayList<>(); // Show location order
        locationOrder.add(firstLocation);

        Queue<Location> queue = new LinkedList<>();
        queue.offer(firstLocation); // Add the first location to the queue

        ArrayList<Location> locationList = new ArrayList<>();
        for (int i = 1; i < allLocation.length; i++) { // Not including the first location
            locationList.add(allLocation[i]);
        }

        while (!queue.isEmpty()) {
            Location currentLocation = queue.poll();
            ArrayList<Distance> listNextLocation = currentLocation.getList();
            
            // Iterate through the list of next locations
            for (Distance distance : listNextLocation) {
                Location nextLocation = distance.getConnectLocation();
                
                // Check if the next location is connected and not already visited
                if (!locationOrder.contains(nextLocation) && currentLocation.isConnected(nextLocation)) {
                    
                    // Add the next location to the queue and mark it as visited
                    queue.offer(nextLocation);
                    locationOrder.add(nextLocation);
                    
                    // Update the total distance
                    totalDistance += distance.getDistance();
                }
            }
        }

        if(printLocationOrder) {
            // Print location order
            StringBuilder locationorder = new StringBuilder("Using Breadth First Search\nLocation Order : ");
            for (int i = 0; i < locationOrder.size()-1; i++) {
                locationorder.append(locationOrder.get(i).getTeam()).append(" -> ");
            }
            JOptionPane.showMessageDialog(null, locationorder.toString());
            JOptionPane.showMessageDialog(null, locationOrder.get(locationOrder.size()-1).getTeam());
        }
        
        for (int i = 0; i < 10; i++) {
            flow[i] = locationOrder.get(i);
        }
        
        return totalDistance;
    }
    
    public static int depthFirstSearch(Location firstLocation, Location[] allLocations, boolean printLocationOrder, Location[] flow) {
        int totalDistance = 0;
        ArrayList<Location> locationOrder = new ArrayList<>(); // Show location order
        locationOrder.add(firstLocation);

        ArrayList<Location> locationList = new ArrayList<>();
        for (int i = 1; i < allLocations.length; i++) { // not include the first location
            locationList.add(allLocations[i]);
        }

        Location currentLocation = firstLocation;

        while (!locationList.isEmpty()) {
            Location nextLocation = findNextLocation(currentLocation, locationList);
            if (nextLocation != null) {
                totalDistance += nextLocation.getDistanceWith(currentLocation);
                locationOrder.add(nextLocation);
                locationList.remove(nextLocation);
                currentLocation = nextLocation;
            } else {
                break; // No more connected locations
            }
        }
        
        if(printLocationOrder) {
            // Print location order
            StringBuilder locationorder = new StringBuilder("Using Breadth First Search\nLocation Order : ");
            for (int i = 0; i < locationOrder.size()-1; i++) {
                locationorder.append(locationOrder.get(i).getTeam()).append(" -> ");
            }
            JOptionPane.showMessageDialog(null, locationorder.toString());
            JOptionPane.showMessageDialog(null, locationOrder.get(locationOrder.size()-1).getTeam());
        }
        
        for (int i = 0; i < 10; i++) {
            flow[i] = locationOrder.get(i);
        }

        return totalDistance;
    }

    private static Location findNextLocation(Location currentLocation, ArrayList<Location> locationList) {
        ArrayList<Distance> listNextLocation = currentLocation.getList();
        for (Distance distance : listNextLocation) {
            Location nextLocation = distance.getConnectLocation();
            if (locationList.contains(nextLocation)) {
                return nextLocation;
            }
        }
        return null;
    }
    
    public static int nearestNeighborAlgorithm(Location firstLocation, Location[] allLocation, boolean printLocationOrder, Location[] flow) {
        int totalDistance = 0;
        ArrayList<Location> locationOrder = new ArrayList<>(); // show location order
        locationOrder.add(firstLocation);

        ArrayList<Location> locationList = new ArrayList<>();
        for (int i = 1; i < allLocation.length; i++) { // not include the first location
            locationList.add(allLocation[i]);
        }

        Location currentLocation = firstLocation;

        while (!locationList.isEmpty()) {
            ArrayList<Distance> ListNextLocation = currentLocation.getList();
            Location nearestLocation = null;
            int minDistance = Integer.MAX_VALUE;

            for (Distance distance : ListNextLocation) {
                int d = distance.getDistance();
                if (d < minDistance && locationList.contains(distance.getConnectLocation())) {
                    minDistance = d;
                    nearestLocation = distance.getConnectLocation();
                }
            }

            if (nearestLocation != null) {
                totalDistance += minDistance;
                locationOrder.add(nearestLocation);
                locationList.remove(nearestLocation);
                currentLocation = nearestLocation;
            }
        }

        if(printLocationOrder) {
            // Print location order
            StringBuilder locationorder = new StringBuilder("Using Nearest Neighbor Algorithm\nLocation Order : ");
            for (int i = 0; i < locationOrder.size()-1; i++) {
                locationorder.append(locationOrder.get(i).getTeam()).append(" -> ");
            }
            JOptionPane.showMessageDialog(null, locationorder.toString());
            JOptionPane.showMessageDialog(null, locationOrder.get(locationOrder.size()-1).getTeam());
        }
        
        for (int i = 0; i < 10; i++) {
            flow[i] = locationOrder.get(i);
        }
        
        return totalDistance;
    }
    
    public static int exhaustiveSearch(Location firstLocation, Location[] allLocation, boolean print, Location[] flow) {
        int totalDistance = Integer.MAX_VALUE;
        ArrayList<Location> bestPath = new ArrayList<>();

        // Remove the first location from the list of remaining locations
        Location[] remainingLocations = new Location[allLocation.length - 1];
        for (int i = 1; i < allLocation.length; i++) {
            remainingLocations[i - 1] = allLocation[i];
        }

        // Generate all permutations of remaining locations
        ArrayList<ArrayList<Location>> permutations = new ArrayList<>();
        generatePermutations(new ArrayList<>(), remainingLocations, permutations);

        // Calculate total distance for each permutation
        for (ArrayList<Location> permutation : permutations) {
            // Add the first location back to the beginning of the permutation
            permutation.add(0, firstLocation);

            if (isValidPath(permutation)) {
                int distance = calculateTotalDistance(firstLocation, permutation);
                if (distance < totalDistance) {
                    totalDistance = distance;
                    bestPath = permutation;
                }
            }
        }
        
        if(print) {
            // Print location order
            StringBuilder locationorder = new StringBuilder("Using Exhaustive Search\nLocation Order : ");
            for (int i = 0; i < bestPath.size()-1; i++) {
                locationorder.append(bestPath.get(i).getTeam()).append(" -> ");
            }
            JOptionPane.showMessageDialog(null, locationorder.toString());
            JOptionPane.showMessageDialog(null, bestPath.get(bestPath.size()-1).getTeam());
        }
        
        for (int i = 0; i < 10; i++) {
            flow[i] = bestPath.get(i);
        }

        return totalDistance;
    }
    
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
    
    // Method to handle other input
    private static int getInput (String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message);
            if (input == null) {
                return -1; // Assuming -1 is a special value indicating cancellation or invalid input
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
    }
}