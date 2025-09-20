package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class KirakiraAnimation extends JPanel implements ActionListener {
    private ArrayList<ImageInfo> imageInfos = new ArrayList<>();
    private Random random = new Random();
    private Timer timer;
    private GameFrame frame;

    public KirakiraAnimation(int x, int y, GameFrame frame) {
        imageInfos.clear();
        this.setOpaque(false);
        this.setBackground(null);
        this.frame = frame;
        ImageInfo[] images = new ImageInfo[10];
        for(int i = 0 ; i <= 9 ; i++){
            int colorTag = random.nextInt(10), xTag = random.nextInt(2) == 0 ? random.nextInt(20) : -random.nextInt(20), yTag = random.nextInt(2) == 0 ? random.nextInt(20) : -random.nextInt(20);
            images[i] = new ImageInfo(".\\src\\resourses\\kirakira\\" + colorTag + ".jpg" , x, y, xTag, yTag);
        }
        for (int i = 0; i < 5; i++) {
            imageInfos.add(images[random.nextInt(10)]);
        }
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ImageInfo imageInfo : imageInfos) {
            if (imageInfo.isVisible()) {
                g.drawImage(imageInfo.getImage(), imageInfo.getX(), imageInfo.getY(), this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<ImageInfo> a = new ArrayList<>();
        if (imageInfos.isEmpty()) {
            timer.stop();
            frame.remove(this);
        }
        for (ImageInfo imageInfo : imageInfos) {
            if (!imageInfo.updatePosition(getWidth(), getHeight())) {
                a.add(imageInfo);
            }
            imageInfo.addDeltaY(5);
        }
        for (ImageInfo imageInfo : a) {
            imageInfos.remove(imageInfo);
        }
        repaint();
    }
}

//class ImageInfo {
//    private Image image;
//    private int x;
//    private int y;
//    private int deltaX;
//    private int deltaY;
//    private boolean visible = true;
//
//    ImageInfo(String imagePath, int x, int y, int deltaX, int deltaY) {
//        ImageIcon icon = new ImageIcon(imagePath);
//        image = icon.getImage();
//        this.x = x;
//        this.y = y;
//        this.deltaX = deltaX;
//        this.deltaY = deltaY;
//    }
//
//    public Image getImage() {
//        return image;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public int getDeltaY() {
//        return deltaY;
//    }
//
//    public void addDeltaY(int i) {
//        deltaY += i;
//    }
//
//    public boolean isVisible() {
//        return visible;
//    }
//
//    public void setVisible(boolean visible) {
//        this.visible = visible;
//    }
//
//    public boolean updatePosition(int panelWidth, int panelHeight) {
//        x += deltaX;
//        y += deltaY;
//        if (x <= 0 || x >= panelWidth - image.getWidth(null) ) {
//            visible = false;
//            image.flush();
//            return false;
//        }
//        if (y <= 0 || y >= panelHeight - image.getHeight(null) ) {
//            visible = false;
//            image.flush();
//            return false;
//        }
//        return true;
//    }
//}
