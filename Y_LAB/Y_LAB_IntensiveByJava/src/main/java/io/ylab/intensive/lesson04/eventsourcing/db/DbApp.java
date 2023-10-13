package io.ylab.intensive.lesson04.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class DbApp {
    public static void main(String[] args) throws Exception {
        String queueName = "queue";
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();

        while (true) {  // реализация бесконечного цикла для получения сообщений из очереди
            try (java.sql.Connection connectionDB = dataSource.getConnection()) {
                PersonDAOIml personDAOIml = new PersonDAOIml(connectionDB);

                while (!Thread.currentThread().isInterrupted()) {
                    try (Connection connection = connectionFactory.newConnection();
                         Channel channel = connection.createChannel()) {
                        GetResponse message = channel.basicGet(queueName, true);
                        if (message != null) {
                            String routingKey = message.getEnvelope().getRoutingKey();
                            if (routingKey.equals("save.person")) {
                                personDAOIml.save(new String(message.getBody()));
                            } else if (routingKey.equals("delete.person")) {
                                personDAOIml.delete(new String(message.getBody()));
                            } else {
                                System.out.println("Неизвестный ключ маршрутизации");
                            }
                        }
                    } catch (IOException | TimeoutException e) {
                        System.err.println("Не удалось установить соединение с RabbitMQ: " + e.getMessage());
                        Thread.sleep(2000);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Не удалось установить соединение с базой данных: " + e.getMessage());
            }
        }
        // Данный код реализует бесконечный цикл, который продолжает работать
        // даже в случае ошибок при соединении с базой данных или RabbitMQ.
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
