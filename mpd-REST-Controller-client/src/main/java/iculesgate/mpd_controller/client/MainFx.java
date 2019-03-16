package iculesgate.mpd_controller.client;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import iculesgate.mpd_controller.configuration.ConfigurationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {
    private static class ClientModule extends AbstractModule {
        private final ConfigurationManager configurationManager;
        private final MainFx mainFx;

        public ClientModule(final ConfigurationManager configurationManager,
                            final MainFx mainFx) {
            this.configurationManager = configurationManager;
            this.mainFx = mainFx;
        }

        @Override
        protected void configure() {
            bind(MainFx.class).toInstance(mainFx);
            bind(ConfigurationManager.class).toInstance(configurationManager);
            bind(TargetClient.class);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(getParameters().getRaw().get(0));
        Injector injector = Guice.createInjector(new ClientModule(configurationManager, this));
        FXMLLoaderFactory.parametrize(injector);

        FXMLLoader loader = FXMLLoaderFactory.getLoader();
        loader.setLocation(getClass().getResource("/iculesgate/mpd_controller/client/MainFrame.fxml"));

        BorderPane borderPane = loader.load();

        primaryStage.setScene(new Scene(borderPane));

        primaryStage.show();
    }
}
