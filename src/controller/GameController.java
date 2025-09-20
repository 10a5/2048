package controller;

import model.GridNumber;
import model.ServerConnection;
import view.GameFrame;
import view.GamePanel;


/**
 * This class is used for interactive with JButton in GameFrame.
 */
public class GameController {
    private GamePanel view;
    private GridNumber model;
    private String currentUser;
    private ServerConnection server;
    private MainController mainController;


    public GameController(GamePanel view, GridNumber model , String currentUser , MainController mainController) {
        this.view = view;
        this.model = model;
        this.currentUser = currentUser;
        this.mainController = mainController;
    }
    public void restartGame() {
        GameFrame gameFrame = new GameFrame(1000,800,currentUser,mainController);
        gameFrame.setVisible(true);
    }

    //todo: add other methods such as loadGame, saveGame...

}
