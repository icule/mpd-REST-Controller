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


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by icule on 14/07/17.
 */
public class DatabaseManagerTest {
    private DatabaseManager databaseManager;
    private MusicInfo musicInfo1;
    private MusicInfo musicInfo2;

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
    public void setUp() throws IOException, SQLException {
        ConfigurationManager configurationManager = ConfigurationManagerDefinition.loadConfiguration("../configuration.json");

        Injector injector = Guice.createInjector(new ServerModule(configurationManager));
        databaseManager = injector.getInstance(DatabaseManager.class);
        databaseManager.init();
        deleteDatabase();

        musicInfo1 = new MusicInfo("test1.ogg", "test1", "artist1", null, UUID.randomUUID());
        musicInfo2 = new MusicInfo("test2.ogg", "test2", "artist2", null, UUID.randomUUID());
    }

    private void deleteDatabase() {
        File f = new File("./test-db.mv.db");
        f.delete();
    }

    @Test
    public void testAddMusicData() throws DatabaseOperationImpossible {
        databaseManager.addMusicInfo(musicInfo1);
        assertEquals(musicInfo1, databaseManager.getMusicInfo(musicInfo1.getMusicId()));

        assertNull(databaseManager.getMusicInfo(musicInfo2.getMusicId()));
        databaseManager.addMusicInfo(musicInfo2);
        assertEquals(musicInfo1, databaseManager.getMusicInfo(musicInfo1.getMusicId()));
        assertEquals(musicInfo2, databaseManager.getMusicInfo(musicInfo2.getMusicId()));
    }
}