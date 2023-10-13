package io.ylab.intensive.lesson04.filesort;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

public class FileSorterTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        File data = new File("data.txt");       // файл на 100_000
        FileSortImpl fileSorter = new FileSortImpl(dataSource);

        Long currTime = System.currentTimeMillis();
        File res = fileSorter.sort(data);
        System.out.println("Отсартированный файл c batch-processing: " + res + "\nTime: " +
                (System.currentTimeMillis() - currTime) + " ms");

        currTime = System.currentTimeMillis();
        res = fileSorter.sortSimpleUpdate(data);
        System.out.println("Отсартированный файл без batch-processing: " + res + "\nTime: " +
                (System.currentTimeMillis() - currTime) + " ms");
    }

    public static DataSource initDb() throws SQLException {
        String createSortTable = ""
                + "drop table if exists numbers;"
                + "CREATE TABLE if not exists numbers (\n"
                + "\tval bigint\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createSortTable, dataSource);
        return dataSource;
    }
}
