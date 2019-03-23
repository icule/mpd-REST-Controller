package iculesgate.mpd_controller.client;

import com.google.gson.Gson;
import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.data.MpdMusicInformation;
import iculesgate.mpd_controller.data.Tag;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class TargetClient {
    private final Gson gson;

    private final WebTarget targetPlayer;
    private final WebTarget targetTag;

    @Inject
    public TargetClient(final ConfigurationManager configurationManager,
                        final Gson gson) {
        this.gson = gson;
        Client c = ClientBuilder.newClient();

        targetPlayer = c.target("http://" + configurationManager.getUrl() + ":" + configurationManager.getPort() + "/player/" + configurationManager.getAuthToken());
        targetTag = c.target("http://" + configurationManager.getUrl() + ":" + configurationManager.getPort() + "/tag/" + configurationManager.getAuthToken());
    }

    public MpdMusicInformation getCurrentMusicInfo() {
        try (Response response = targetPlayer.path("music").request().get();){
            return gson.fromJson(response.readEntity(String.class), MpdMusicInformation.class);
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
        targetTag.path("tag").request().post(Entity.text(tag));
    }
}
