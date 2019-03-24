package iculesgate.mpd_controller.data;

import iculesgate.mpd_controller.annotation.MyStyle;
import iculesgate.mpd_controller.annotation.Nullable;
import org.immutables.value.Value;

import java.util.UUID;

/**
 * Created by icule on 14/07/17.
 */
@Value.Immutable
@MyStyle
public interface MusicInfoDefinition {
    String getFilename();

    String getTitle();

    String getArtist();

    @Nullable
    UUID getMusicId();
}
