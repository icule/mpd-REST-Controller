package iculesgate.mpd_controller.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StatisticFrame {
    public StatisticFrame() throws IOException {
        FXMLLoader loader = FXMLLoaderFactory.getLoader();
        loader.setLocation(getClass().getResource("/iculesgate/mpd_controller/client/StatisticFrame.fxml"));

        BorderPane borderPane = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(borderPane));

        stage.show();
    }
}
