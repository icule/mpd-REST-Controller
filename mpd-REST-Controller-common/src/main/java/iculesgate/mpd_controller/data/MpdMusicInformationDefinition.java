package iculesgate.mpd_controller.data;

import iculesgate.mpd_controller.annotation.MyStyle;
import org.immutables.value.Value;

@Value.Immutable
@MyStyle
public interface MpdMusicInformationDefinition {
    PlayerStatus getPlayerStatus();

    MusicInfo getMusicInfo();

    PlaylistPosition getPlaylistPosition();

    PlayerTiming getPlayerTiming();
}
