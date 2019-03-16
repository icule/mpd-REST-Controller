package iculesgate.mpd_controller.client;

import iculesgate.mpd_controller.data.Tag;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import javax.inject.Inject;

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
        musicInformationArea.setText(client.getCurrentMusicInfo());
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
}
