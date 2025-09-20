package view;

import controller.LoginController;
import controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class LoginFrame extends JFrame {
    private  WavPlayer WavPlayer;
    private JButton LoginBtn;
    private LoginController loginController;
    private JButton RegisterBtn , QuickBtn, yesBtn,undoBtn;
    private JLabel userLabel , passwordLabel , checkPasswordLabel,whiteStuff;
    public LoginFrame(int width, int height) throws IOException {
        BackgroundPanel backgroundPanel = new BackgroundPanel(".\\src\\resourses\\frame\\loginBackground.jpg");
        setContentPane(backgroundPanel);
        setComponentZOrder(backgroundPanel,0);
        ImageIcon whiteStuff = new ImageIcon(".\\src\\resourses\\frame\\whiteSpecial.png");
        this.whiteStuff = new JLabel(whiteStuff);
        this.add(this.whiteStuff);
        this.whiteStuff.setVisible(false);
        this.whiteStuff.setLocation(0,0);
        this.whiteStuff.setSize(600,400);
//        if(!server.getConnection()){
//            System.out.println("未连接服务器");
//        }else{
//            System.out.println("已连接服务器");
////            server.sendMessage("123");
////            System.out.println(server.getMessage());
//        }
        this.setTitle("这是...2048?");
        this.setLayout(null);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        setResizable(false);
        this.loginController = new LoginController();
        this.WavPlayer = new WavPlayer();
        this.LoginBtn = createButton("登录", new Point(400,50),100,50);
        this.RegisterBtn = createButton("注册", new Point(400,150),100,50);
        this.QuickBtn = createButton("快速游玩", new Point(400,250),100,50);
        this.undoBtn = createButton("取消", new Point(400,290),100,50);
        undoBtn.setVisible(false);
        this.userLabel = createLabel("用户名",new Font("serif", Font.BOLD, 20), new Point(20, 100), 100, 50);
        this.passwordLabel = createLabel("密码",new Font("serif", Font.BOLD, 20), new Point(20, 200), 100, 50);
        this.checkPasswordLabel = createLabel("确定密码",new Font("serif", Font.BOLD, 20), new Point(20, 300), 100, 50);
        this.userLabel.setVisible(false);
        this.passwordLabel.setVisible(false);
        this.checkPasswordLabel.setVisible(false);
        userLabel.setForeground(Color.BLUE);
        passwordLabel.setForeground(Color.BLUE);
        checkPasswordLabel.setForeground(Color.BLUE);

        this.LoginBtn.addActionListener(e -> {
//            BackgroundPanel backgroundPanel1 = new BackgroundPanel(".\\src\\resourses\\frame\\loginBackground1.png");
//            setContentPane(backgroundPanel1);
//            setComponentZOrder(backgroundPanel1,0);
            this.whiteStuff.setVisible(true);
            hideBtn();
            this.add(userLabel);
            this.add(passwordLabel);
            userLabel.setLocation(340,85);
            passwordLabel.setLocation(340,145);
            this.userLabel.setVisible(true);
            this.passwordLabel.setVisible(true);
            undoBtn.setVisible(true);

            JTextField textField = new JTextField(20);
            this.add(new JLabel("单行输入："), BorderLayout.NORTH);
            this.add(textField, BorderLayout.CENTER);
            textField.setVisible(true);
            textField.setSize(100,40);
            textField.setLocation(405,90);

            JTextField passwordField1 = new JPasswordField(20);
            this.add(new JLabel("密码："), BorderLayout.NORTH);
            this.add(passwordField1, BorderLayout.CENTER);
            passwordField1.setVisible(true);
            passwordField1.setSize(100,40);
            passwordField1.setLocation(405,150);

            this.yesBtn = createButton("确定", new Point(400,220),100,50);
            this.yesBtn.addActionListener(u -> {
                String userName = textField.getText(), password1 = passwordField1.getText();
                if (!checkUserExists(userName)) {
                    JOptionPane.showMessageDialog(this, "用户名不存在！", "登录失败", JOptionPane.INFORMATION_MESSAGE);
                    passwordField1.setText("");
                } else if (!checkPassword(userName , password1)) {
                    JOptionPane.showMessageDialog(this, "用户名或密码错误！", "登录失败", JOptionPane.INFORMATION_MESSAGE);
                    passwordField1.setText("");
                }else {
                    UIManager.put("OptionPane.background",Color.ORANGE);
                    JOptionPane.showMessageDialog(this, "登录成功！", "登录成功", JOptionPane.INFORMATION_MESSAGE);
                    LoginController.newGame(userName);
                    this.dispose();
                }
            });
        });
        this.RegisterBtn.addActionListener(e -> {
//            BackgroundPanel backgroundPanel1 = new BackgroundPanel(".\\src\\resourses\\frame\\loginBackground1.png");
//            setContentPane(backgroundPanel1);
//            setComponentZOrder(backgroundPanel1,0);
            this.whiteStuff.setVisible(true);
            hideBtn();
            this.add(userLabel);
            this.add(passwordLabel);
            this.add(checkPasswordLabel);
            userLabel.setLocation(365,50);
            passwordLabel.setLocation(365,100);
            checkPasswordLabel.setLocation(365,150);
            userLabel.setVisible(true);
            passwordLabel.setVisible(true);
            checkPasswordLabel.setVisible(true);
            undoBtn.setVisible(true);

            JTextField textField = new JTextField(20); // 20 列宽度
            this.add(new JLabel("单行输入："), BorderLayout.NORTH);
            this.add(textField, BorderLayout.CENTER);
            textField.setVisible(true);
            textField.setSize(100,40);
            textField.setLocation(450,50);

            JTextField passwordField1 = new JPasswordField(20); // 20 列宽度
            this.add(new JLabel("密码："), BorderLayout.NORTH);
            this.add(passwordField1, BorderLayout.CENTER);
            passwordField1.setVisible(true);
            passwordField1.setSize(100,40);
            passwordField1.setLocation(450,100);

            JTextField passwordField2 = new JPasswordField(20); // 20 列宽度
            this.add(new JLabel("密码："), BorderLayout.NORTH);
            this.add(passwordField2, BorderLayout.CENTER);
            passwordField2.setVisible(true);
            passwordField2.setSize(100,40);
            passwordField2.setLocation(450,150);

            this.yesBtn = createButton("确定", new Point(400,220),100,50);
            this.yesBtn.addActionListener(u -> {
                String userName = textField.getText() , password1 = passwordField1.getText() , password2 = passwordField2.getText();
                if(checkUserExists(userName)){
                    JOptionPane.showMessageDialog(this, "该用户名已存在！", "注册失败", JOptionPane.INFORMATION_MESSAGE);
                    passwordField1.setText("");
                    passwordField2.setText("");
                }else if(!Objects.equals(password1, password2)){
                    JOptionPane.showMessageDialog(this, "两次输入的密码不一致！", "注册失败", JOptionPane.INFORMATION_MESSAGE);
                    passwordField1.setText("");
                    passwordField2.setText("");
                }else if (userName.length() < 2) {
                    JOptionPane.showMessageDialog(this, "用户名太短啦！设计的长一点吧！（长度限制：2~10）", "注册失败", JOptionPane.INFORMATION_MESSAGE);
                    passwordField1.setText("");
                    passwordField2.setText("");
                }else if (userName.length() > 10) {
                    JOptionPane.showMessageDialog(this, "用户名太长啦！设计的短一点吧！（长度限制：2~10）", "注册失败", JOptionPane.INFORMATION_MESSAGE);
                    passwordField1.setText("");
                    passwordField2.setText("");
                }else if (password1.length() < 6) {
                    JOptionPane.showMessageDialog(this, "密码太短啦！设计的复杂一点吧！（长度限制：6~20）", "注册失败", JOptionPane.INFORMATION_MESSAGE);
                    passwordField1.setText("");
                    passwordField2.setText("");
                }else if (password1.length() > 20) {
                    JOptionPane.showMessageDialog(this, "密码太长啦！设计的简洁一点吧！（长度限制：6~20）", "注册失败", JOptionPane.INFORMATION_MESSAGE);
                    passwordField1.setText("");
                    passwordField2.setText("");
                }else{
                    createUserFolder(userName,password1);
                    JOptionPane.showMessageDialog(this, "注册成功！快去登陆吧！", "注册成功", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        LoginFrame loginFrame = new LoginFrame(600,400 );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    this.dispose();
                }
            });
//            loginController.register();
        });
        this.QuickBtn.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(1000,800,"游客",new MainController("游客"));
            gameFrame.setVisible(true);
            gameFrame.getGamePanel().requestFocusInWindow();
            this.dispose();
        });
        this.undoBtn.addActionListener(e -> {
            try {
                LoginFrame loginFrame = new LoginFrame(600,400);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
        });
        this.setVisible(true);
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
    private void hideBtn(){
        this.QuickBtn.setVisible(false);
        this.LoginBtn.setVisible(false);
        this.RegisterBtn.setVisible(false);

    }
    private boolean checkUserExists(String username){
        File baseFolder = new File(".\\src\\Users");
        if (baseFolder.exists()){
            File[] files = baseFolder.listFiles();
            if (files != null){
                for (File file : files){
                    if (file.getName().equals(username+".txt")){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private  void  createUserFolder(String username,String password){
        File baseFolder = new File(".\\src\\Users\\");
        if (!baseFolder.exists()){
            baseFolder.mkdir();
        }
        File userFile = new File(".\\src\\Users\\" + username +".txt");
        try {
            FileWriter writer = new FileWriter(userFile);
            writer.write("用户名："+ username + "\n");
            writer.write("密码："+ password+"\n");
            writer.write("积分：0\n");
            writer.write("时间：0\n");
            for (int i = 0 ; i < 16 ; i++){
                writer.write("0\n");
            }
            writer.write("步数：0");
//            System.out.println(userFile.length());
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private boolean checkPassword(String username,String password){
        File userFile = new File(".\\src\\Users\\" + username + ".txt");
        try {
            Scanner scanner = new Scanner(userFile);
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            } else {
                JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            String PasswordLine = null;
            if (scanner.hasNextLine()) {
                PasswordLine = scanner.nextLine();
            } else {
                JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            String Password = null;
            if (PasswordLine.contains("：")) {
                Password = PasswordLine.substring(PasswordLine.indexOf("：") + 1);
            } else{
                JOptionPane.showMessageDialog(this, "文件损坏！请联系管理员", "错误", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            scanner.close();
            return password.equals(Password);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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