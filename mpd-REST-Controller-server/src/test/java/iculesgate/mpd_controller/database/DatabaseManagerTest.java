package iculesgate.mpd_controller.database;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.configuration.ConfigurationManagerDefinition;
import iculesgate.mpd_controller.data.MusicInfo;
import org.junit.Before;
import org.junit.Test;
import iculesgate.mpd_controller.data.Tag;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by icule on 14/07/17.
 */
public class DatabaseManagerTest {
    private DatabaseManager databaseManager;

    private static class ServerModule extends AbstractModule {
        private final ConfigurationManager configurationManager;

        private ServerModule(final ConfigurationManager configurationManager) {
            this.configurationManager = configurationManager;
        }

        @Override
        protected void configure() {
            bind(ConfigurationManager.class).toInstance(configurationManager);
            bind(DatabaseManager.class).in(Singleton.class);
        }
    }

    @Before
    public void setUp() throws IOException {
        ConfigurationManager configurationManager = ConfigurationManagerDefinition.loadConfiguration("../configuration.json");

        Injector injector = Guice.createInjector(new ServerModule(configurationManager));
        databaseManager = injector.getInstance(DatabaseManager.class);

        deleteDatabase();
    }

    private void deleteDatabase() {
        File f = new File("./test-db.mv.db");
        f.delete();
    }

    @Test
    public void testInitClose() throws SQLException {
        databaseManager.init();
        databaseManager.close();
    }

    @Test
    public void testInsertTagMusic() throws SQLException {
        databaseManager.init();
        MusicInfo info = new MusicInfo("lala.mp3", "l", "l3", Tag.GOOD, null);
        databaseManager.registerTag(info);

        databaseManager.close();
    }

    @Test
    public void testGetMusicList() throws SQLException {
        databaseManager.init();
        MusicInfo info = new MusicInfo("lala.mp3", "l", "l3", Tag.TO_REMOVE, null);

        databaseManager.registerTag(info);
        List<MusicInfo> infoList = databaseManager.getTaggedMusic();
        assertEquals(1, infoList.size());
        MusicInfo res = infoList.get(0);
        assertEquals("lala.mp3", res.getFilename());
        assertEquals("l", res.getTitle());
        assertEquals("l3", res.getArtist());

        databaseManager.close();
    }

}