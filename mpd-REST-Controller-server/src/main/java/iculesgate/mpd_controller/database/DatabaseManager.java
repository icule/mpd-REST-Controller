package iculesgate.mpd_controller.database;

import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.data.MusicStatistic;
import iculesgate.mpd_controller.data.Tag;
import iculesgate.mpd_controller.data.TaggedMusicInfo;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by icule on 13/07/17.
 */
public class DatabaseManager {
    private final Connection connection;
    private final MusicTag musicTag;

    private final MusicInfoTable musicInfoTable;
    private final StatisticTable statisticTable;

    @Inject
    public DatabaseManager(ConfigurationManager configurationManager) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        String jdbcString = "jdbc:h2:" + configurationManager.getDatabaseConfiguration().getPath();
        connection = DriverManager.getConnection(jdbcString);

        musicTag = new MusicTag(this);
        musicInfoTable = new MusicInfoTable(this);
        statisticTable = new StatisticTable(this);
    }

    public void init() throws SQLException, DatabaseOperationImpossible {
        musicInfoTable.init();
        musicTag.init();
        statisticTable.init();
        commit();
    }

    public void commit () throws DatabaseOperationImpossible {
        try {
            connection.commit();
        }
        catch (SQLException e) {
            throw new DatabaseOperationImpossible(e, "Impossible to commit. Original error: %s", e.getMessage());
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        }
        catch (Exception e) {
            //for now we do nothing
        }
    }

    PreparedStatement getPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public void close() throws SQLException {
        connection.close();
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

    public void addTag(final UUID id, final Tag tag) throws DatabaseOperationImpossible {
        musicTag.addTag(id, tag);
    }

    public TaggedMusicInfo getTaggedMusicInfo(final UUID id) throws DatabaseOperationImpossible {
        MusicInfo info = musicInfoTable.getMusicInfo(id);
        if (info == null) {
            return null;
        }

        return new TaggedMusicInfo(info, musicTag.getTagList(id), statisticTable.getPlayCount(id));
    }

    public MusicStatistic getMusicStatistic(final UUID id) throws DatabaseOperationImpossible {
        return statisticTable.getPlayCount(id);
    }

    public void incrementPlayCount(final UUID id) throws DatabaseOperationImpossible {
        statisticTable.incrementPlayCount(id);
        commit();
    }

    public List<TaggedMusicInfo> getAllMusic() throws DatabaseOperationImpossible {
        List<UUID> knownList = musicInfoTable.getAllKnownId();
        List<TaggedMusicInfo> res = new ArrayList<>();

        for (UUID uuid : knownList) {
            res.add(getTaggedMusicInfo(uuid));
        }
        return res;
    }
}
