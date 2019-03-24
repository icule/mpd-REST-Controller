package iculesgate.mpd_controller.database;

import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.data.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by icule on 13/07/17.
 */
public class MusicTag {
    private static final String TABLE_NAME = "Tag";
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(" +
            "id uuid not null," +
            "tag varchar(25) not null" +
            ");" +
            "ALTER TABLE " + TABLE_NAME + " " +
            "ADD FOREIGN KEY (id) REFERENCES " + MusicInfoTable.TABLE_NAME + "(id);";

    private static final String INSERT_VALUE_QUERY = "INSERT INTO " + TABLE_NAME +
            " VALUES (?, ?);";

    private static final String SELECT_FROM_ID_QUERY = "SELECT tag FROM " + TABLE_NAME +
            " WHERE id = ?;";


    private DatabaseManager databaseManager;


    public MusicTag(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void init() throws SQLException {
        try (PreparedStatement statement = databaseManager.getPreparedStatement(CREATE_QUERY)) {
            statement.execute();
        }
    }

    public void addTag(final UUID id, final Tag tag) throws DatabaseOperationImpossible {
        try (PreparedStatement statement = databaseManager.getPreparedStatement(INSERT_VALUE_QUERY)) {
            statement.setObject(1, id);
            statement.setString(2, tag.toString());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseOperationImpossible(e, "Impossible to insert the tag. Original error: %s", e.getMessage());
        }
    }

    public List<Tag> getTagList(final UUID id) throws DatabaseOperationImpossible {
        List<Tag> res = new ArrayList<>();

        try (PreparedStatement statement = databaseManager.getPreparedStatement(SELECT_FROM_ID_QUERY)) {
            statement.setObject(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                res.add(Tag.valueOf(resultSet.getString(1)));
            }
        }
        catch (SQLException e) {
            throw new DatabaseOperationImpossible(e, "Impossible to retrieve the tag list. Original error: %s", e.getMessage());
        }

        return res.stream().distinct().collect(Collectors.toList());
    }
}
