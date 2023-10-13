package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Component
public class WordDAO {

  private final DataSource dataSource;

  @Autowired
  public WordDAO(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void createDB() {
    try (Connection connection = dataSource.getConnection()) {
      DatabaseMetaData metaData = connection.getMetaData();
      ResultSet tables = metaData.getTables(null, null, "forbidden_words", null);
      if (!tables.next()) {
        String ddl = """
                CREATE TABLE forbidden_words (
                  id SERIAL PRIMARY KEY,
                  word TEXT NOT NULL
                );""";
        DbUtil.applyDdl(ddl, dataSource);
      }
      fromFileToDb(connection);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void fromFileToDb(Connection connection) {
    try (PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE forbidden_words")) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Ошибка при очистки БД");
    }

    String query = "INSERT INTO forbidden_words (word) VALUES (?)";
    try (FileReader fileReader = new FileReader(new File("src/main/java/io/ylab/intensive/lesson05/messagefilter/forbiddenWords.txt"));
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         PreparedStatement statement = connection.prepareStatement(query)) {
      String stringInput;
      while ((stringInput = bufferedReader.readLine()) != null) {
        statement.setString(1, stringInput);
        statement.addBatch();
      }
      statement.executeBatch();
    } catch (FileNotFoundException e) {
      System.err.println("Файл не найден");
    } catch (IOException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean isWordForbidden(String word) {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM forbidden_words WHERE word = LOWER(?)")) {
      statement.setString(1, word);
      ResultSet resultSet = statement.executeQuery();
      return resultSet.next();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
