package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DbApp {
  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    String queueName = "queue";
    ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);

    while (true) {
      PersonDAOIml personDAOIml = applicationContext.getBean(PersonDAOIml.class);

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
    }
  }
}
