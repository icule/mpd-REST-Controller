package iculesgate.mpd_controller.client;

import iculesgate.mpd_controller.configuration.ConfigurationManager;
import iculesgate.mpd_controller.data.Tag;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

public class TargetClient {
    private final WebTarget targetPlayer;
    private final WebTarget targetTag;

    @Inject
    public TargetClient(final ConfigurationManager configurationManager) {
        Client c = ClientBuilder.newClient();

        targetPlayer = c.target("http://" + configurationManager.getCompleteUrl() + "/player/" + configurationManager.getAuthToken());
        targetTag = c.target("http://" + configurationManager.getCompleteUrl() + "/tag/" + configurationManager.getAuthToken());
    }

    public String getCurrentMusicInfo() {
        return targetPlayer.path("music").request().get(String.class);
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
