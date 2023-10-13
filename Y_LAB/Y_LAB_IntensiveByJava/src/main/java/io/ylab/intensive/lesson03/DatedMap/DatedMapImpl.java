package io.ylab.intensive.lesson03.DatedMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap{
    private Map<String, String> mapData = new HashMap<>();
    private Map<String, Date> currentDate = new HashMap<>();

    @Override
    public void put(String key, String value) {
        mapData.put(key, value);
        currentDate.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return mapData.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return mapData.containsKey(key);
    }

    @Override
    public void remove(String key) {
        mapData.remove(key);
        currentDate.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return mapData.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return currentDate.get(key);
    }

    @Override
    public String toString() {
        return "DatedMapImpl{" +
                "\nmapData = " + mapData +
                "\ncurrentDate = " + currentDate +
                "\n}";
    }
}
