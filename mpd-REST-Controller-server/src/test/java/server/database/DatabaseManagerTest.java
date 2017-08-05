package server.database;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import common.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import server.data.MusicInfo;
import common.Tag;

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
        @Override
        protected void configure() {
            bind(ConfigurationManager.class);
            bind(DatabaseManager.class).in(Singleton.class);
        }
    }

    @Before
    public void setUp() throws IOException {
        Injector injector = Guice.createInjector(new ServerModule());
        File f = new File("../test.properties");
        System.out.println(f.getAbsolutePath());
        injector.getInstance(ConfigurationManager.class).loadConfiguration("../test.properties");
        databaseManager = injector.getInstance(DatabaseManager.class);

        deleteDatabase();
    }

    private void deleteDatabase() {
        File f = new File("./test-lala.mv.db");
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
        MusicInfo info = new MusicInfo("lala.mp3", "l", "l3", Tag.GOOD);
        databaseManager.registerTag(info);

        databaseManager.close();
    }

    @Test
    public void testGetMusicList() throws SQLException {
        databaseManager.init();
        MusicInfo info = new MusicInfo("lala.mp3", "l", "l3", Tag.TO_REMOVE);

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