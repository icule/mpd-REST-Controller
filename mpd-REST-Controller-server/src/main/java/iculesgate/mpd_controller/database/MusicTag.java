package iculesgate.mpd_controller.database;

import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.data.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private static final String SELECT_FROM_ID_QUERY = "SELECT * FROM " + TABLE_NAME +
            " WHERE id = ?;";


    private DatabaseManager databaseManager;


    public MusicTag(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void init() throws SQLException {
        PreparedStatement statement = databaseManager.getPreparedStatement(CREATE_QUERY);
        statement.execute();
    }
}
