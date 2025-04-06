package dao;

import models.Coin;
import models.Country;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {
    public int getCountryIdByName(String name) throws Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM countries WHERE name = ?");
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        return -1;
    }
    public int addCountry(String name, int yearEstablished, int population) throws Exception {
        // First, check if the country already exists in the database
        String checkQuery = "SELECT id FROM countries WHERE name = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, name);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            // If country exists, return the existing country ID
            return rs.getInt("id");
        } else {
            // If country doesn't exist, insert a new country
            String insertQuery = "INSERT INTO countries (name, year_established, population) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, name);
            insertStmt.setInt(2, yearEstablished);
            insertStmt.setInt(3, population);
            insertStmt.executeUpdate();

            // Get the newly inserted country ID
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);  // Return the generated ID
            } else {
                throw new SQLException("Failed to insert country, no ID obtained.");
            }
        }
    }

    public List<Country> getAllCountries() throws SQLException {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT * FROM countries";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                countries.add(new Country(
                    rs.getString("name"),
                    rs.getInt("year_established"),
                    rs.getInt("population")
                ));
            }
        }
    
        return countries;
    }

}
