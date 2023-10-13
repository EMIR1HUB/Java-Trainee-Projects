package io.ylab.intensive.lesson04.eventsourcing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {

    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER_NAME = "postgres";
    private final String PASSWORD = "postgres";
    private final com.rabbitmq.client.Connection connection;

    public PersonApiImpl(com.rabbitmq.client.Connection connection) {
        this.connection = connection;
    }

    @Override
    public void deletePerson(Long personId) {
        try (Channel channel = connection.createChannel()) {
            String exchangeName = "exc";    // Имя exchange
            String queueName = "queue";    // Имя очереди
            String routingKey = "delete.person";   // формируем routing key

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
            channel.basicPublish(exchangeName, routingKey, null, String.valueOf(personId).getBytes());
            System.out.println("[+] Отправлено запрос на удаление person с id " + personId);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке запроса на удаление с id + " + personId);
            e.printStackTrace();
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        try (Channel channel = connection.createChannel()) {
            String exchangeName = "exc";    // Имя exchange
            String queueName = "queue";    // Имя очереди
            String routingKey = "save.person";   // формируем routing key
            ObjectMapper objectMapper = new ObjectMapper();

            Person person = new Person();
            person.setId(personId);
            person.setName(firstName);
            person.setLastName(lastName);
            person.setMiddleName(middleName);
            String jsonMessage = objectMapper.writeValueAsString(person);

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
            channel.basicPublish(exchangeName, routingKey, null, jsonMessage.getBytes());
            System.out.println("[+] Отправлено запрос на сохранения person с id " + personId);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке запроса на сохранения с id + " + personId);
            e.printStackTrace();
        }
    }

    @Override
    public Person findPerson(Long personId) {
        Person person = new Person();
        String query = "SELECT * FROM person WHERE person_id = ?";
        try (Connection connection = getConnectionDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                person.setId(personId);
                person.setName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setMiddleName(resultSet.getString("middle_name"));
            }
        } catch (SQLException e) {
            System.err.println("Не удалось подключиться к БД");
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        String query = "SELECT * FROM person";
        try (Connection connection = getConnectionDB();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong("person_id"));
                person.setName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setMiddleName(resultSet.getString("middle_name"));
                personList.add(person);
            }
        } catch (SQLException e) {
            System.err.println("Не удалось подключиться к БД");
        }
        return personList;
    }

    private Connection getConnectionDB() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Не найден");
        }
        return null;
    }
}
