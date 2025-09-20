package view;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    public BackgroundPanel(String location){
        backgroundImage = new ImageIcon(location).getImage();
        setLayout(new BorderLayout());
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
    }
}
