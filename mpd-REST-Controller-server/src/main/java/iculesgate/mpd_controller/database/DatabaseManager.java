package iculesgate.mpd_controller.database;

import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.data.Tag;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by icule on 13/07/17.
 */
public class DatabaseManager {
    private final Connection connection;
    private final MusicTag musicTag;

    private final MusicInfoTable musicInfoTable;

    @Inject
    public DatabaseManager(ConfigurationManager configurationManager) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        String jdbcString = "jdbc:h2:" + configurationManager.getDatabaseConfiguration().getPath();
        connection = DriverManager.getConnection(jdbcString);

        musicTag = new MusicTag(this);
        musicInfoTable = new MusicInfoTable(this);
    }

    public void init() throws SQLException {
        musicTag.init();
        musicInfoTable.init();
        commit();
    }

    public void commit () throws SQLException {
        connection.commit();
    }

    PreparedStatement getPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }


    public void close() throws SQLException {
        connection.close();
    }

    public void registerTag(MusicInfo info) throws SQLException {

    }

    public void addMusicInfo(final MusicInfo musicInfo) throws DatabaseOperationImpossible {
        musicInfoTable.addMusicInfo(musicInfo);
    }

    public MusicInfo getMusicInfo(final UUID id) throws DatabaseOperationImpossible {
        return musicInfoTable.getMusicInfo(id);
    }

    public void updateMusicInfo(final MusicInfo musicInfo) throws DatabaseOperationImpossible {
        musicInfoTable.updateMusicData(musicInfo);
    }

    public List<MusicInfo> getTaggedMusic() throws SQLException {
        return Collections.emptyList();
    }

    public Tag getTag(String filename) throws SQLException {
        return null;
    }
}
