package iculesgate.mpd_controller.REST;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.server.Core;
import io.logz.guice.jersey.JerseyModule;
import io.logz.guice.jersey.JerseyServer;
import io.logz.guice.jersey.configuration.JerseyConfiguration;
import iculesgate.mpd_controller.mpd.MPDClient;
import iculesgate.mpd_controller.database.DatabaseManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by icule on 25/03/17.
 */
@Singleton
public class RESTServer {

    private JerseyServer server;

    @Inject
    public RESTServer(final ConfigurationManager configurationManager,
                      final MPDClient mpdClient,
                      final DatabaseManager databaseManager,
                      final Core core) throws Exception {
        JerseyConfiguration configuration = JerseyConfiguration.builder()
                .addPackage("iculesgate.mpd_controller")
                .addPort(6062)
                .build();

        List<AbstractModule> modules = new ArrayList<>();
        modules.add(new JerseyModule(configuration));
        modules.add(new RestServerModule(configurationManager, databaseManager, mpdClient, core));

        this.server = Guice.createInjector(modules).getInstance(JerseyServer.class);
        this.server.start();
    }
}
