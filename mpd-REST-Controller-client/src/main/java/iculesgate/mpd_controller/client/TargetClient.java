package iculesgate.mpd_controller.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.data.MpdMusicInformation;
import iculesgate.mpd_controller.data.Tag;
import iculesgate.mpd_controller.data.TaggedMusicInfo;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.List;

public class TargetClient {
    private static final Type TAGGED_MUSIC_LIST_TYPE = new TypeToken<List<TaggedMusicInfo>>(){}.getType();

    private final Gson gson;

    private final WebTarget targetPlayer;
    private final WebTarget targetTag;
    private final WebTarget musicPath;

    @Inject
    public TargetClient(final ConfigurationManager configurationManager,
                        final Gson gson) {
        this.gson = gson;
        Client c = ClientBuilder.newClient();

        targetPlayer = c.target("http://" + configurationManager.getUrl() + ":" + configurationManager.getPort() + "/mpd/player/" + configurationManager.getAuthToken());
        targetTag = c.target("http://" + configurationManager.getUrl() + ":" + configurationManager.getPort() + "/mpd/tag/" + configurationManager.getAuthToken());
        musicPath = c.target("http://" + configurationManager.getUrl() + ":" + configurationManager.getPort() + "/mpd/music/" + configurationManager.getAuthToken());
    }

    public MpdMusicInformation getCurrentMusicInfo() {
        try (Response response = targetPlayer.path("music").request().get();){
            String payload = response.readEntity(String.class);
            return gson.fromJson(payload, MpdMusicInformation.class);
        }
    }

    public void nextMusic() {
        targetPlayer.path("next").request().put(Entity.text(""));
    }

    public void play() {
        targetPlayer.path("play").request().put(Entity.text(""));
    }

    public void pause() {
        targetPlayer.path("pause").request().put(Entity.text(""));
    }

    public void stop() {
        targetPlayer.path("stop").request().put(Entity.text(""));
    }

    public void addTag(final Tag tag) {
        targetTag.path("tag").request().post(Entity.text(tag.toString()));
    }

    public List<TaggedMusicInfo> getAllMusic() {
        return gson.fromJson(musicPath.path("statistic").request().get(String.class), TAGGED_MUSIC_LIST_TYPE);
    }
}
