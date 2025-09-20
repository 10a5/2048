package view;

import java.io.*;
import javax.sound.sampled.*;

public class WavPlayer {
    private volatile boolean playing = false;
    private Thread audioThread;
    private String filePath;

    public void play(String filePath) {
        if (playing) {
            System.out.println("音乐已经在播放中");
            return;
        }

        this.filePath = filePath;
        playing = true;
        audioThread = new Thread(() -> {
            while (playing) {
                try {
                    playOnce(filePath);
                    Thread.sleep(8000); // 等待8秒后再次播放
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        audioThread.start();
    }

    private void playOnce(String filePath) {
        try {
            File file = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                line.write(buffer, 0, bytesRead);
            }
            line.drain();
            line.close();
            audioInputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        playing = false;
        if (audioThread != null) {
            try {
                audioThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        WavPlayer player = new WavPlayer();
//        String desktopPath = System.getProperty("user.home") + "/Desktop/";
//        String filePath = desktopPath + "your_file.wav";
//        player.play(filePath); // 播放 WAV 文件
//
//        // 主线程结束前等待一段时间，以确保音乐播放
//        try {
//            Thread.sleep(30000); // 等待30秒后停止播放
//            player.stop(); // 停止音乐
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
