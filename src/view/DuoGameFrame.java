//0是出题人，1是做题人!!!!!!!!!
package view;

import controller.GameController;
import controller.MainController;
import model.ServerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import static view.GamePanelDuo.random;

public class DuoGameFrame extends GameFrame {

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

    private JLabel stepLabel , timeLabel;
    private GamePanelDuo gamePanel;
    private String currentUser;
    private ServerConnection server;
    private JButton[] grid;
    private int time,egg,role;
    private MainController mainController;

    public DuoGameFrame(int width, int height , String currentUser, MainController mainController , int role) {
        super(width,height,currentUser,mainController);
        this.role = role;
        BackgroundPanel backgroundPanel = new BackgroundPanel(".\\src\\resourses\\frame\\gameBackground.png");
        setContentPane(backgroundPanel);
        setComponentZOrder(backgroundPanel,0);
        this.setTitle("2048");
        this.setLayout(null);
        this.setSize(width, height);
//        ColorMap.InitialColorMap();
        this.mainController = mainController;
        this.currentUser = currentUser;
        gamePanel = new GamePanelDuo((int) (this.getHeight() * 0.8),this);
        gamePanel.setLocation(this.getHeight() / 30 + 50, this.getWidth() / 30 + 25);
        this.add(gamePanel);
        this.egg = 0;
//        setComponentZOrder(gamePanel,0);

//        this.controller = new GameController(gamePanel, gamePanel.getModel(),currentUser,mainController);
//        this.loadBtn = createButton("Load", new Point(850, 220), 20, 10);
//        this.regretBtn = createRegretButton("", new Point(870,180), 80 , 80);
        this.settingsBtn = createSettingButton("" , new Point(830,80), 160 , 80);
//        this.AIBtn = createAIButton("" , new Point(870,280), 80 , 100);
        this.catBtn = createCatButton("" , new Point(776,441), 220 , 305);
        catBtn.setVisible(true);
        if(role == 1){
            this.leftBtn = createLeftButton("" , new Point(5,340), 20 , 80);
            this.rightBtn = createRightButton("" , new Point(760,340), 20 , 80);
            this.upBtn = createUpButton("" , new Point(360,7), 80 , 20);
            this.downBtn = createDownButton("" , new Point(360,730), 80 , 20);
            leftBtn.setVisible(true);
            rightBtn.setVisible(true);
            upBtn.setVisible(true);
            downBtn.setVisible(true);;

        }

//        this.ScoreBtn = createButton("Score" , new Point(850,420), 110 , 50);
        this.stepLabel = createLabel("按下方向键移动吧！", new Font("serif", Font.ITALIC, 14), new Point(850, 380), 180, 50);
        if(role == 0){
            stepLabel.setText("点击方块放置数字！");
        }
        //        this.timeLabel = createLabel("", new Font("serif", Font.ITALIC, 14), new Point(870, 410), 180, 50);
        stepLabel.setForeground(Color.YELLOW);
        gamePanel.setStepLabel(stepLabel);
        this.timeLabel = createLabel("", new Font("serif", Font.ITALIC, 14), new Point(850, 380), 180, 50);
        timeLabel.setForeground(Color.YELLOW);
        this.add(timeLabel);
        timeLabel.setVisible(true);

//        this.loadBtn.addActionListener(e -> {
//            String string = JOptionPane.showInputDialog(this, "Input path:");
//            System.out.println(string);
//            gamePanel.requestFocusInWindow();//enable key listener
//        });
//        this.regretBtn.addActionListener(e -> {
//            gamePanel.regret();
//            gamePanel.requestFocusInWindow();
//        });

        this.catBtn.addActionListener(e -> {
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
                                        isFull = false;
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
            if(role == 1){gamePanel.requestFocusInWindow();}
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
//        this.AIBtn.addActionListener(e -> {
//            int dir = gamePanel.getModel().AIGetDirection();
//            try {
//                Robot robot = new Robot();
//                switch (dir){
//                    case 0:
//                        robot.keyPress(KeyEvent.VK_RIGHT);
//                        break;
//                    case 1:
//                        robot.keyPress(KeyEvent.VK_LEFT);
//                        break;
//                    case 2:
//                        robot.keyPress(KeyEvent.VK_UP);
//                        break;
//                    case 3:
//                        robot.keyPress(KeyEvent.VK_DOWN);
//                        break;
//                }
//            } catch (AWTException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            gamePanel.requestFocusInWindow();
//        });
        if(role == 0){

        }
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
        if(role == 0){
            for (int i = 0 ; i < 4 ; i++){
                for (int j = 0 ; j < 4 ; j++){
                    gamePanel.getModel().getNumber(i,j);
                }
            }

        }
    }
    public void backToGame(){
        this.setVisible(true);
        gamePanel.requestFocusInWindow();
    }

    public void restartGame(){
        GameFrame gameFrame = new GameFrame(1000,800,currentUser,mainController);
        gameFrame.setVisible(true);
        gamePanel.requestFocusInWindow();//enable key listener
        this.dispose();
    }

    private JButton createNumberButton(String name, Point location, int width, int height) {
//        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);

        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\air.png"));
        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\numberButton.png"));
//        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\numbers\\8.png"));

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        this.add(button);
        return button;
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
//    private JButton createAIButton(String name, Point location, int width, int height) {
////        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
//        JButton button = new JButton(name);
//        button.setLocation(location);
//        button.setSize(width, height);
//
//        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\ai1.png"));
//        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\ai3.png"));
//        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\ai2.png"));
//
//        button.setHorizontalTextPosition(SwingConstants.CENTER);
//        button.setVerticalTextPosition(SwingConstants.CENTER);
//
//        button.setBorderPainted(false);
//        button.setContentAreaFilled(false);
//        button.setFocusPainted(false);
//        button.setOpaque(false);
//
//        this.add(button);
//        return button;
//    }
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

//    private JButton createRegretButton(String name, Point location, int width, int height) {
////        ImageIcon icon = new ImageIcon(".\\src\\resourses\\numbers\\2.jpg");
//        JButton button = new JButton(name);
//        button.setLocation(location);
//        button.setSize(width, height);
//
//        button.setIcon(new ImageIcon(".\\src\\resourses\\icon\\regret1.png"));
//        button.setRolloverIcon(new ImageIcon(".\\src\\resourses\\icon\\regret3.png"));
//        button.setPressedIcon(new ImageIcon(".\\src\\resourses\\icon\\regret2.png"));
//
//        button.setHorizontalTextPosition(SwingConstants.CENTER);
//        button.setVerticalTextPosition(SwingConstants.CENTER);
//
//        button.setBorderPainted(false);
//        button.setContentAreaFilled(false);
//        button.setFocusPainted(false);
//        button.setOpaque(false);
//
//        this.add(button);
//        return button;
//    }
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
//        time = 0;
//        Timer timerTime = new Timer();
//        TimerTask add1 = new TimerTask() {
//            @Override
//            public void run() {
//                time++;
//                if(egg > 0){
//                    egg--;
//                }
//                timeLabel.setText("Time:" + time/3600 + ":" + (time / 60) % 60 + ":" + time % 60);
//            }
//        };
//        timerTime.scheduleAtFixedRate(add1,1000,1000);
    }
    public void save(){

    }
    public void backToMain() throws IOException {
        mainController.appear();
        this.dispose();
    }
    public int[] YourTurn(int[] situation){
        if(situation[1] == 7){
            System.out.println("10isa5");
            gamePanel.canMove = true;
            switch (situation[0]){
                case 0 -> gamePanel.doMoveRight();
                case 1 -> gamePanel.doMoveLeft();
                case 2 -> gamePanel.doMoveUp();
                case 3 -> gamePanel.doMoveDown();
            }
            gamePanel.canMove = false;
        }else{
            for(int i = 0 ; i < 4 ; i++){
                for (int j = 0 ; j < 4 ; j++){
                    gamePanel.getModel().setNumber(i,j,situation[i+4 * j]);
                }
            }
            gamePanel.updateGridsNumber(4);
        }

        if(role == 1){
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
            gamePanel.canMove = true;
            while(gamePanel.canMove){
                gamePanel.requestFocusInWindow();
            }
        }else {
            grid = new JButton[16];
            final boolean[] canMove = {true};
            gamePanel.canMove = false;
            for (int i = 0 ; i < 4 ; i++){
                for (int j = 0 ; j < 4 ; j++){
                    grid[i + 4 * j] = createNumberButton("",new Point(j*160 + 77, i*160 + 60),160,160);
                    if(gamePanel.getModel().getNumber(i,j) == 0){grid[i + 4 * j].setVisible(true);}
                    else {grid[i + 4 * j].setVisible(false);}
                    int temp = i + 4 * j;
                    this.grid[i + 4 * j].addActionListener(e -> {
                        if(canMove[0]){
                            System.out.println("按下方格" + temp);
                            gamePanel.getModel().setNumber(temp % 4 , temp / 4 , 2);
                            if(random.nextInt(6) == 0){
                                gamePanel.getModel().setNumber(temp % 4 , temp / 4 , 4);
                            }
                            gamePanel.updateGridsNumber(4);
                            canMove[0] = false;
                        }
                    });
                }
            }
            while (canMove[0]){
                gamePanel.requestFocusInWindow();
            }
            for (int i = 0 ; i < 16 ; i++){
                grid[i].setVisible(false);
            }
        }
        int[] result = new int[16];
        for(int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                result[i+4 * j] = gamePanel.getModel().getNumber(i,j);
            }
        }
        if(role == 1){
            result[0] = gamePanel.dir;
//            System.out.println("我的银行卡密码是" + result[0]);
        }
        return result;
    }
    public void setSame(int[] number){
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                gamePanel.getModel().setNumber(i,j,number[i + 4 * j]);
            }
        }

        for (int i = 0; i < 16; i++) {
            System.out.println(gamePanel.getModel().getNumber(i%4,i/4));
        }
        gamePanel.updateGridsNumber(4);
        gamePanel.draw();
    }
    public void initialNumber(){
        int[][] numbers = new int[4][4];
        for(int i = 0 ; i < numbers.length ; i++){
            for (int j = 0 ; j < numbers[i].length ; j++){
                numbers[i][j] = 0;
            }
        }
        int randomNum = random.nextInt(16);
        numbers[randomNum/4][randomNum%4] = 2;
        int randomNum2 = random.nextInt(16);
        while(randomNum2 == randomNum){
            randomNum2 = random.nextInt(16);
        }
        numbers[randomNum2/4][randomNum2%4] = 4;
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                gamePanel.getModel().setNumber(i,j,numbers[i][j]);
            }
        }
    }
    public int[] getNumber(){
        int[] result = new int[16];
        for(int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                result[i+4 * j] = gamePanel.getModel().getNumber(i,j);
            }
        }
        return result;
    }
    public static void main(String args[]){
//        DuoGameFrame duoGameFrame1 = new DuoGameFrame(1000,800,"10",new MainController("游客"),0);
//            DuoGameFrame duoGameFrame2 = new DuoGameFrame(1000,800,"10",new MainController("游客"),1);
//            duoGameFrame1.setVisible(true);
//            duoGameFrame2.setVisible(true);

//        new MainFrame.ServerConnectionWorker(this, currentUser).execute();
//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("111");
//                ServerConnection serverConnection2 = new ServerConnection();
//            }
//        }
//        timer.schedule(timerTask,4000);
        try {
            LoginFrame loginFrame = new LoginFrame(600,400 );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
