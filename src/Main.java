import model.ServerConnection;
import view.LoginFrame;
import view.WavPlayer;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        ServerConnection server = new ServerConnection();
//        server = null;
        SwingUtilities.invokeLater(() -> {
            try {
                LoginFrame loginFrame = new LoginFrame(600,400 );
                WavPlayer player = new WavPlayer();
                String filePath = ".\\src\\resourses\\BGM\\bgm.wav";
                player.play(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
