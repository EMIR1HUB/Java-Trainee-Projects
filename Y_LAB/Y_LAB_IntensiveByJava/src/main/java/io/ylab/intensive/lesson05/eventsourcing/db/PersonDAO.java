package io.ylab.intensive.lesson05.eventsourcing.db;

public interface PersonDAO {
    void save(String message);
    void delete(String message);
}
