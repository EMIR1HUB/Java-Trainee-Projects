package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileSortImpl implements FileSorter {
    private final DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        List<Long> numbers = readFile(data);
        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("INSERT INTO numbers (val) VALUES (?)")) {
                for (Long number : numbers){
                    statement.setLong(1, number);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            return sortNumbersWriteInFile(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка соединения с БД");
        }
        return null;
    }

    public File sortSimpleUpdate(File data) {
        List<Long> numbers = readFile(data);
        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("INSERT INTO numbers (val) VALUES (?)")) {
                for (Long number : numbers){
                    statement.setLong(1, number);
                    statement.executeUpdate();
                }
            }
            return sortNumbersWriteInFile(connection);
        } catch (SQLException e) {
            System.out.println("Ошибка соединения с БД");
        }
        return null;
    }

    private List<Long> readFile(File data){
        List<Long> numbers = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader(data))){
            String line;
            while ((line = bf.readLine()) != null){
                numbers.add(Long.parseLong(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    private File sortNumbersWriteInFile(Connection connection) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeQuery("SELECT val FROM numbers ORDER BY val DESC");
            try(ResultSet resultSet = statement.getResultSet()) {   // Запись в новый файл
                File outputFile = new File("output.txt");
                try(PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                    while (resultSet.next()){
                        Long numer = resultSet.getLong(1);
                        writer.println(numer);
                    }
                }
                return outputFile;
            } catch (IOException e) {
                System.out.println("Ошибка записи в файл");
            }
        }
        return null;
    }
}
