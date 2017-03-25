package server;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import common.ConfigurationManager;

import java.io.IOException;


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
        }
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Injector injector = Guice.createInjector(new ServerModule());
        injector.getInstance(ConfigurationManager.class).loadConfiguration("config.properties");
        injector.getInstance(RESTServer.class);
    }
}

