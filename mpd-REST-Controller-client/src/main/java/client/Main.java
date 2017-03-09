package client;

import common.ConfigurationManager;

import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by icule on 26/02/17.
 */
public class Main {
    private WebTarget target;
    private JPanel panel1;
    private JTextArea dataArea;
    private JButton infoButton;
    private JButton nextButton;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;

    private void updateMusicData(){
        String res = target.path("music").request().get(String.class);
        dataArea.setText(res);
    }

    public Main(ConfigurationManager configurationManager){
        Client c = ClientBuilder.newClient();
        target = c.target("http://" + configurationManager.getCompleteUrl() + "/player/" + configurationManager.getAuthToken());


        JFrame frame = new JFrame("Mpd controller");

        infoButton.addActionListener((actionEvent) -> updateMusicData() );

        nextButton.addActionListener((actionEvent) -> {
                target.path("next").request().put(Entity.text(""), String.class);
                updateMusicData();
        });

        playButton.addActionListener((actionEvent) -> {
                target.path("play").request().put(Entity.text(""), String.class);
                updateMusicData();
        });

        pauseButton.addActionListener((actionEvent) -> {
                target.path("pause").request().put(Entity.text(""), String.class);
                updateMusicData();
        });

        stopButton.addActionListener((actionEvent) -> {
                target.path("stop").request().put(Entity.text(""), String.class);
                updateMusicData();
        });

        frame.add(panel1);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String args[]) throws IOException {
        String authToken = "test";
        ConfigurationManager configurationManager = new ConfigurationManager("config.properties");
        Main m = new Main(configurationManager);
    }
}
