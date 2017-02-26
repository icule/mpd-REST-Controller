package server;

import common.ConfigurationManager;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class.
 *
 */
public class Main {
    public static final String BASE_URI = "http://localhost:6061/";


    public static HttpServer startServer() throws IOException {
        ConfigurationManager configurationManager = new ConfigurationManager("config.properties");

        final ResourceConfig rc = new ResourceConfig().packages("server");
        Map<String, Object> config = new HashMap<String, Object>();
        config.put("authToken", configurationManager.getAuthToken());
        rc.addProperties(config);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create("http://" + configurationManager.getCompleteUrl()), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final HttpServer server = startServer();
    }
}

