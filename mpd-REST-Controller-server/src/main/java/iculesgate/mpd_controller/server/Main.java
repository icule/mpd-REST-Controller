package iculesgate.mpd_controller.server;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import iculesgate.mpd_controller.configuration.ConfigurationManager;
import org.slf4j.LoggerFactory;
import iculesgate.mpd_controller.REST.RESTServer;
import iculesgate.mpd_controller.database.DatabaseManager;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Main class.
 *
 */
public class Main {
    private static class ServerModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(ConfigurationManager.class);
            bind(RESTServer.class);
            bind(DatabaseManager.class).in(Singleton.class);
        }
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, SQLException {
        Injector injector = Guice.createInjector(new ServerModule());
        injector.getInstance(ConfigurationManager.class).loadConfiguration("config.properties");

        DatabaseManager manager = injector.getInstance(DatabaseManager.class);
        manager.init();
        injector.getInstance(RESTServer.class);
    }
}

