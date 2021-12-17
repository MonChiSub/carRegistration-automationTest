package main;

public class Car {
    private String registration;
    private String make;
    private String model;
    private String colour;
    private int year;

    /**
     * Constructors of Car class for 3 cases (Unknown Reg, Known reg but rest unknown, all known).
     */
    public Car() {
        this("Unknown");
    }

    public Car(String pRegistration) {
        this(pRegistration, "Unknown", "Unknown", "Unknown", 0);
    }

    public Car(String pRegistration, String pMake, String pModel, String pColour, int pYear) {
        this.registration = pRegistration;
        this.make = pMake;
        this.model = pModel;
        this.colour = pColour;
        this.year = pYear;
    }
    // End of Constructors

    /**
     * Getter and Setter methods
     */
    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String toString() {
        return this.registration+", "+this.make+", "+this.model+", "+this.colour+", "+this.year+".";
    }
}
