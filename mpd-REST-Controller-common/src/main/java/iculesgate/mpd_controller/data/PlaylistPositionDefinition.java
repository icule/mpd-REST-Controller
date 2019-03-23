package iculesgate.mpd_controller.data;

import iculesgate.mpd_controller.annotation.MyStyle;
import org.immutables.value.Value;

@Value.Immutable
@MyStyle
public interface PlaylistPositionDefinition {
    int getPosition();

    int getPlaylistSize();
}
