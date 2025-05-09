package javaapplication1;

/**
 * The Distance class represents a distance between two locations.
 * It stores one of the locations (Location B) and the distance between 
 * Location A (passed in the constructor) and Location B.
 */
public class Distance {
    private Location locationB; // The location at the other end of the distance
    private int distanceBetween; // The distance between the two locations
    
    /**
     * Constructor to initialize the Distance object.
     * 
     * @param a the first location (not stored, but implied as the origin)
     * @param b the second location (stored as locationB)
     * @param d the distance between location a and location b
     */
    public Distance(Location a, Location b, int d) {
        locationB = b; // Initialize locationB with the provided location b
        distanceBetween = d; // Initialize distanceBetween with the provided distance d
    }
    
    /**
     * Returns the location that can be reached.
     * 
     * @return locationB the location at the other end of the distance
     */
    public Location getConnectLocation() {
        return locationB; // Return the stored locationB
    }
    
    /**
     * Returns the distance between the two locations.
     * 
     * @return distanceBetween the distance between the two locations
     */
    public int getDistance() {
        return distanceBetween; // Return the stored distanceBetween
    }
}
