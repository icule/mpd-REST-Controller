package iculesgate.mpd_controller.database;

public class DatabaseOperationImpossible extends Exception {
    public DatabaseOperationImpossible(final String format,
                                       final Object... objects) {
        super(String.format(format, objects));
    }

    public DatabaseOperationImpossible(final Exception origin,
                                       final String format,
                                       final Object... objects) {
        super(String.format(format, objects), origin);
    }
}
