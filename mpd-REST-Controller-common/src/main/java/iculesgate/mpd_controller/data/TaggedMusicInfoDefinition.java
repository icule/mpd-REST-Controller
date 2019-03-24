package iculesgate.mpd_controller.data;

import iculesgate.mpd_controller.annotation.MyStyle;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@MyStyle
public interface TaggedMusicInfoDefinition {
    MusicInfo getMusicInfo();

    List<Tag> getTagList();
}
