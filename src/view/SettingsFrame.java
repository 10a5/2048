package view;

import model.ServerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class SettingsFrame extends JFrame {
    private view.SettingsController settingsController;
    private JButton restartBtn , backBtn , saveBtn , menuBtn;
    private ServerConnection server;
    public SettingsFrame(int width, int height , view.SettingsController settingsController, ServerConnection server){
        BackgroundPanel backgroundPanel = new BackgroundPanel(".\\src\\resourses\\frame\\settingBackground.jpg");
        setContentPane(backgroundPanel);
        setComponentZOrder(backgroundPanel,0);
        this.server = server;
        this.setTitle("设置");
        this.setLayout(null);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.settingsController = settingsController;
        this.restartBtn = createButton("Restart", new Point(350, 50), 100, 50);
        this.backBtn = createButton("Back", new Point(350, 120), 100, 50);
        this.menuBtn = createButton("Menu", new Point(350, 190), 100, 50);
        this.saveBtn = createButton("Save", new Point(350, 260), 100, 50);

        this.restartBtn.addActionListener(e -> {
            settingsController.restartGame();
            this.dispose();
        });
        this.menuBtn.addActionListener(e -> {
            settingsController.save();
            try {
                settingsController.backToMain();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
        });
        this.backBtn.addActionListener(e -> {
            settingsController.backToGame();
            this.dispose();
        });
        this.saveBtn.addActionListener(e -> {
            settingsController.save();
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                settingsController.backToGame();
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
}
