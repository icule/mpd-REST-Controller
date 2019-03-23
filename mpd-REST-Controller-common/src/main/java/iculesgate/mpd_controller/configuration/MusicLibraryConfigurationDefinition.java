package iculesgate.mpd_controller.configuration;

import iculesgate.mpd_controller.annotation.MyStyle;
import org.immutables.value.Value;

@Value.Immutable
@MyStyle
public interface MusicLibraryConfigurationDefinition {
    String getRootPath();
}
