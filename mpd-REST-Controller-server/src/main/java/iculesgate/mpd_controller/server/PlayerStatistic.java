package iculesgate.mpd_controller.server;

import com.google.inject.Inject;
import iculesgate.mpd_controller.data.MpdMusicInformation;
import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.mpd.MPDClient;
import iculesgate.mpd_controller.utils.MusicIdExtractor;

import java.util.UUID;

public class PlayerStatistic {
    private final MPDClient mpdClient;


    private Thread thread;
    private boolean interrupted;

    @Inject
    public PlayerStatistic(final MPDClient mpdClient) {
        this.mpdClient = mpdClient;
    }

    public void startMonitoring() {
        interrupted = false;

        thread = new Thread(this::monitor);
        thread.setDaemon(true);
        thread.start();
    }

    public void stopMonitoring() {
        interrupted = true;

        thread.interrupt();
    }

    private void monitor() {
        MpdMusicInformation lastRequested = mpdClient.getInfo();

        while (!interrupted) {
            MpdMusicInformation currentInfo = mpdClient.getInfo();
            if (!lastRequested.getMusicInfo().getMusicId().equals(currentInfo.getMusicInfo().getMusicId())) {
                lastRequested = currentInfo;

            }
        }
    }
}
