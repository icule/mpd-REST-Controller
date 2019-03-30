package iculesgate.mpd_controller.database;

import iculesgate.mpd_controller.data.MusicStatistic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StatisticTable {
    private static final String TABLE_NAME = "Statistic";
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(" +
            "id uuid not null," +
            "playCount int not null" +
            ");" +
            "ALTER TABLE " + TABLE_NAME + " " +
            "ADD FOREIGN KEY (id) REFERENCES " + MusicInfoTable.TABLE_NAME + "(id);";

    private static final String INSERT_VALUE_QUERY = "INSERT INTO " + TABLE_NAME +
            " VALUES (?, ?);";

    private static final String UPDATE_QUERY = "UPDATE " + TABLE_NAME + " SET " +
            " playCount = ? WHERE id = ?;";

    private static final String SELECT_FROM_ID_QUERY = "SELECT playCount FROM " + TABLE_NAME +
            " WHERE id = ?;";

    private final DatabaseManager databaseManager;

    StatisticTable(final DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    void init() throws SQLException {
        try (PreparedStatement statement = databaseManager.getPreparedStatement(CREATE_QUERY)) {
            statement.execute();
        }
    }

    MusicStatistic getPlayCount(final UUID id) throws DatabaseOperationImpossible {
        try (PreparedStatement preparedStatement = databaseManager.getPreparedStatement(SELECT_FROM_ID_QUERY)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new MusicStatistic(resultSet.getInt(1));
            }
            return new MusicStatistic(0);
        }
        catch (SQLException e) {
            throw new DatabaseOperationImpossible(e, "Impossible to retrieve the play count. Original error: %s", e.getMessage());
        }
    }

    void incrementPlayCount(final UUID id) throws DatabaseOperationImpossible {
        MusicStatistic musicStatistic = getPlayCount(id);
        if (musicStatistic.getPlayCount() == 0) {
            try (PreparedStatement preparedStatement = databaseManager.getPreparedStatement(INSERT_VALUE_QUERY)) {
                preparedStatement.setObject(1, id);
                preparedStatement.setInt(2, 1);

                preparedStatement.executeUpdate();
            }
            catch (SQLException e) {
                throw new DatabaseOperationImpossible(e, "Impossible to add play count. Original error: %s", e.getMessage());
            }
        }
        else {
            try (PreparedStatement preparedStatement = databaseManager.getPreparedStatement(UPDATE_QUERY)) {
                preparedStatement.setInt(1, musicStatistic.getPlayCount() + 1);
                preparedStatement.setObject(2, id);

                preparedStatement.executeUpdate();
            }
            catch (SQLException e) {
                throw new DatabaseOperationImpossible(e, "Impossible to update playCount. Original error: %s", e.getMessage());
            }
        }
    }
}
