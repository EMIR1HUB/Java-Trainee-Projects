package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordProcessing {
  private final WordDAO wordDAO;
  private final ConnectionFactory connectionFactory;

  @Autowired
  public WordProcessing(WordDAO wordDAO, ConnectionFactory connectionFactory){
    this.wordDAO = wordDAO;
    this.connectionFactory = connectionFactory;
  }


  public String censor(String message){
    StringBuilder filteredMessage = new StringBuilder();
    String[] words = message.split("(?=[\\p{Punct}\\s])|(?<=[\\p{Punct}\\s])");
    for (String word : words){
      if(wordDAO.isWordForbidden(word)){
        filteredMessage.append(word.charAt(0)).append("*".repeat(word.length() - 2)).append(word.charAt(word.length() - 1));
      }else {
        filteredMessage.append(word);
      }
    }
    sendToQueue(filteredMessage.toString());
    return filteredMessage.toString();
  }

  private void sendToQueue(String message){
    try(Channel channel = connectionFactory.newConnection().createChannel()){
      String exchangeName = "exc";
      String queueOutput = "output";

      channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
      channel.queueDeclare(queueOutput, true, false, false, null);
      channel.queueBind(queueOutput, exchangeName, "*");
      channel.basicPublish(exchangeName, "*", null, message.getBytes());
    } catch (Exception e) {
      System.err.println("Ошибка при отправке очереди output");
    }
  }
}
