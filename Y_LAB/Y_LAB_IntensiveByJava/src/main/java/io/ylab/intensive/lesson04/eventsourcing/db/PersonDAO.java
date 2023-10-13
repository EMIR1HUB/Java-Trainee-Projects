package io.ylab.intensive.lesson04.eventsourcing.db;

import com.rabbitmq.client.GetResponse;

public interface PersonDAO {
    public void save(String message);
    public void delete(String message);
}
