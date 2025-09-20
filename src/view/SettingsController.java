package view;

import model.ServerConnection;

import java.io.IOException;

public class SettingsController {
    private  GameFrame gameFrame;
    private ServerConnection server;
    public SettingsController(GameFrame gameFrame, ServerConnection server){
        this.gameFrame = gameFrame;
        this.server = server;
    }
    public void newSettings(){
        SettingsFrame settingsFrame = new SettingsFrame(600,400,this,server);
        settingsFrame.setVisible(true);
    }
    public void restartGame(){
        gameFrame.restartGame();
    }
    public void backToGame(){
        gameFrame.backToGame();
    }

    public void backToMain() throws IOException {
        gameFrame.backToMain();
    }
    public void save(){
        gameFrame.save();
    }
}
