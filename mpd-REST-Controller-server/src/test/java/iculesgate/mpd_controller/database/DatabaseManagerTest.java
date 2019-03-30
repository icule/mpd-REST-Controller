package iculesgate.mpd_controller.database;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.configuration.ConfigurationManagerDefinition;
import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.data.MusicStatistic;
import iculesgate.mpd_controller.data.Tag;
import iculesgate.mpd_controller.data.TaggedMusicInfo;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
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
    public void setUp() throws IOException, SQLException, DatabaseOperationImpossible {
        ConfigurationManager configurationManager = ConfigurationManagerDefinition.loadConfiguration("../configuration.json");

        Injector injector = Guice.createInjector(new ServerModule(configurationManager));
        databaseManager = injector.getInstance(DatabaseManager.class);
        databaseManager.init();
        deleteDatabase();

        musicInfo1 = new MusicInfo("test1.ogg", "test1", "artist1", UUID.randomUUID());
        musicInfo2 = new MusicInfo("test2.ogg", "test2", "artist2", UUID.randomUUID());
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

    @Test
    public void testUpdateMusicData() throws DatabaseOperationImpossible {
        databaseManager.updateMusicInfo(musicInfo1);
        assertNull(databaseManager.getMusicInfo(musicInfo1.getMusicId()));

        testAddMusicData();
        MusicInfo updated = new MusicInfo(musicInfo2.getFilename(), musicInfo1.getTitle(), musicInfo1.getArtist(), musicInfo1.getMusicId());
        databaseManager.updateMusicInfo(updated);
        assertEquals(updated, databaseManager.getMusicInfo(musicInfo1.getMusicId()));
        assertEquals(musicInfo2, databaseManager.getMusicInfo(musicInfo2.getMusicId()));
    }

    private void assertTag(final MusicInfo musicInfo, final Tag... tag) throws DatabaseOperationImpossible {
        TaggedMusicInfo tagged = databaseManager.getTaggedMusicInfo(musicInfo.getMusicId());
        assertEquals(musicInfo, tagged.getMusicInfo());
        assertThat(tagged.getTagList(), Matchers.containsInAnyOrder(tag));
    }

    @Test
    public void testTag() throws DatabaseOperationImpossible {
        assertNull(databaseManager.getTaggedMusicInfo(musicInfo1.getMusicId()));
        databaseManager.addMusicInfo(musicInfo1);
        assertTag(musicInfo1);

        databaseManager.addTag(musicInfo1.getMusicId(), Tag.TO_REMOVE);
        assertTag(musicInfo1, Tag.TO_REMOVE);

        databaseManager.addTag(musicInfo1.getMusicId(), Tag.GOOD);
        assertTag(musicInfo1, Tag.GOOD, Tag.TO_REMOVE);
    }

    @Test
    public void testIncrement() throws DatabaseOperationImpossible {
        assertEquals(new MusicStatistic(0), databaseManager.getMusicStatistic(musicInfo1.getMusicId()));

        databaseManager.addMusicInfo(musicInfo1);
        assertEquals(new MusicStatistic(0), databaseManager.getMusicStatistic(musicInfo1.getMusicId()));

        databaseManager.incrementPlayCount(musicInfo1.getMusicId());
        assertEquals(new MusicStatistic(1), databaseManager.getMusicStatistic(musicInfo1.getMusicId()));

        databaseManager.incrementPlayCount(musicInfo1.getMusicId());
        assertEquals(new MusicStatistic(2), databaseManager.getMusicStatistic(musicInfo1.getMusicId()));
    }
}