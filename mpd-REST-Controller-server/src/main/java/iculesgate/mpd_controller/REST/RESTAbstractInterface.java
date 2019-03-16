package iculesgate.mpd_controller.REST;


import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.mpd.MPDClient;
import iculesgate.mpd_controller.database.DatabaseManager;

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


    String getAuthToken() {
        return configurationManager.getAuthToken();
    }

    MPDClient getMPDClient() {
        return mpdClient;
    }

    DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    void checkToken(final String token) throws AuthenticationException {
        if (!token.equals(configurationManager.getAuthToken())) {
            throw new AuthenticationException("Token " + token + " isn't valid");
        }
    }
}
