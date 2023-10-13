package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class MovieLoaderImpl implements MovieLoader {
    private final DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        String SQL = "INSERT INTO movie(year, length, title, subject, actors, actress, director, popularity, awards)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (BufferedReader bf = new BufferedReader(new FileReader(file));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            String current = "";
            bf.readLine();
            bf.readLine();

            while ((current = bf.readLine()) != null) {
                String[] arrDataCurrent = current.split(";");
                Movie movie = new Movie();
                if (!arrDataCurrent[0].isEmpty()) {
                    movie.setYear(Integer.valueOf(arrDataCurrent[0]));
                }
                if (!arrDataCurrent[1].isEmpty()) {
                    movie.setLength(Integer.valueOf(arrDataCurrent[1]));
                }
                movie.setTitle(arrDataCurrent[2]);
                movie.setSubject(arrDataCurrent[3]);
                movie.setActors(arrDataCurrent[4]);
                movie.setActress(arrDataCurrent[5]);
                movie.setDirector(arrDataCurrent[6]);
                if (!arrDataCurrent[7].isEmpty()) {
                    movie.setPopularity(Integer.valueOf(arrDataCurrent[7]));
                }
                movie.setAwards(Boolean.valueOf(arrDataCurrent[8]));
                workWitchDataSource(movie, preparedStatement);
            }
            preparedStatement.executeBatch();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (SQLException e) {
            System.out.println("Ошибка сохранения данных в БД");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void workWitchDataSource(Movie movie, PreparedStatement preparedStatement) throws SQLException {
        if (movie.getYear() == null) {
            preparedStatement.setNull(1, Types.INTEGER);
        } else {
            preparedStatement.setInt(1, movie.getYear());
        }
        if (movie.getLength() == null) {
            preparedStatement.setNull(2, Types.INTEGER);
        } else {
            preparedStatement.setInt(2, movie.getLength());
        }
        preparedStatement.setString(3, movie.getTitle());
        preparedStatement.setString(4, movie.getSubject());
        preparedStatement.setString(5, movie.getActors());
        preparedStatement.setString(6, movie.getActress());
        preparedStatement.setString(7, movie.getDirector());
        if (movie.getPopularity() == null) {
            preparedStatement.setNull(8, Types.INTEGER);
        } else {
            preparedStatement.setInt(8, movie.getPopularity());
        }
        preparedStatement.setBoolean(9, movie.getAwards());
        preparedStatement.addBatch();
    }
}
