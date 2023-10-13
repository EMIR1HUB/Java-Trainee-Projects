package io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private final DataSource dataSource;
    private String nameMap;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.nameMap = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        String query = "SELECT * FROM persistent_map WHERE map_name = ? AND key = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, nameMap);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    @Override
    public List<String> getKeys() throws SQLException {
        String query = "SELECT key FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameMap);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> keys = new ArrayList<>();
            while (resultSet.next()) {
                keys.add(resultSet.getString("key"));
            }
            return keys;
        }
    }

    @Override
    public String get(String key) throws SQLException {
        String query = "SELECT value FROM persistent_map WHERE map_name = ? AND key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameMap);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("value");
            } else {
                return null;
            }
        }
    }

    @Override
    public void remove(String key) throws SQLException {
        String query = "DELETE FROM persistent_map WHERE map_name = ? AND key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameMap);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        if (containsKey(key)) {
            remove(key);
        }
        String updateQuery = "INSERT INTO persistent_map(map_name, key, value) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, nameMap);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, value);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        String query = "DELETE FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameMap);
            preparedStatement.executeUpdate();
        }
    }
}
