package dao;

import models.User;
import utils.DBConnection;
import java.sql.*;

public class UserDAO {
    public int getUserIdByName(String name) throws Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE name = ?");
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        return -1;
    }

    public int addUser(User user) throws Exception {
        int existingId = getUserIdByName(user.getName());
        if (existingId != -1) {
            return existingId;
        }

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, user.getName());
        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);
        }
        return -1;
    }
}
