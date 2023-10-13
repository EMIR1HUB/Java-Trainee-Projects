package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageFilterApp {
  private static final String QUEUE_INPUT = "input";

  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    WordDAO wordDAO = applicationContext.getBean(WordDAO.class);
    wordDAO.createDB();
    WordProcessing wordProcessing = applicationContext.getBean(WordProcessing.class);
    ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);

    while (!Thread.currentThread().isInterrupted()){
        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
          channel.queueDeclare(QUEUE_INPUT, true, false, false, null);
          GetResponse response = channel.basicGet(QUEUE_INPUT, true);
          if (response != null) {
            String message = new String(response.getBody());
            System.out.println("Получено из очереди input: " + message);
            String censorMessage = wordProcessing.censor(message);
            System.out.println("Отправлено в очередь output: " + censorMessage);
          }
        } catch (IOException | TimeoutException e) {
          System.err.println("Поиск активной очереди...");
          Thread.sleep(2000);
        }
    }
  }
}
