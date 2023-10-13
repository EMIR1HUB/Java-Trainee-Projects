package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
  private final Connection connection;

  @Autowired
  public SQLQueryBuilderImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public String queryForTable(String tableName) throws SQLException {
    DatabaseMetaData databaseMetaData = connection.getMetaData();
    ResultSet tablesResult = databaseMetaData.getTables(null, null, tableName, null);
    if (!tablesResult.next()) {
      return null;
    }
    List<String> columns = new ArrayList<>();
    ResultSet columnsResultSet = databaseMetaData.getColumns(null, null, tableName, null);
    while (columnsResultSet.next()) {
      columns.add(columnsResultSet.getString("COLUMN_NAME"));
    }

    StringJoiner selectColumns = new StringJoiner(", ");
    for (String column : columns) {
      selectColumns.add(column);
    }
    return "SELECT " + selectColumns.toString() + " FROM " + tableName;
  }

  @Override
  public List<String> getTables() throws SQLException {
    List<String> tableNames = new ArrayList<>();
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet tablesResultSet = metaData.getTables(null, null, null, null);  // new String[]{"TABLE"}
    while (tablesResultSet.next()){
      tableNames.add(tablesResultSet.getString("TABLE_NAME"));
    }
    return tableNames;
  }
}
