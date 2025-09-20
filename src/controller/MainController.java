package controller;
import view.LoginFrame;
import view.MainFrame;

import java.io.IOException;

public class MainController {
    private MainFrame mainFrame;
    public static String currentUser;
    public MainController(String currentUser){
        this.currentUser = currentUser;
        if(currentUser != "游客"){mainFrame = new MainFrame(600 , 400 , this , currentUser);}
    }
    public void appear() throws IOException {
        if(currentUser != "游客"){mainFrame = new MainFrame(600 , 400 , this , currentUser);}
        else {
            LoginFrame loginFrame = new LoginFrame(600,400);
        }
    }
}
