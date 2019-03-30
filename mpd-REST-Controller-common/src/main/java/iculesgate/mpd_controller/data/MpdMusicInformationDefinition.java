package iculesgate.mpd_controller.data;

import iculesgate.mpd_controller.annotation.MyStyle;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@MyStyle
public interface MpdMusicInformationDefinition {
    PlayerStatus getPlayerStatus();

    MusicInfo getMusicInfo();

    PlaylistPosition getPlaylistPosition();

    PlayerTiming getPlayerTiming();

    List<Tag> getTagList();

    MusicStatistic getMusicStatistic();
}
