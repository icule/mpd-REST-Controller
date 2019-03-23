package iculesgate.mpd_controller.data;

public enum PlayerStatus {
    PLAY,
    PAUSE,
    STOP;

    public static PlayerStatus fromPlayerPrefix(final String prefix) {
        switch (prefix) {
            case "stop":
                return STOP;

            case "play":
                return PLAY;

            case "pause":
                return PAUSE;

            default:
                return null;
        }
    }
}
