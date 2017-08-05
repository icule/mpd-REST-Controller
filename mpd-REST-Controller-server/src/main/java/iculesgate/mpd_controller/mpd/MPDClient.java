package iculesgate.mpd_controller.mpd;

import iculesgate.mpd_controller.configuration.ConfigurationManager;
import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.objects.MPDSong;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by icule on 25/03/17.
 */
@Singleton
public class MPDClient {
    private MPD mpdConnection;

    @Inject
    public MPDClient(ConfigurationManager configurationManager) throws MPDConnectionException {
      this.mpdConnection = (new MPD.Builder()).build();
    }

    public void next() throws MPDPlayerException {
        this.mpdConnection.getPlayer().playNext();
    }

    public void play() throws MPDPlayerException {
        this.mpdConnection.getPlayer().play();
    }

    public void pause() throws MPDPlayerException {
        this.mpdConnection.getPlayer().pause();
    }

    public void stop() throws MPDPlayerException {
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

    public String getInfo() throws MPDPlayerException, MPDDatabaseException {
        MPDSong mpdSong = this.mpdConnection.getPlayer().getCurrentSong();
        Player mpdPlayer = this.mpdConnection.getPlayer();

        int songCount = this.mpdConnection.getPlaylist().getDatabase().getSongCount();
        String res = mpdSong.getArtistName() + " - " + mpdSong.getTitle();

        res += "\n[" + mpdPlayer.getStatus().getPrefix() + "]";
        res += " #" + mpdSong.getId() + "/" + songCount;
        res += "  " + longToTime(mpdPlayer.getElapsedTime()) + "/" + longToTime(mpdPlayer.getTotalTime());

        res += "\n" + mpdSong.getFile();
        return res;
    }

    public MPDSong getMusicInfo() throws MPDPlayerException {
        return this.mpdConnection.getPlayer().getCurrentSong();
    }
}
