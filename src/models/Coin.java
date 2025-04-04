package models;

public class Coin {
    private String name;
    private int year;
    private double value;
    private int userId;
    private int countryId;

    public Coin(String name, int year, double value, int userId, int countryId) {
        this.name = name;
        this.year = year;
        this.value = value;
        this.userId = userId;
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public double getValue() {
        return value;
    }

    public int getUserId() {
        return userId;
    }

    public int getCountryId() {
        return countryId;
    }
}
