package models;

public class Coin {
    private String name;
    private int year;
    private double value;
    private int userId;
    private int countryId;
    private int weight;

// If the Coin constructor expects a value type double, make sure to pass a double for value
public Coin(String name, int year, double value, int weight, int userId, int countryId) {
    this.name = name;
    this.year = year;
    this.value = value;
    this.weight = weight;
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
