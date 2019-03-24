package iculesgate.mpd_controller.REST;

import com.google.inject.AbstractModule;
import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.database.DatabaseManager;
import iculesgate.mpd_controller.mpd.MPDClient;
import iculesgate.mpd_controller.server.Core;

class RestServerModule extends AbstractModule {
    private final ConfigurationManager configurationManager;
    private final DatabaseManager databaseManager;
    private final MPDClient mpdClient;
    private final Core core;

    public RestServerModule(final ConfigurationManager configurationManager,
                            final DatabaseManager databaseManager,
                            final MPDClient mpdClient,
                            final Core core) {
        this.configurationManager = configurationManager;
        this.databaseManager = databaseManager;
        this.mpdClient = mpdClient;
        this.core = core;
    }


    @Override
    protected void configure() {
        bind(ConfigurationManager.class).toInstance(configurationManager);
        bind(DatabaseManager.class).toInstance(databaseManager);
        bind(MPDClient.class).toInstance(mpdClient);
        bind(Core.class).toInstance(core);
    }
}
