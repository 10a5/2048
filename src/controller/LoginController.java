package controller;
import model.ServerConnection;
import view.GameFrame;
import view.LoginFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LoginController {
//    private static ServerConnection server;
//    public LoginController(){
////        this.server = server;
//    }
    public static void newGame(String username){
        MainController mainController = new MainController(username);
    }
    public static String currentUser;
    private Scanner input = new Scanner(System.in);
    private String username;
    private String password1;
    private String password2;
    private final String basePath = ".\\src\\Users\\";

    String regex = "^[a-zA-Z0-9_]+$";
    private  void  createUserFolder(String username,String password){
        File baseFolder = new File(basePath);
        if (!baseFolder.exists()){
            baseFolder.mkdir();
        }
        File userFile = new File(basePath + username +".txt");
        try {
            FileWriter writer = new FileWriter(userFile);
            writer.write("用户名："+ username + "\n");
            writer.write("密码："+ password+"\n");
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //注册
    public void register() {
        boolean registered = false;
        while (!registered) {
            System.out.println("新用户名：");
            this.username = input.next();
            System.out.println("请输入密码：");
            this.password1 = input.next();
            System.out.println("请再次输入密码：");
            this.password2 = input.next();
            if (doublecheck() && isreasonable() && isnotexist()) {
                createUserFolder(username,password1);
                System.out.println("注册成功！");
                break;
            }
            if (!doublecheck()) {
                System.out.println("两次密码输入不一致");
            }
            if (!isnotexist()) {
                System.out.println("用户名已存在");
            }
            if (!isreasonable()) {
                System.out.println("用户名长度应在10以内，密码应在6~20之间");
                System.out.println("用户名与密码均只能用字母、数字以及下划线“_”组成");
            }

        }
    }
    //检查用户是否存在
    private boolean checkUserExists(String username){
        File baseFolder = new File(basePath);
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
    //检查密码与用户名是否匹配
    private boolean checkPassword(String username,String password){
        File userFile = new File(basePath + username + ".txt");
        try {
            Scanner scanner = new Scanner(userFile);
            scanner.nextLine();
            String PasswordLine = scanner.nextLine();
            String Password = PasswordLine.substring(PasswordLine.indexOf("：")+1);
            scanner.close();
            return password.equals(Password);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //二次检查密码输入正确
    public boolean doublecheck() {
        if (this.password1.equals(this.password2)) {
            return true;
        }
        return false;
    }
    //检查用户名是否存在

    public boolean isnotexist() {
        if (!checkUserExists(username)) {
            return true;
        }
        return false;
    }
    //检查用户名或密码是否合理
    public boolean isreasonable() {
        if (this.username.length() <= 10 && this.password1.length() >= 6 && this.password1.length() <= 20 && this.username.matches(regex) && this.password1.matches(regex)) {
            return true;
        }
        return false;
    }
    //登录
    public void login() {
        boolean logined = false;
        while (!logined) {
            System.out.println("请输入用户名：");
            String usernameInput = input.next();
            System.out.println("请输入密码：");
            String passwordInput = input.next();
            if (checkUserExists(usernameInput)&&checkPassword(usernameInput,passwordInput)) {
                System.out.println("登录成功！");
                currentUser = usernameInput;
                LoginController.newGame(usernameInput);
                break;
            } else {
                System.out.println("用户名或密码错误！");
            }
        }
    }

    public static void main(String[] args) {
        LoginController loginSystem = new LoginController();
        Scanner input = new Scanner(System.in);
        System.out.println("登录or注册：");
        int choice = input.nextInt();
        if (choice == 1) {
            loginSystem.register();
        } else if (choice == 2) {
            loginSystem.login();
        } else {
            System.out.println("请输入有效选项！");
        }
    }
}
