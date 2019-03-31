package iculesgate.mpd_controller.client;

import com.google.inject.Inject;
import iculesgate.mpd_controller.data.TaggedMusicInfo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.function.Supplier;

public class StatisticViewMapping {
    @FXML
    private TableView<TaggedMusicInfo> statisticView;
    @FXML
    private TableColumn<TaggedMusicInfo, String> titleColumn;

    @FXML
    private TableColumn<TaggedMusicInfo, String> artistColumn;

    @FXML
    private TableColumn<TaggedMusicInfo, String> playCountColumn;

    @FXML
    private TableColumn<TaggedMusicInfo, String> playtimeColumn;

    @FXML
    private Label musicCountLabel;

    @FXML
    private Label totalPlaycountLabel;

    @FXML
    private Label totalPlaytimeLabel;

    private TargetClient targetClient;

    @Inject
    public StatisticViewMapping(final TargetClient targetClient) {
        this.targetClient = targetClient;
    }

    @FXML
    private void initialize() {
        List<TaggedMusicInfo> toDisplay = targetClient.getAllMusic();
        musicCountLabel.setText("" + toDisplay.size());
        totalPlaycountLabel.setText("" + toDisplay.stream().mapToInt(o -> o.getMusicStatistic().getPlayCount()).sum());
        totalPlaytimeLabel.setText(formatDuration(toDisplay.stream().mapToLong(this::getPlaytime).sum()));

        statisticView.setItems(FXCollections.observableList(toDisplay));
        titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getIfNotNull(cellData,
                                                                                           () -> cellData.getValue().getMusicInfo().getTitle())));

        artistColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getArtist(cellData)));
        playCountColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("" + cellData.getValue().getMusicStatistic().getPlayCount()));
        playtimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(formatDuration(getPlaytime(cellData.getValue()))));
    }

    private String getArtist(final TableColumn.CellDataFeatures<TaggedMusicInfo, String> cellData) {
        if (cellData.getValue().getMusicInfo() != null) {
            if (cellData.getValue().getMusicInfo().getArtist() != null) {
                if (cellData.getValue().getMusicInfo().getArtist().contains("中川幸太郎")) {//There is a unicode caracter that make crash javafx here
                    return "";
                }
                return cellData.getValue().getMusicInfo().getArtist();
            }
        }
        return "";
    }

    private String getIfNotNull(final TableColumn.CellDataFeatures<TaggedMusicInfo, String> cellData,
                                final Supplier<String> supplier) {
        if (cellData.getValue().getMusicInfo() == null) {
            return "";
        }
        else {
            return supplier.get();
        }
    }

    private long getPlaytime(final TaggedMusicInfo info) {
        return info.getMusicStatistic().getPlayCount() * info.getMusicInfo().getDuration();
    }

    private String formatDuration(long duration) {
        StringBuilder builder = new StringBuilder();
        if (duration >= 3600) {
            builder.append(String.format("%02d:", duration / 3600));
            duration = duration % 3600;
        }
        if (duration >= 60) {
            builder.append(String.format("%02d:", duration / 60));
            duration = duration % 60;
        }
        builder.append(String.format("%02d", duration));


        return builder.toString();
    }
}
