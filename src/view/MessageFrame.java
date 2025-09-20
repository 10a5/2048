package view;

import javax.swing.*;
import java.awt.*;

public class MessageFrame extends JFrame {
    private JButton ConfirmBtn;
    public MessageFrame(int width, int height) {
        BackgroundPanel backgroundPanel = new BackgroundPanel(".\\src\\resourses\\frame\\MessageBackground.png");
        setContentPane(backgroundPanel);
        setComponentZOrder(backgroundPanel, 0);
        this.ConfirmBtn = createButton("确定", new Point(350, 50), 100, 50);
    }

    private JButton createButton(String name, Point location, int width, int height) {
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
