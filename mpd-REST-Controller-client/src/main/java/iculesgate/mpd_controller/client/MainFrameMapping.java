package iculesgate.mpd_controller.client;

import iculesgate.mpd_controller.data.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import javax.inject.Inject;
import java.io.IOException;

public class MainFrameMapping {
    @FXML
    private ComboBox<Tag> tagCombo;

    @FXML
    private TextArea musicInformationArea;

    private final TargetClient client;

    @Inject
    public MainFrameMapping(final TargetClient client) {
        this.client = client;
    }

    @FXML
    private void initialize() {
        tagCombo.setItems(FXCollections.observableArrayList(Tag.values()));
    }

    @FXML
    public void infoButtonAction() {
        musicInformationArea.setText(fromInfo(client.getCurrentMusicInfo()));
    }

    @FXML
    public void nextButtonAction() {
        client.nextMusic();
        infoButtonAction();
    }

    @FXML
    public void playButtonAction() {
        client.play();
        infoButtonAction();
    }

    @FXML
    public void pauseButtonAction() {
        client.pause();
        infoButtonAction();
    }

    @FXML
    public void stopActionButton() {
        client.stop();
        infoButtonAction();
    }

    @FXML
    public void tagButtonAction() {
        client.addTag(tagCombo.getValue());
        infoButtonAction();
    }

    @FXML
    public void displayStatistic() throws IOException {
        new StatisticFrame();
    }

    private String fromInfo(final MpdMusicInformation information) {
        String res = information.getMusicInfo().getArtist() + " - " + information.getMusicInfo().getTitle();

        res += "\n[" + information.getPlayerStatus() + "]";
        res += " #" + information.getPlaylistPosition().getPosition() + "/" + information.getPlaylistPosition().getPlaylistSize();
        res += "  " + PlayerTiming.longToTime(information.getPlayerTiming().getElapsedTime()) + "/" + PlayerTiming.longToTime(information.getPlayerTiming().getTotalDuration());

        res += "\n" + information.getMusicInfo().getFilename();
        res += "\nMusic id: " + information.getMusicInfo().getMusicId();
        res += "\nTag: " + information.getTagList();
        res += "\nPlay count: " + information.getMusicStatistic().getPlayCount();

        return res;
    }
}
