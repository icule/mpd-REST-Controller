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
    private DatabaseManager databaseManager;


    public MusicTag(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void init() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " +
                "MusicIdExtractor (" +
                "filename varchar(250) PRIMARY KEY," +
                "title varchar(250), " +
                "artist varchar(250)," +
                "music_tag varchar(25));";
        System.out.println(query);
        PreparedStatement statement = databaseManager.getPreparedStatement(query);
        statement.execute();
        System.out.println("Execute create table request");
    }

    public void registerTag(MusicInfo info) throws SQLException {
        try {
            String query = "INSERT INTO MusicIdExtractor VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(query);
            preparedStatement.setString(1, info.getFilename());
            preparedStatement.setString(2, info.getTitle());
            preparedStatement.setString(3, info.getArtist());
            preparedStatement.setString(4, info.getTag().toString());
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public List<MusicInfo> getMusicInfoList() throws SQLException {
        List<MusicInfo> res = new ArrayList<>();
        String query = "SELECT * FROM MusicIdExtractor;";
        PreparedStatement statement = databaseManager.getPreparedStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            MusicInfo info = new MusicInfo(resultSet.getString(1),
                                           resultSet.getString(2),
                                           resultSet.getString(3),
                                           Tag.valueOf(resultSet.getString(4)),
                                           null);
            res.add(info);
        }
        statement.close();

        return res;
    }

    public Tag getTag(String filename) throws SQLException {
        Tag res = null;
        String query = "SELECT tag from MusicIdExtractor WHERE filename=?";
        PreparedStatement statement = databaseManager.getPreparedStatement(query);
        statement.setString(1, filename);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()) {
            res = Tag.valueOf(resultSet.getString(1));
        }
        resultSet.close();
        return res;
    }

}
