package server;

import common.ConfigurationManager;
import org.bff.javampd.MPD;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDPlayerException;

import javax.inject.Inject;

/**
 * Created by icule on 25/03/17.
 */
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
}
