package iculesgate.mpd_controller.REST;


import com.google.gson.Gson;
import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.mpd.MPDClient;
import iculesgate.mpd_controller.database.DatabaseManager;
import iculesgate.mpd_controller.server.Core;

import javax.inject.Inject;

/**
 * Created by icule on 17/07/17.
 */
public class RESTAbstractInterface {
    @Inject
    private DatabaseManager databaseManager;

    @Inject
    private ConfigurationManager configurationManager;

    @Inject
    private MPDClient mpdClient;

    @Inject
    private Gson gson;

    @Inject
    private Core core;


    String getAuthToken() {
        return configurationManager.getAuthToken();
    }

    MPDClient getMPDClient() {
        return mpdClient;
    }

    DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public Gson getGson() {
        return gson;
    }

    Core getCore() {
        return core;
    }

    void checkToken(final String token) throws AuthenticationException {
        if (!token.equals(configurationManager.getAuthToken())) {
            throw new AuthenticationException("Token " + token + " isn't valid");
        }
    }
}
