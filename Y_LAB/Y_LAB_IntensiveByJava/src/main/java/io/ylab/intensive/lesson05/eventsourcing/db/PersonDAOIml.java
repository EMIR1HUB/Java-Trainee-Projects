package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson05.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDAOIml implements PersonDAO {

    private final Connection connection;

    PersonDAOIml(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("Не удалось установить соединение с базой данных: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(String jsonMessage) {
        try {
            Person person = new ObjectMapper().readValue(jsonMessage, Person.class);
            if (checkOnPersonPresentOrAbsent(person.getId())) {
                String query = "UPDATE person SET first_name = ?, last_name = ?, middle_name = ? WHERE person_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, person.getName());
                    preparedStatement.setString(2, person.getLastName());
                    preparedStatement.setString(3, person.getMiddleName());
                    preparedStatement.setLong(4, person.getId());
                    preparedStatement.executeUpdate();
                    System.out.println("[+] Данные обнавлены с id: " + person.getId());
                } catch (SQLException e) {
                    System.err.println("Ошибка при обновлений данных в БД");
                }
            } else {
                String query = "INSERT INTO person(person_id, first_name, last_name, middle_name) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setLong(1, person.getId());
                    preparedStatement.setString(2, person.getName());
                    preparedStatement.setString(3, person.getLastName());
                    preparedStatement.setString(4, person.getMiddleName());
                    preparedStatement.executeUpdate();
                    System.out.println("[+] Данные сохранены с id: " + person.getId());
                }
                catch (SQLException e) {
                    System.err.println("Ошибка при сохранении данных в БД");
                }
            }
        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при работе с JSON файлом");
        }
    }

    @Override
    public void delete(String personId) {
        String query = "DELETE FROM person WHERE person_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (!checkOnPersonPresentOrAbsent(Long.parseLong(personId))) {
                System.err.println("[x] Человека не существует для удаления c ID " + personId);
                return;
            }
            preparedStatement.setLong(1, Long.parseLong(personId));
            preparedStatement.executeUpdate();
            System.out.println("[+] Данные удалены с id: " + personId);
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении данных с БД");
        }
    }

    private boolean checkOnPersonPresentOrAbsent(Long id) {
        String query = "SELECT * FROM person WHERE person_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
