package javaapplication1;

public class Player {
    // Attributes of the Player class
    private String name;    // Name of the player
    private double height;  // Height of the player in inches/centimeters
    private double weight;  // Weight of the player in pounds/kilograms
    private String position; // Position of the player (e.g., guard, forward)
    private double ppg;     // Points per game
    private double rpg;     // Rebounds per game
    private double apg;     // Assists per game
    private double pie;     // Player Impact Estimate
    private double salary;  // Salary of the player

    // Default constructor
    public Player() {
        // Initializes a new Player object with default values
    }

    // Parameterized constructor to initialize all attributes
    public Player(String name, double height, double weight, String position, double ppg, double rpg, double apg, double pie, double salary) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.position = position;
        this.ppg = ppg;
        this.rpg = rpg;
        this.apg = apg;
        this.pie = pie;
        this.salary = salary;
    }

    // Constructor to initialize only the name attribute
    public Player(String name) {
        this.name = name;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Setter method for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for height
    public double getHeight() {
        return height;
    }

    // Setter method for height
    public void setHeight(double height) {
        this.height = height;
    }

    // Getter method for weight
    public double getWeight() {
        return weight;
    }

    // Setter method for weight
    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Getter method for position
    public String getPosition() {
        return position;
    }

    // Setter method for position
    public void setPosition(String position) {
        this.position = position;
    }

    // Getter method for points per game
    public double getPpg() {
        return ppg;
    }

    // Setter method for points per game
    public void setPpg(double ppg) {
        this.ppg = ppg;
    }

    // Getter method for rebounds per game
    public double getRpg() {
        return rpg;
    }

    // Setter method for rebounds per game
    public void setRpg(double rpg) {
        this.rpg = rpg;
    }

    // Getter method for assists per game
    public double getApg() {
        return apg;
    }

    // Setter method for assists per game
    public void setApg(double apg) {
        this.apg = apg;
    }

    // Getter method for Player Impact Estimate
    public double getPie() {
        return pie;
    }

    // Setter method for Player Impact Estimate
    public void setPie(double pie) {
        this.pie = pie;
    }

    // Getter method for salary
    public double getSalary() {
        return salary;
    }

    // Setter method for salary
    public void setSalary(double salary) {
        this.salary = salary;
    }
}
