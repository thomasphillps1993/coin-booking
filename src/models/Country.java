package models;

public class Country {
    private String name;
    private int yearEstablished;
    private int population;

    // Updated constructor to accept name, year, and population
    public Country(String name, int yearEstablished, int population) {
        this.name = name;
        this.yearEstablished = yearEstablished;
        this.population = population;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getYearEstablished() {
        return yearEstablished;
    }

    public int getPopulation() {
        return population;
    }
}