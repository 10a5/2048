package view;

import javax.swing.*;
import java.awt.*;

public class ImageInfo {
    private Image image;
    private int x;
    private int y;
    private int deltaX;
    private int deltaY;
    private int finalX;
    private int finalY;
    private boolean visible = true;

    ImageInfo(String imagePath, int x, int y, int finalX, int finalY) {
        ImageIcon icon = new ImageIcon(imagePath);
        image = icon.getImage();
        this.deltaX = (finalX - x) / 10;
        this.deltaY = (finalY - y) / 10;
        this.finalX = finalX;
        this.finalY = finalY;
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public void addDeltaY(int i) {
        deltaY += i;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean updatePosition(int panelWidth, int panelHeight) {
        x += deltaX;
        y += deltaY;
        if (x == finalX && y == finalY) {
            visible = false;
            image.flush();
            return false;
        }
        return true;
    }
}
