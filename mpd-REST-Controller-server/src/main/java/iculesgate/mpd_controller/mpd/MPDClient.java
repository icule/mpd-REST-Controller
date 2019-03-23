package iculesgate.mpd_controller.mpd;

import iculesgate.mpd_controller.configuration.ConfigurationManager;

import iculesgate.mpd_controller.data.*;
import iculesgate.mpd_controller.database.DatabaseManager;
import org.bff.javampd.player.Player;
import org.bff.javampd.server.MPD;
import org.bff.javampd.server.MPDConnectionException;
import org.bff.javampd.song.MPDSong;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;

/**
 * Created by icule on 25/03/17.
 */
@Singleton
public class MPDClient {
    private final MPD mpdConnection;
    private final DatabaseManager databaseManager;

    @Inject
    public MPDClient(ConfigurationManager configurationManager,
                     DatabaseManager databaseManager) throws MPDConnectionException {
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

    private String longToTime(long time) {
        String res = "";
        if(time >= 3600){
            res += time / 3600 + ":";
            time = time % 3600;
        }
        res += time / 60 + ":" + time % 60;
        return res;
    }

    public MpdMusicInformation getInfo() {
        MPDSong mpdSong = this.mpdConnection.getPlayer().getCurrentSong();
        Player mpdPlayer = this.mpdConnection.getPlayer();

        int songCount = this.mpdConnection.getPlaylist().getSongList().size();
        String res = mpdSong.getArtistName() + " - " + mpdSong.getTitle();

        res += "\n[" + mpdPlayer.getStatus().getPrefix() + "]";
        res += " #" + mpdSong.getPosition() + "/" + songCount;
        res += "  " + longToTime(mpdPlayer.getElapsedTime()) + "/" + longToTime(mpdPlayer.getTotalTime());

        res += "\n" + mpdSong.getFile();

        PlaylistPosition playlistPosition = new PlaylistPosition(mpdSong.getPosition(), songCount);
        PlayerStatus status = PlayerStatus.fromPlayerPrefix(mpdPlayer.getStatus().getPrefix());
        MusicInfo musicInfo = new MusicInfo(mpdSong.getFile(), mpdSong.getTitle(), mpdSong.getArtistName(), getTag(mpdSong));
        PlayerTiming playerTiming = new PlayerTiming(mpdPlayer.getElapsedTime(), mpdPlayer.getTotalTime());

        return new MpdMusicInformation(status, musicInfo, playlistPosition, playerTiming);
    }

    public Tag getTag(final MPDSong mpdSong) {
        try {
            return databaseManager.getTag(mpdSong.getFile());
        }
        catch (SQLException e) {
            return null;
        }
    }


    public MPDSong getMusicInfo() {
        return this.mpdConnection.getPlayer().getCurrentSong();
    }
}
