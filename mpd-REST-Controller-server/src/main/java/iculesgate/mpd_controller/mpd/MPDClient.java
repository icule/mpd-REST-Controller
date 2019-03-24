package iculesgate.mpd_controller.mpd;

import iculesgate.mpd_controller.configuration.ConfigurationManager;

import iculesgate.mpd_controller.data.*;
import iculesgate.mpd_controller.database.DatabaseManager;
import iculesgate.mpd_controller.utils.MusicIdExtractor;
import org.bff.javampd.player.Player;
import org.bff.javampd.server.MPD;
import org.bff.javampd.server.MPDConnectionException;
import org.bff.javampd.song.MPDSong;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by icule on 25/03/17.
 */
@Singleton
public class MPDClient {
    private final ConfigurationManager configurationManager;
    private final MPD mpdConnection;
    private final DatabaseManager databaseManager;

    @Inject
    public MPDClient(ConfigurationManager configurationManager,
                     DatabaseManager databaseManager) throws MPDConnectionException {
        this.configurationManager = configurationManager;
        this.mpdConnection = new MPD.Builder().build();
        this.databaseManager = databaseManager;
    }

    public void next() {
        this.mpdConnection.getPlayer().playNext();
    }

    public void play() {
        this.mpdConnection.getPlayer().play();
    }

    public void pause() {
        this.mpdConnection.getPlayer().pause();
    }

    public void stop() {
        this.mpdConnection.getPlayer().stop();
    }

    public MpdMusicInformation getInfo() {
        MPDSong mpdSong = this.mpdConnection.getPlayer().getCurrentSong();
        Player mpdPlayer = this.mpdConnection.getPlayer();
        int songCount = this.mpdConnection.getPlaylist().getSongList().size();

        PlaylistPosition playlistPosition = new PlaylistPosition(mpdSong.getPosition(), songCount);
        PlayerStatus status = PlayerStatus.fromPlayerPrefix(mpdPlayer.getStatus().getPrefix());
        MusicInfo musicInfo = new MusicInfo(mpdSong.getFile(), mpdSong.getTitle(), mpdSong.getArtistName(), getMusicId(mpdSong.getFile()));
        PlayerTiming playerTiming = new PlayerTiming(mpdPlayer.getElapsedTime(), mpdPlayer.getTotalTime());

        return new MpdMusicInformation(status, musicInfo, playlistPosition, playerTiming);
    }

    private UUID getMusicId(final String relativePath) {
        return MusicIdExtractor.getLibraryId(configurationManager.getLibraryConfiguration().getRootPath() + "/" + relativePath);
    }


    public MPDSong getMusicInfo() {
        return this.mpdConnection.getPlayer().getCurrentSong();
    }
}
