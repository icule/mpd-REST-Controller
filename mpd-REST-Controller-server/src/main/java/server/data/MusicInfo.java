package server.data;

/**
 * Created by icule on 14/07/17.
 */
public class MusicInfo {
    private String filename;
    private String title;
    private String artist;
    private Tag tag;

    public MusicInfo(String filename, String title, String artist, Tag tag) {
        this.filename = filename;
        this.title = title;
        this.artist = artist;
        this.tag = tag;
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

    public Tag getTag() {
        return tag;
    }
}
