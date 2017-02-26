package client;

import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by icule on 26/02/17.
 */
public class Main {
    private WebTarget target;
    private JTextArea textArea;

    private void updateMusicData(){
        String res = target.path("music").request().get(String.class);
        textArea.setText(res);
    }

    public Main(String url){
        Client c = ClientBuilder.newClient();
        target = c.target(url);


        JFrame frame = new JFrame("Mpd controller");

        frame.setLayout(new BorderLayout());

        JPanel center = new JPanel();
        textArea = new JTextArea(3, 40);
        center.add(textArea);
        frame.add(center, BorderLayout.CENTER);

        JPanel north = new JPanel();
        JButton info = new JButton("Info");
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateMusicData();
            }
        });
        north.add(info);
        
        JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.path("next").request().put(Entity.text(""), String.class);
                updateMusicData();
            }
        });
        north.add(next);

        JButton play = new JButton("Play");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.path("play").request().put(Entity.text(""), String.class);
                updateMusicData();
            }
        });
        north.add(play);

        JButton pause = new JButton("Pause");
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.path("pause").request().put(Entity.text(""), String.class);
                updateMusicData();
            }
        });
        north.add(pause);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.path("stop").request().put(Entity.text(""), String.class);
                updateMusicData();
            }
        });
        north.add(stop);

        frame.add(north, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String args[]){
        String authToken = "test";
        Main m = new Main("http://127.0.0.1:6061/player/" + authToken + "/");
    }
}
