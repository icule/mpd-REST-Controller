package iculesgate.mpd_controller.server;

import iculesgate.mpd_controller.data.MpdMusicInformation;
import iculesgate.mpd_controller.data.Tag;
import iculesgate.mpd_controller.data.TaggedMusicInfo;
import iculesgate.mpd_controller.database.DatabaseManager;
import iculesgate.mpd_controller.database.DatabaseOperationImpossible;
import iculesgate.mpd_controller.mpd.MPDClient;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.UUID;

public class Core {
    private final DatabaseManager databaseManager;
    private final MPDClient mpdClient;
    private final PlayerStatistic playerStatistic;

    @Inject
    public Core(final DatabaseManager databaseManager,
                final MPDClient mpdClient,
                final PlayerStatistic playerStatistic) {
        this.databaseManager = databaseManager;
        this.mpdClient = mpdClient;
        this.playerStatistic = playerStatistic;
    }

    public void init() throws SQLException, DatabaseOperationImpossible {
        databaseManager.init();

        playerStatistic.startMonitoring();
    }

    public MpdMusicInformation getCurrentMusicInfo() {
        return mpdClient.getInfo();
    }

    public void addTagToCurrentMusic(final Tag tag) throws DatabaseOperationImpossible {
        MpdMusicInformation current = mpdClient.getInfo();

        try {
            if (databaseManager.getMusicInfo(current.getMusicInfo().getMusicId()) == null) {
                databaseManager.addMusicInfo(current.getMusicInfo());
            }
            databaseManager.addTag(current.getMusicInfo().getMusicId(), tag);
            databaseManager.commit();
        }
        catch (DatabaseOperationImpossible e) {
            databaseManager.rollback();
            throw e;
        }

    }

    public TaggedMusicInfo getTaggedInformation(final UUID uuid) throws DatabaseOperationImpossible {
        return databaseManager.getTaggedMusicInfo(uuid);
    }
}
