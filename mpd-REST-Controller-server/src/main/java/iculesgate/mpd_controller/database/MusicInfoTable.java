package iculesgate.mpd_controller.database;

import iculesgate.mpd_controller.data.MusicInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MusicInfoTable {
    private static String TABLE_NAME = "MusicInfo";
    private static String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(" +
            "id uuid PRIMARY KEY," +
            "title varchar(250), " +
            "artist varchar(250)," +
            "filename varchar(250)" +
            ");";
    private static String SELECT_FROM_ID_QUERY = "SELECT * FROM " + TABLE_NAME +
            " WHERE id = ?;";
    private static String INSERT_QUERY = "INSERT INTO " + TABLE_NAME +
            " VALUES (?, ?, ?, ?)";
    private static String UPDATE_QUERY = "UPDATE " + TABLE_NAME +
            " SET title = ?, artist = ?, filename = ? " +
            " WHERE id = ?;";


    private DatabaseManager databaseManager;


    public MusicInfoTable(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void init() throws SQLException {
        PreparedStatement statement = databaseManager.getPreparedStatement(CREATE_TABLE_QUERY);
        statement.execute();
    }

    void addMusicInfo(final MusicInfo musicInfo) throws DatabaseOperationImpossible {
        try (PreparedStatement statement = databaseManager.getPreparedStatement(INSERT_QUERY)){
            statement.setObject(1, musicInfo.getMusicId());
            statement.setString(2, musicInfo.getTitle());
            statement.setString(3, musicInfo.getArtist());
            statement.setString(4, musicInfo.getFilename());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseOperationImpossible(e,"Impossible to add music info. Error message: %s", e.getMessage());
        }
    }

    MusicInfo getMusicInfo(final UUID id) throws DatabaseOperationImpossible {
        try (PreparedStatement statement = databaseManager.getPreparedStatement(SELECT_FROM_ID_QUERY)){
            statement.setObject(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new MusicInfo(resultSet.getString(4),
                                     resultSet.getString(2),
                                     resultSet.getString(3),
                                     (UUID) resultSet.getObject(1));
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            throw new DatabaseOperationImpossible(e, "Impossible to select music info. Error message: %s", e.getMessage());
        }
    }
}
