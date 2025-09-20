package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

import controller.MainController;
import model.ServerConnection;

public class MainFrame extends JFrame {
    private MainController mainController;
    private JButton startButton, duoBtn;
    private String currentUser;
    private JLabel userLabel, scoreLabel;
    private Thread serverConnectionThread;
    private ServerConnection serverConnection;

    public MainFrame(int width, int height, MainController mainController, String currentUser) {
        BackgroundPanel backgroundPanel = new BackgroundPanel(".\\src\\resourses\\frame\\mainBackground.jpg");
        setContentPane(backgroundPanel);
        setComponentZOrder(backgroundPanel, 0);
        this.setTitle("主菜单");
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setResizable(false);
        this.currentUser = currentUser;
        this.userLabel = createLabel(currentUser, new Font("serif", Font.ITALIC, 24), new Point(0, 120), 300, 50);
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setForeground(Color.YELLOW);
        String score = getScore(".\\src\\Users\\" + currentUser + ".txt");
        int Time,Score,Walk;
        try{
            Score = Integer.parseInt(score);
        }catch (NumberFormatException e){
            Score = 0;
            System.out.println("文件损坏！");
        }
        String time = getTime(".\\src\\Users\\" + currentUser + ".txt");
        try{
            Time = Integer.parseInt(time);
        }catch (NumberFormatException e){
            Time = 0;
            System.out.println("文件损坏！");
        }
        String walk = getWalk(".\\src\\Users\\" + currentUser + ".txt");
        try{
            Walk = Integer.parseInt(walk);
        }catch (NumberFormatException e){
            Walk = 0;
            System.out.println("文件损坏！");
        }
//        this.scoreLabel = createLabel(score,new Font("serif", Font.ITALIC, 22), new Point(150, 100), 100, 50);
        this.mainController = mainController;
        this.startButton = createButton("单机模式", new Point(385, 90), 100, 50);
        this.duoBtn = createButton("多人模式", new Point(385, 160), 100, 50);

        int finalTime = Time;
        int finalScore = Score;
        int finalWalk = Walk;
        this.startButton.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(1000, 800, currentUser, mainController);
            if(finalTime > 0){
                int[] panel = new int[16];
                panel = getPanel(".\\src\\Users\\" + currentUser + ".txt");
                gameFrame.set(panel, finalTime, finalScore, finalWalk);
            }
            gameFrame.setVisible(true);
            gameFrame.getGamePanel().requestFocusInWindow();
            this.dispose();
        });

        this.duoBtn.addActionListener(e -> {
            duoFrame DuoFrame = new duoFrame(this, mainController, currentUser);
            this.setVisible(false);

            // Start the server connection in a new thread
            serverConnection = new ServerConnection(this, currentUser , DuoFrame);
            serverConnectionThread = new Thread(serverConnection);
            serverConnectionThread.start();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (serverConnection != null) {
                    serverConnection.stop();
                    try {
                        serverConnectionThread.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                System.exit(0);
            }
        });
    }
    public void stopServer(){
        serverConnection.stop();
    }

    private JButton createButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        button.setFont(new Font("serif", Font.ITALIC, 14)); // 设置字体和大小
        button.setForeground(Color.WHITE); // 设置文字颜色

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\btn1.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\btn2.jpg"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\btn3.jpg"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }

    private JLabel createLabel(String name, Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setLocation(location);
        label.setSize(width, height);
        this.add(label);
        return label;
    }

    public String getScore(String path) {
        File userFile = new File(path);
        ArrayList<String> file = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                file.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (file.size() >= 3) {
            String line = file.get(2), score;
            if (line.contains("：")) {
                score = line.substring(line.indexOf("：") + 1);
            } else {
                JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
                return "0";
            }
            return score;
        } else {
            JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
            return "0";
        }
    }
    public String getTime(String path) {
        File userFile = new File(path);
        ArrayList<String> file = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                file.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (file.size() >= 4) {
            String line = file.get(3), time;
            if (line.contains("：")) {
                time = line.substring(line.indexOf("：") + 1);
            } else {
                JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
                return "0";
            }
            return time;
        } else {
            JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
            return "0";
        }
    }
    public MainController getMainController(){
        return mainController;
    }
    public int[] getPanel(String path) {
        int[] panel = new int[16];
        File userFile = new File(path);
        ArrayList<String> file = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                file.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (file.size() >= 19) {
            for (int i = 4 ; i < 20 ; i++){
                String line = file.get(i);
                try{
                    int num = Integer.parseInt(line);
                    panel[i-4] = num;
                }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
                    int[] empty = new int[16];
                    for(int j = 0 ; j < 16 ; j++){
                        empty[j] = 0;
                    }
                    return empty;
                }
            }
            return panel;
        } else {
            JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
            int[] empty = new int[16];
            for(int i = 0 ; i < 16 ; i++){
                empty[i] = 0;
            }
            return empty;
        }
    }
    public String getWalk(String path) {
        File userFile = new File(path);
        ArrayList<String> file = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                file.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (file.size() >= 21) {
            String line = file.get(20), walk;
            if (line.contains("：")) {
                walk = line.substring(line.indexOf("：") + 1);
            } else {
                JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
                return "0";
            }
            return walk;
        } else {
            JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
            return "0";
        }
    }
}
