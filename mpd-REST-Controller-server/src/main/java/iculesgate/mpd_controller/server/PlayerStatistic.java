package iculesgate.mpd_controller.server;

import com.google.inject.Inject;
import iculesgate.mpd_controller.data.MpdMusicInformation;
import iculesgate.mpd_controller.database.DatabaseManager;
import iculesgate.mpd_controller.database.DatabaseOperationImpossible;
import iculesgate.mpd_controller.mpd.MPDClient;


public class PlayerStatistic {
    private final MPDClient mpdClient;
    private final DatabaseManager databaseManager;

    private Thread thread;
    private boolean interrupted;

    @Inject
    public PlayerStatistic(final MPDClient mpdClient,
                           final DatabaseManager databaseManager) {
        this.mpdClient = mpdClient;
        this.databaseManager = databaseManager;
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

    private void waitTime(final long delay) {
        try {
            Thread.sleep(delay);
        }
        catch (InterruptedException e) {
            //
        }
    }

    private void monitor() {
        MpdMusicInformation lastRequested = mpdClient.getInfo();

        while (!interrupted) {
            try {
                MpdMusicInformation currentInfo = mpdClient.getInfo();
                if (!lastRequested.getMusicInfo().getMusicId().equals(currentInfo.getMusicInfo().getMusicId())) {
                    lastRequested = currentInfo;

                    if (databaseManager.getMusicInfo(lastRequested.getMusicInfo().getMusicId()) == null) {
                        databaseManager.addMusicInfo(lastRequested.getMusicInfo());
                    }
                    databaseManager.incrementPlayCount(currentInfo.getMusicInfo().getMusicId());
                }
                waitTime(5000);
            }
            catch (DatabaseOperationImpossible databaseOperationImpossible) {
                //nothing to do
                waitTime(5000);
            }

        }
    }
}
