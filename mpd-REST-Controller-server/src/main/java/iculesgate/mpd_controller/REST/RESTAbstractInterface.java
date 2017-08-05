package iculesgate.mpd_controller.REST;

import iculesgate.mpd_controller.mpd.MPDClient;
import iculesgate.mpd_controller.database.DatabaseManager;

import javax.inject.Inject;
import javax.ws.rs.core.Application;

/**
 * Created by icule on 17/07/17.
 */
public class RESTAbstractInterface {
    @Inject
    private Application application;

    String getAuthToken(){
        return (String)application.getProperties().get("authToken");
    }

    MPDClient getMPDClient() {
        return (MPDClient)application.getProperties().get("mpdClient");
    }

    DatabaseManager getDatabaseManager() {
        return (DatabaseManager)application.getProperties().get("database");
    }
}
