package dao;

import models.Coin;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoinDAO {
    public int getCoinId(String name, int year, int countryId) throws Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn
                .prepareStatement("SELECT id FROM coins WHERE name = ? AND year = ? AND country_id = ?");
        stmt.setString(1, name);
        stmt.setInt(2, year);
        stmt.setInt(3, countryId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        return -1;
    }

    public int addCoin(Coin coin) throws Exception {
        int existingId = getCoinId(coin.getName(), coin.getYear(), coin.getCountryId());
        if (existingId != -1) {
            return existingId;
        }

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO coins (name, year, value, user_id, country_id) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, coin.getName());
        stmt.setInt(2, coin.getYear());
        stmt.setDouble(3, coin.getValue());
        stmt.setInt(4, coin.getUserId());
        stmt.setInt(5, coin.getCountryId());
        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);
        }
        return -1;
    }

    public List<Coin> getCoinsByUserId(int userId) throws SQLException {
        List<Coin> coins = new ArrayList<>();
        String query = "SELECT name, year, value FROM coins WHERE user_id = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            coins.add(new Coin(
                    rs.getString("name"),
                    rs.getInt("year"),
                    rs.getDouble("value"),
                    userId,
                    -1 // countryId not needed for this view
            ));
        }
        return coins;
    }
    public List<Coin> getAllCoins() throws SQLException {
        List<Coin> coins = new ArrayList<>();
        String query = "SELECT * FROM coins";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                coins.add(new Coin(
                    rs.getString("name"),
                    rs.getInt("year"),
                    rs.getDouble("value"),
                    rs.getInt("user_id"),
                    rs.getInt("country_id")
                ));
            }
        }
    
        return coins;
    }
    
}
