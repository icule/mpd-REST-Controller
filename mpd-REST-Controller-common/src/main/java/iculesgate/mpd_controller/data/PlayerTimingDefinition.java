package iculesgate.mpd_controller.data;


import iculesgate.mpd_controller.annotation.MyStyle;
import org.immutables.value.Value;

@Value.Immutable
@MyStyle
public abstract class PlayerTimingDefinition {
    public abstract long getElapsedTime();

    public abstract long getTotalDuration();

    public static String longToTime(long time) {
        String res = "";
        if(time >= 3600){
            res += time / 3600 + ":";
            time = time % 3600;
        }
        res += time / 60 + ":" + time % 60;
        return res;
    }
}
