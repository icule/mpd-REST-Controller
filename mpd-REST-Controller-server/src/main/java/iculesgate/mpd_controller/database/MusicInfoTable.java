package iculesgate.mpd_controller.database;

import iculesgate.mpd_controller.data.MusicInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MusicInfoTable {
    static final String TABLE_NAME = "MusicInfo";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(" +
            "id uuid PRIMARY KEY," +
            "title varchar(250), " +
            "artist varchar(250)," +
            "filename varchar(250)" +
            ");";

    private static final String SELECT_FROM_ID_QUERY = "SELECT * FROM " + TABLE_NAME +
            " WHERE id = ?;";

    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME +
            " VALUES (?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "UPDATE " + TABLE_NAME +
            " SET title = ?, artist = ?, filename = ? " +
            " WHERE id = ?;";

    private static final String SELECT_ALL_ID_QUERY = "SELECT id FROM " + TABLE_NAME + " Where 1;";


    private DatabaseManager databaseManager;


    public MusicInfoTable(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void init() throws SQLException {
        try (PreparedStatement statement = databaseManager.getPreparedStatement(CREATE_TABLE_QUERY)) {
            statement.execute();
        }
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

    void updateMusicData(final MusicInfo musicInfo) throws DatabaseOperationImpossible {
        try (PreparedStatement statement = databaseManager.getPreparedStatement(UPDATE_QUERY)){
            statement.setString(1, musicInfo.getTitle());
            statement.setString(2, musicInfo.getArtist());
            statement.setString(3, musicInfo.getFilename());
            statement.setObject(4, musicInfo.getMusicId()); //where clause

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseOperationImpossible(e, "Impossible to update music info. Error message: %s", e.getMessage());
        }
    }

    List<UUID> getAllKnownId() throws DatabaseOperationImpossible {
        try (PreparedStatement preparedStatement = databaseManager.getPreparedStatement(SELECT_ALL_ID_QUERY)) {
            List<UUID> res = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.add((UUID) resultSet.getObject(1));
            }
            return res;
        }
        catch (SQLException e) {
            throw new DatabaseOperationImpossible(e, "Impossible to get the list of all id. Original error: %s", e.getMessage());
        }
    }
}
