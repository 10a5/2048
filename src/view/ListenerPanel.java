package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is to enable key events.
 *
 */
public abstract class ListenerPanel extends JPanel {
    private Timer timer;
    private boolean isCooldown;
    public ListenerPanel() {
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        this.setFocusable(true);
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("冷却结束");
                isCooldown = false;
            }
        });
        timer.setRepeats(false);
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getID() == KeyEvent.KEY_PRESSED && !isCooldown) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> {
                    doMoveRight();
                    isCooldown = true;
                    timer.restart();
                }
                case KeyEvent.VK_LEFT -> {
                    doMoveLeft();
                    isCooldown = true;
                    timer.restart();
                }
                case KeyEvent.VK_UP -> {
                    doMoveUp();
                    isCooldown = true;
                    timer.restart();
                }
                case KeyEvent.VK_DOWN -> {
                    doMoveDown();
                    isCooldown = true;
                    timer.restart();
                }
                //todo: complete other move event
            }
        }
    }
    public void delay(KeyEvent e){
        if(e.getID() == KeyEvent.KEY_PRESSED){
            timer.stop();
            timer.start();
        }
    }

    public abstract void doMoveRight();
    public abstract void doMoveLeft();
    public abstract void doMoveUp();
    public abstract void doMoveDown();
}
