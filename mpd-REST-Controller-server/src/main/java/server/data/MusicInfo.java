package server.data;

/**
 * Created by icule on 14/07/17.
 */
public class MusicInfo {
    private String filename;
    private String title;
    private String artist;

    public MusicInfo(String filename, String title, String artist) {
        this.filename = filename;
        this.title = title;
        this.artist = artist;
    }

    public String getFilename() {
        return filename;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
