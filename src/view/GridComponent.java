package view;

import util.ColorMap;

import javax.swing.*;
import java.awt.*;

public class GridComponent extends JComponent {
    private int row;
    private int col;
    private int number;
    static Font font = new Font("Serif", Font.BOLD, 42);

    public GridComponent(int row, int col, int gridSize) {
        this.setSize(gridSize, gridSize);
        this.row = row;
        this.col = col;
        this.number = 0;
    }

    public GridComponent(int row, int col, int number, int gridSize) {
        this.setSize(gridSize, gridSize);
        this.row = row;
        this.col = col;
        this.number = number;
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        if (number > 0) {
//            g.setColor(Color.white);
//            g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
//            g.setColor(ColorMap.getColor(number));
//            g.setFont(font);
//            FontMetrics metrics = g.getFontMetrics(g.getFont());
//            int textWidth = metrics.stringWidth(String.valueOf(number));
//            int x = 0;
//            int y = 0;

            //这下面是直接输出数字
//            g.drawString(String.valueOf(number), x, y);

//            //这下面是输出代表数字的图片
            ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\" + String.valueOf(number) + ".png");
            Image image = icon.getImage();
            g.drawImage(image,0,0,null);
//            g.clearRect(x,y,160,160);
        } else {
//            g.setColor(Color.LIGHT_GRAY);
//            g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
//            setOpaque(false);
        }
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {this.row = row;}

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
