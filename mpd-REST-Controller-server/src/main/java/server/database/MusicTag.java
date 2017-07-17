package server.database;

import server.data.MusicInfo;
import server.data.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by icule on 13/07/17.
 */
public class MusicTag {
    private DatabaseManager databaseManager;


    public MusicTag(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void init() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " +
                "MusicTag (" +
                "filename varchar(250) PRIMARY KEY," +
                "title varchar(250), " +
                "artist varchar(250)," +
                "tag varchar(25));";
        PreparedStatement statement = databaseManager.getPreparedStatement(query);
        statement.execute();
        System.out.println("Execute create table request");
    }

    public void registerTag(MusicInfo info) throws SQLException {
        String query = "INSERT INTO MusicTag VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = databaseManager.getPreparedStatement(query);
        preparedStatement.setString(1, info.getFilename());
        preparedStatement.setString(2, info.getTitle());
        preparedStatement.setString(3, info.getArtist());
        preparedStatement.setString(4, info.getTag().toString());
        preparedStatement.executeUpdate();
    }

    public List<MusicInfo> getMusicInfoList() throws SQLException {
        List<MusicInfo> res = new ArrayList<>();
        String query = "SELECT * FROM MusicTag;";
        PreparedStatement statement = databaseManager.getPreparedStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            MusicInfo info = new MusicInfo(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Tag.valueOf(resultSet.getString(4)));
            res.add(info);
        }

        return res;
    }

}
