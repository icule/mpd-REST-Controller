package iculesgate.mpd_controller.REST;

import iculesgate.mpd_controller.configuration.ConfigurationManager;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import iculesgate.mpd_controller.mpd.MPDClient;
import iculesgate.mpd_controller.database.DatabaseManager;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by icule on 25/03/17.
 */
@Singleton
public class RESTServer {
    private HttpServer server;

    @Inject
    public RESTServer(ConfigurationManager configurationManager, MPDClient mpdClient, DatabaseManager databaseManager) throws IOException {
        final ResourceConfig rc = new ResourceConfig().packages("iculesgate.mpd_controller.REST");
        Map<String, Object> config = new HashMap<>();
        config.put("authToken", configurationManager.getAuthToken());
        config.put("mpdClient", mpdClient);
        config.put("database", databaseManager);
        rc.addProperties(config);
        this.server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://" + configurationManager.getCompleteUrl()), rc);
    }
}
