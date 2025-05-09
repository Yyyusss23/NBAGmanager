package javaapplication1;

import java.util.ArrayList;

/**
 * Represents a location associated with a team, and manages connected locations and distances.
 */
public class Location {
    private String place; // The place name of the location
    private String team; // The team associated with the location
    private ArrayList<Distance> locationAble; // Stores locations that can be reached from this location
    
    /**
     * Constructor to initialize a Location with a team and place name.
     * 
     * @param t The team associated with this location.
     * @param p The place name of this location.
     */
    public Location(String t, String p) {
        place = p;
        team = t;
        locationAble = new ArrayList<>();
    }
    
    /**
     * Adds a connected location with a specified distance.
     * 
     * @param locationConnected The location that is connected to this location.
     * @param distance The distance to the connected location.
     */
    public void addLocation(Location locationConnected, int distance) {
        Distance newD = new Distance(this, locationConnected, distance);
        locationAble.add(newD);
    }
    
    /**
     * Returns the list of distances to connected locations.
     * 
     * @return An ArrayList of Distance objects representing connected locations and distances.
     */
    public ArrayList<Distance> getList() {
        return locationAble;
    }
    
    /**
     * Returns the distance to a specified location if it is connected.
     * 
     * @param theLocation The location to get the distance to.
     * @return The distance to the specified location, or 0 if not connected.
     */
    public int getDistanceWith(Location theLocation) {
        int d = 0;
        for (int i = 0; i < locationAble.size(); i++) {
            if(theLocation == locationAble.get(i).getConnectLocation()) {
                d = locationAble.get(i).getDistance();
            }
        }
        return d;
    }
    
    /**
     * Checks if the current location is connected to a specified location.
     * 
     * @param theLocation The location to check connection with.
     * @return true if the location is connected, false otherwise.
     */
    public boolean isConnected(Location theLocation) {
        boolean connect = false;
        for (int i = 0; i < locationAble.size(); i++) {
            if(theLocation == locationAble.get(i).getConnectLocation()) {
                connect = true;
            }
        }
        return connect;
    }
    
    /**
     * Returns the team associated with this location.
     * 
     * @return The team name.
     */
    public String getTeam() {
        return team;
    }
    
    /**
     * Returns the place name of this location.
     * 
     * @return The place name.
     */
    public String getPlace() {
        return place;
    }
}
