package iculesgate.mpd_controller.configuration;

import com.google.gson.Gson;
import iculesgate.mpd_controller.annotation.MyStyle;
import org.immutables.value.Value;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.util.Properties;

/**
 * Created by icule on 26/02/17.
 */
@Value.Immutable
@MyStyle
public abstract class ConfigurationManagerDefinition {
    public abstract String getAuthToken();

    public abstract String getUrl();

    public abstract String getPort();

    public abstract DatabaseConfiguration getDatabaseConfiguration();

    public abstract MusicLibraryConfiguration getLibraryConfiguration();

    public static ConfigurationManager loadConfiguration(final String path) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(path), ConfigurationManager.class);
    }
}
