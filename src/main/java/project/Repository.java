package project;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.lang.reflect.Field;
import java.sql.*;

public class Repository {

    /**
     * table name
     */
    private static final String TABLE_NAME = "entity";

    private EmbeddedDataSource dataSource;

    public Repository(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    /**
     * initializes table
     */
    private void initTable() {
        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(
                    null, null,
                    TABLE_NAME, new String[]{"TABLE"});
            if (resultSet.next()) {
                System.out.println("Table already exists!");
            } else {
                Field[] fields = Entity.class.getDeclaredFields();
                statement.executeUpdate(
                        "CREATE TABLE "
                                + TABLE_NAME + " ("
                                + fields[0].getName() + " INTEGER, "
                                + fields[1].getName() + " VARCHAR(255), "
                                + fields[2].getName() + " BOOLEAN, "
                                + fields[3].getName() + " DATE, "
                                + fields[4].getName() + " TIMESTAMP)");
            }
            resultSet.close();
        } catch (SQLException exception) {
            System.out.println("Failed to create table!");
            exception.printStackTrace();
        }
    }

    /**
     * drops table
     */
    public void dropTable() {
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE " + TABLE_NAME);
        } catch (SQLException exception) {
            System.out.println("Failed to drop table!");
            exception.printStackTrace();
        }
    }

    /**
     * selects entity with this integerField
     * @param integerField integerField
     * @return entity
     */
    public Entity select(int integerField) {
        String select = "SELECT * FROM " +
                TABLE_NAME + ' ' +
                "WHERE integerField=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setInt(1, integerField);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return new Entity(
                        resultSet.getInt("integerField"),
                        resultSet.getString("stringField"),
                        resultSet.getBoolean("booleanField"),
                        resultSet.getDate("localDateField").toLocalDate(),
                        resultSet.getTimestamp("localDateTimeField").toLocalDateTime()
                );
            }
        } catch (SQLException exception) {
            System.out.println("Select operation failed!");
            System.out.println(exception.getMessage());
        }
        return null;
    }

    /**
     * inserts entity
     * @param entity entity
     */
    public void insert(Entity entity) {
        String insert = "INSERT INTO " + TABLE_NAME +
                        " VALUES(?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setInt(1, entity.getIntegerField());
            statement.setString(2, entity.getStringField());
            statement.setBoolean(3, entity.isBooleanField());
            statement.setDate(4, Date.valueOf(entity.getLocalDateField()));
            statement.setTimestamp(5, Timestamp.valueOf(entity.getLocalDateTimeField()));
            statement.execute();
        } catch (SQLException exception) {
            System.out.println("Entity insertion failed!");
            exception.printStackTrace();
        }
    }

    /**
     * updates entity
     * @param entity entity
     */
    public void update(Entity entity) {
        String update = "UPDATE " + TABLE_NAME + ' ' +
                        "SET " +
                        "integerField=?, " +
                        "stringField=?, " +
                        "booleanField=?, " +
                        "localDateField=?, " +
                        "localDateTimeField=? " +
                        "WHERE integerField=?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setInt(1, entity.getIntegerField());
            statement.setString(2, entity.getStringField());
            statement.setBoolean(3, entity.isBooleanField());
            statement.setDate(4, Date.valueOf(entity.getLocalDateField()));
            statement.setTimestamp(5, Timestamp.valueOf(entity.getLocalDateTimeField()));
            statement.setInt(6, entity.getIntegerField());
            statement.execute();
        } catch (SQLException exception) {
            System.out.println("Update operation failed!");
            System.out.println(exception.getMessage());
        }
    }

    /**
     * deletes entity
     * @param entity entity
     */
    public void delete(Entity entity) {
        if (entity == null) {
            return;
        }
        String delete = "DELETE FROM " + TABLE_NAME + " " +
                        "WHERE integerField=?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setInt(1, entity.getIntegerField());
            statement.execute();
        } catch (SQLException exception) {
            System.out.println("Delete operation failed!");
            System.out.println(exception.getMessage());
        }
    }

}
