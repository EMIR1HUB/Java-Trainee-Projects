package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        System.out.println("==MapFirst==");
        persistentMap.init("MapFirst");
        persistentMap.put("1", "Leo");
        persistentMap.put("2", "Mike");
        persistentMap.put("3", "Donny");
        persistentMap.put("4", "Raphael");
        persistentMap.put("Five", "Cerry");

        System.out.println("Список всех ключей");
        System.out.println(persistentMap.getKeys());

        System.out.print("\nКлюч 4: ");
        System.out.println(persistentMap.containsKey("4"));
        System.out.print("Ключ 5: ");
        System.out.println(persistentMap.containsKey("5"));

        System.out.print("\nЗначание ключа 4: ");
        System.out.println(persistentMap.get("4"));
        System.out.print("Значание ключа 5: ");
        System.out.println(persistentMap.get("5"));

        System.out.println("\nУдаление ключа 4");
        persistentMap.remove("4");
        System.out.println(persistentMap.getKeys());

        System.out.println("\nДобавление Four");
        persistentMap.put("Four", "Raphael");

        System.out.print("\nЗначение ключа Five: ");
        System.out.println(persistentMap.get("Five"));

        System.out.println("Изменяем значение ключа Five");
        persistentMap.put("Five", "Bob");
        System.out.println("Стало: " + persistentMap.get("Five"));
        System.out.println(persistentMap.getKeys());


        System.out.println("\n==MapSecond==");
        persistentMap.init("MapSecond");
        persistentMap.put("S1", "Jojy");
        persistentMap.put("S2", "Many");
        persistentMap.put("S3", "Horny");
        System.out.println("Удаление MapSecond");
        persistentMap.clear();

        System.out.println("\nВывод MapFirst");
        persistentMap.init("MapFirst");
        for(String key : persistentMap.getKeys()){
            System.out.println(key + " : " + persistentMap.get(key));
        }
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
