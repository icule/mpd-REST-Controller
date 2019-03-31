package iculesgate.mpd_controller.client;

import com.google.inject.Inject;
import iculesgate.mpd_controller.data.TaggedMusicInfo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

    private TargetClient targetClient;

    @Inject
    public StatisticViewMapping(final TargetClient targetClient) {
        this.targetClient = targetClient;
    }

    @FXML
    private void initialize() {
        List<TaggedMusicInfo> toDisplay = targetClient.getAllMusic();
        statisticView.setItems(FXCollections.observableList(toDisplay));
        titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getIfNotNull(cellData,
                                                                                           () -> cellData.getValue().getMusicInfo().getTitle())));

        artistColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(getArtist(cellData)));
        playCountColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("" + cellData.getValue().getMusicStatistic().getPlayCount()));
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
}
