package org.example.db.crud;

import org.example.db.Database;
import org.example.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final Connection CONN = Database.getInstance().getConnection();
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    public static long create(String name) {
        if (name == null || name.length() > 1000) {
            LOGGER.error("create: Parameter exception: name must be String and max 1000 symbols!");
            throw new IllegalArgumentException("create: Illegal argument");
        }
        String sql = "INSERT INTO CLIENT (NAME) VALUES (?)";
        PreparedStatement stm;
        long id = -1;
        try {
            stm = CONN.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, name);
            int affectedRows = stm.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return id;
    }

    public static String getById(long id) {
        if (id < 1) {
            LOGGER.error("getById: Parameter exception: id < 0");
            throw new IllegalArgumentException("getById: Illegal argument");
        }
        String sql = "SELECT ID, NAME FROM CLIENT WHERE ID=?";
        PreparedStatement stm;
        String clientName = "No client!";
        try {
            stm = CONN.prepareStatement(sql);
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                clientName = rs.getString("name");
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return clientName;
    }

    public static void setName(long id, String name) {
        if (id < 1 || name == null || name.length() > 1000) {
            LOGGER.error("setName: Parameter exception");
            throw new IllegalArgumentException("setName: Illegal argument");
        }
        String sql = "UPDATE CLIENT SET NAME = ? WHERE ID=?";
        PreparedStatement stm;

        try {
            stm = CONN.prepareStatement(sql);
            stm.setString(1, name);
            stm.setLong(2, id);
            int affectedRows = stm.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void deleteById(long id) {
        if (id < 1) {
            LOGGER.error("deleteById: Parameter exception");
            throw new IllegalArgumentException("deleteById: Illegal argument");
        }
        String sql = "DELETE FROM CLIENT WHERE ID=?";
        PreparedStatement stm;

        try {
            stm = CONN.prepareStatement(sql);
            stm.setLong(1, id);
            int affectedRows = stm.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT ID, NAME FROM CLIENT";
        try {
            Statement stm = CONN.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                clients.add(new Client(rs.getLong("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return clients;
    }
}
