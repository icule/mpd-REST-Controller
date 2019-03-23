package iculesgate.mpd_controller.utils;

import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

import java.util.UUID;

public class MusicIdExtractor {
    private static MediaPlayerFactory factory = new MediaPlayerFactory();

    private static UUID getId(final String path) {
        try {
            MediaMeta meta = factory.getMediaMeta(path, true);
            return UUID.fromString(meta.getEncodedBy());
        }
        catch (Exception e) {
            return null;
        }
    }

    public static UUID getLibraryId(final String filepath) {
        return getId(filepath);
    }
}
