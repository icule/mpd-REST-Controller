package iculesgate.mpd_controller.database;

import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.data.Tag;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by icule on 13/07/17.
 */
public class DatabaseManager {
    private final Connection connection;
    private final MusicTag musicTag;

    @Inject
    public DatabaseManager(ConfigurationManager configurationManager) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        String jdbcString = "jdbc:h2:" + configurationManager.getDatabaseConfiguration().getPath();
        connection = DriverManager.getConnection(jdbcString);

        musicTag = new MusicTag(this);

    }

    public void init() throws SQLException {
        musicTag.init();
        commit();
    }

    public void commit () throws SQLException {
        connection.commit();
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }


    public void close() throws SQLException {
        connection.close();
    }

    public void registerTag(MusicInfo info) throws SQLException {
        this.musicTag.registerTag(info);
        this.commit();
        System.out.println(getTaggedMusic());
    }

    public List<MusicInfo> getTaggedMusic() throws SQLException {
        return this.musicTag.getMusicInfoList();
    }

    public Tag getTag(String filename) throws SQLException {
        return musicTag.getTag(filename);
    }
}
