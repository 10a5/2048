package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import controller.MainController;
import model.ServerConnection;

public class duoFrame extends JFrame {
    private MainController mainController;
    private JButton quitButton;
    private String currentUser;
    private JLabel userLabel , scoreLabel;
    public duoFrame(MainFrame mainFrame,MainController mainController,String currentUser){
        BackgroundPanel backgroundPanel = new BackgroundPanel(".\\src\\resourses\\frame\\settingBackground.jpg");
        setContentPane(backgroundPanel);
        setComponentZOrder(backgroundPanel,0);
        this.setTitle("连接中");
        this.setLayout(null);
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setResizable(false);
        this.currentUser = currentUser;
        this.userLabel = createLabel("匹配中",new Font("serif", Font.ITALIC, 44), new Point(150, 120), 300, 50);
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setForeground(Color.YELLOW);
//        this.scoreLabel = createLabel(score,new Font("serif", Font.ITALIC, 22), new Point(150, 100), 100, 50);
        this.mainController = mainController;
        this.quitButton = createButton("取消", new Point(250, 200), 100, 50);
        quitButton.setVisible(false);


        this.quitButton.addActionListener(e -> {
            MainFrame newMainFrame = new MainFrame(600,400,mainController,currentUser);
            newMainFrame.setVisible(true);
            mainFrame.stopServer();
            mainFrame.dispose();
            this.dispose();
        });
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                quitButton.setVisible(true);
            }
        };
        timer.schedule(task,600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                if(server.getConnection()){server.sendMessage("Exit");}
                System.exit(0);
            }
        });
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
}
