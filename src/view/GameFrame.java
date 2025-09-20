package view;

import controller.GameController;
import controller.LoginController;
import controller.MainController;
import model.ServerConnection;
import util.ColorMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame {

    private GameController controller;
    private KirakiraAnimation kirakiraAnimation;
    private JButton loadBtn;
    private JButton regretBtn;
    private JButton settingsBtn;

    private JButton catBtn;
    private JButton AIBtn;
    private JButton upBtn,downBtn,rightBtn,leftBtn;
//    private JButton ScoreBtn;
    private view.SettingsController settingsController;

    private JLabel stepLabel , timeLabel , walkLabel;
    private GamePanel gamePanel;
    private String currentUser;
    private ServerConnection server;
    private int time,egg;
    private MainController mainController;

    public GameFrame(int width, int height , String currentUser, MainController mainController) {
        BackgroundPanel backgroundPanel = new BackgroundPanel(".\\src\\resourses\\frame\\gameBackground.png");
        setContentPane(backgroundPanel);
        setComponentZOrder(backgroundPanel,0);
        this.setTitle("2048");
        this.setLayout(null);
        this.setSize(width, height);
        setResizable(false);
//        ColorMap.InitialColorMap();
        this.mainController = mainController;
        this.currentUser = currentUser;
        gamePanel = new GamePanel((int) (this.getHeight() * 0.8),this);
        gamePanel.setLocation(this.getHeight() / 30 + 50, this.getWidth() / 30 + 25);
        this.add(gamePanel);
        this.egg = 0;
//        setComponentZOrder(gamePanel,0);

        this.controller = new GameController(gamePanel, gamePanel.getModel(),currentUser,mainController);
//        this.loadBtn = createButton("Load", new Point(850, 220), 20, 10);
        this.regretBtn = createRegretButton("", new Point(870,180), 80 , 80);
        this.settingsBtn = createSettingButton("" , new Point(830,80), 160 , 80);
        this.AIBtn = createAIButton("" , new Point(870,260), 80 , 100);
        this.catBtn = createCatButton("" , new Point(776,441), 220 , 305);
        this.leftBtn = createLeftButton("" , new Point(5,340), 20 , 80);
        this.rightBtn = createRightButton("" , new Point(760,340), 20 , 80);
        this.upBtn = createUpButton("" , new Point(360,7), 80 , 20);
        this.downBtn = createDownButton("" , new Point(360,730), 80 , 20);
        catBtn.setVisible(true);
        leftBtn.setVisible(true);
        rightBtn.setVisible(true);
        upBtn.setVisible(true);
        downBtn.setVisible(true);
//        this.ScoreBtn = createButton("Score" , new Point(850,420), 110 , 50);
        this.stepLabel = createLabel("按下方向键移动吧！", new Font("serif", Font.ITALIC, 14), new Point(850, 380), 180, 50);
        this.timeLabel = createLabel("", new Font("serif", Font.ITALIC, 14), new Point(870, 410), 180, 50);
        this.walkLabel = createLabel("", new Font("serif", Font.ITALIC, 16), new Point(870, 350), 180, 50);
        stepLabel.setForeground(Color.YELLOW);
        gamePanel.setStepLabel(stepLabel);
        walkLabel.setForeground(Color.YELLOW);
        this.add(walkLabel);
        gamePanel.setWalkLabel(walkLabel);
        timeLabel.setForeground(Color.YELLOW);
        this.add(timeLabel);
        timeLabel.setVisible(true);

//        this.loadBtn.addActionListener(e -> {
//            String string = JOptionPane.showInputDialog(this, "Input path:");
//            System.out.println(string);
//            gamePanel.requestFocusInWindow();//enable key listener
//        });
        this.regretBtn.addActionListener(e -> {
            gamePanel.regret();
            gamePanel.requestFocusInWindow();
        });
        this.rightBtn.addActionListener(e -> {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_RIGHT);
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
            gamePanel.requestFocusInWindow();
        });
        this.leftBtn.addActionListener(e -> {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_LEFT);
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
            gamePanel.requestFocusInWindow();
        });
        this.upBtn.addActionListener(e -> {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_UP);
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
            gamePanel.requestFocusInWindow();
        });
        this.downBtn.addActionListener(e -> {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_DOWN);
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
            gamePanel.requestFocusInWindow();
        });
        this.catBtn.addActionListener(e -> {
            egg++;
            if(egg > 13){
                Timer sleeper = new Timer();
                TimerTask AITask = new TimerTask() {
                    @Override
                    public void run() {
                        int dir = gamePanel.getModel().AIGetDirection();
                        try {
                            Robot robot = new Robot();
                            switch (dir){
                                case 0:
                                    robot.keyPress(KeyEvent.VK_RIGHT);
                                    break;
                                case 1:
                                    robot.keyPress(KeyEvent.VK_LEFT);
                                    break;
                                case 2:
                                    robot.keyPress(KeyEvent.VK_UP);
                                    break;
                                case 3:
                                    robot.keyPress(KeyEvent.VK_DOWN);
                                    break;
                            }
                            boolean isFull = true;
                            for(int i = 0 ; i < 4 ; i++){
                                for (int j = 0 ; j < 4 ; j++){
                                    if(gamePanel.getModel().getNumber(i,j) == 0){
                                        isFull = false;
                                    }
                                    if (gamePanel.getModel().getNumber(i,j) == 2048){
                                        isFull = true;
                                    }
                                }
                            }
                            for(int i = 0 ; i < 3 ; i++){
                                for (int j = 0 ; j < 4 ; j++){
                                    if(gamePanel.getModel().getNumber(i,j) == gamePanel.getModel().getNumber(i+1,j)){
                                        isFull = false;
                                    }
                                }
                            }
                            for(int i = 0 ; i < 4 ; i++){
                                for (int j = 0 ; j < 3 ; j++){
                                    if(gamePanel.getModel().getNumber(i,j) == gamePanel.getModel().getNumber(i,j+1)){
                                        isFull = false;
                                    }
                                }
                            }
                            if(isFull){
                                sleeper.cancel();
                            }
                        } catch (AWTException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                };
                sleeper.scheduleAtFixedRate(AITask,200,200);
            }
            gamePanel.requestFocusInWindow();
        });
        this.settingsBtn.addActionListener(e ->{
            settingsController = new view.SettingsController(this,server);
            settingsController.newSettings();
            this.setVisible(false);
        });
//        this.ScoreBtn.addActionListener(e ->{
//            System.out.println(gamePanel.getModel().Score());
//            gamePanel.requestFocusInWindow();
//        });
        this.AIBtn.addActionListener(e -> {
            int dir = gamePanel.getModel().AIGetDirection();
            try {
                Robot robot = new Robot();
                switch (dir){
                    case 0:
                        robot.keyPress(KeyEvent.VK_RIGHT);
                        break;
                    case 1:
                        robot.keyPress(KeyEvent.VK_LEFT);
                        break;
                    case 2:
                        robot.keyPress(KeyEvent.VK_UP);
                        break;
                    case 3:
                        robot.keyPress(KeyEvent.VK_DOWN);
                        break;
                }
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }

            gamePanel.requestFocusInWindow();
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                save();
//                if(server.getConnection()){server.sendMessage("Exit");}
                System.exit(0);
            }
        });
        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gamePanel.requestFocusInWindow();
        walkLabel.setVisible(false);
    }
    public void backToGame(){
        this.setVisible(true);
        gamePanel.requestFocusInWindow();
    }

    public void restartGame(){
        GameFrame gameFrame = new GameFrame(1000,800,currentUser,mainController);
        gameFrame.setVisible(true);
        gameFrame.getGamePanel().requestFocusInWindow();//enable key listener
        this.dispose();
    }

    private JButton createButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);

        button.setIcon(new ImageIcon(".\\src\\resourses\\numbers\\2.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\numbers\\4.png"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\numbers\\8.png"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }
    private JButton createAIButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\ai1.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\ai3.png"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\ai2.png"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }
    private JButton createCatButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\cat1.jpg"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\cat2.jpg"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\cat3.jpg"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }
    private JButton createSettingButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\menu1.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\menu2.png"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\menu3.png"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }

    private JButton createRegretButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\regret1.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\regret3.png"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\regret2.png"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }
    private JButton createUpButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\up.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\up.png"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\up.png"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }
    private JButton createDownButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\down.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\down.png"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\down.png"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }
    private JButton createLeftButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\left.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\left.png"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\left.png"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }
    private JButton createRightButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\right.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\right.png"));
        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\right.png"));

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
    public void startKirakiraAnimation(int x , int y){
//        SwingUtilities.invokeLater(() -> {
//            kirakiraAnimation = new KirakiraAnimation(x,y,this);
//            kirakiraAnimation.setSize(640,640);
//            kirakiraAnimation.setLocation(this.getHeight() / 15, this.getWidth() / 15);
//            this.add(kirakiraAnimation);
//            setComponentZOrder(kirakiraAnimation,0);
//        });
    }
    public void startTicking(){
        time = 0;
        Timer timerTime = new Timer();
        TimerTask add1 = new TimerTask() {
            @Override
            public void run() {
                time++;
                if(egg > 0){
                    egg--;
                }
                timeLabel.setText("Time:" + time/3600 + ":" + (time / 60) % 60 + ":" + time % 60);
            }
        };
        timerTime.scheduleAtFixedRate(add1,1000,1000);
    }
    public void save(){
        if(currentUser.equals("游客")){
            return;
        }
        ArrayList<String> file = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(".\\src\\Users\\" + currentUser + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                file.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
        }
        file.set(2,"积分：" + gamePanel.getScore());
        file.set(3,"时间：" + time);
        for(int i = 4 ; i < 20 ; i++ ){
            file.set(i,String.valueOf(gamePanel.getModel().getNumber((i-4) % 4,(i-4) / 4)));
        }
        file.set(20,"步数：" + gamePanel.walk);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\Users\\" + currentUser + ".txt"))) {
            for (String line : file) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cleanUp(){
        if(currentUser.equals("游客")){
            return;
        }
        ArrayList<String> file = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(".\\src\\Users\\" + currentUser + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                file.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.set(2,"积分：0");
        file.set(3,"时间：0");
        for(int i = 4 ; i < 20 ; i++ ){
            file.set(i,"0");
        }
        file.set(20,"步数：0");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\Users\\" + currentUser + ".txt"))) {
            for (String line : file) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToMain() throws IOException {
        mainController.appear();
        this.dispose();
    }
    public GamePanel getGamePanel(){
        return gamePanel;
    }
    public void set(int[] panel , int time , int score,int walk){
        this.gamePanel.setScore(score);
        this.gamePanel.getModel().setNumbers(panel);
        this.gamePanel.updateGridsNumber(4);
        startTicking();
        gamePanel.startTicking = true;
        this.time = time;
        this.gamePanel.walk = walk;
    }
    public void walkIt(){
        walkLabel.setVisible(true);
    }
    public void lose(){
        JButton loseBtn = createLoseBtn("",new Point(150,250),500,300);
        loseBtn.setVisible(true);
        this.setComponentZOrder(loseBtn, 0);
        cleanUp();
        loseBtn.addActionListener(e ->{
            restartGame();
        });
        this.requestFocusInWindow();
    }
    public void win(){
        JButton winBtn = createWinBtn("",new Point(150,250),500,300);
        winBtn.setVisible(true);
        this.setComponentZOrder(winBtn, 0);
        cleanUp();
        winBtn.addActionListener(e ->{
            restartGame();
        });
        this.requestFocusInWindow();
    }
    private JButton createLoseBtn(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\lose.png"));
//        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\right.png"));
//        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\right.png"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
    }
    private JButton createWinBtn(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\win.png"));
//        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\right.png"));
//        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\right.png"));

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
