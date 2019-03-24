package iculesgate.mpd_controller.server;

import iculesgate.mpd_controller.data.MpdMusicInformation;
import iculesgate.mpd_controller.data.Tag;
import iculesgate.mpd_controller.data.TaggedMusicInfo;
import iculesgate.mpd_controller.database.DatabaseManager;
import iculesgate.mpd_controller.database.DatabaseOperationImpossible;
import iculesgate.mpd_controller.mpd.MPDClient;

import java.util.UUID;

public class Core {
    private final DatabaseManager databaseManager;
    private final MPDClient mpdClient;

    public Core(final DatabaseManager databaseManager, final MPDClient mpdClient) {
        this.databaseManager = databaseManager;
        this.mpdClient = mpdClient;
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
