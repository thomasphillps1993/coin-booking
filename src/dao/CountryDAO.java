package dao;

import models.Country;
import utils.DBConnection;
import java.sql.*;

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

    public int addCountry(Country country) throws Exception {
        int existingId = getCountryIdByName(country.getName());
        if (existingId != -1) {
            return existingId;
        }

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO countries (name, population) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, country.getName());
        stmt.setInt(2, country.getPopulation());
        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);
        }
        return -1;
    }

}
